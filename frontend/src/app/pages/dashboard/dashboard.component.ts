import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CustomerService } from '../../core/services/customer.service';
import { AccountService } from '../../core/services/account.service';
import { TransactionService } from '../../core/services/transaction.service';
import { Customer } from '../../core/models/customer.model';
import { Account, AccountType, ACCOUNT_LIMITS } from '../../core/models/account.model';

import { SpotlightCardComponent } from '../../shared/components/spotlight-card/spotlight-card.component';
import { TiltedCardComponent } from '../../shared/components/tilted-card/tilted-card.component';
import { CountUpComponent } from '../../shared/components/count-up/count-up.component';
import { ShinyTextComponent } from '../../shared/components/shiny-text/shiny-text.component';
import { DecryptedTextComponent } from '../../shared/components/decrypted-text/decrypted-text.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    RouterModule,
    SpotlightCardComponent,
    TiltedCardComponent,
    CountUpComponent,
    ShinyTextComponent,
    DecryptedTextComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  customers = signal<Customer[]>([]);
  accounts = signal<Account[]>([]);
  loading = signal<boolean>(true);

  // Computed stats
  totalBalance = signal<number>(0);
  activeAccountsCount = signal<number>(0);
  customersCount = signal<number>(0);

  // Quick Action Modal State
  activeModal = signal<'DEPOSIT' | 'WITHDRAW' | 'TRANSFER' | 'NEW_ACCOUNT' | null>(null);
  selectedAccountNumber = '';
  transferTargetAccountNumber = '';
  transactionAmount: number | null = null;
  transactionLoading = signal<boolean>(false);
  modalError = signal<string | null>(null);
  modalSuccess = signal<string | null>(null);

  // New Account fields
  newAccountCustomerId = '';
  newAccountNumber = '';
  newAccountType: AccountType = 'CURRENT';
  newAccountInitialBalance: number = 1000;

  readonly accountLimits = ACCOUNT_LIMITS;

  constructor(
    private customerService: CustomerService,
    private accountService: AccountService,
    private transactionService: TransactionService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading.set(true);

    // Fetch individual and corporate customers
    this.customerService.getCustomers('INDIVIDUAL').subscribe({
      next: (indCustomers) => {
        this.customerService.getCustomers('CORPORATE').subscribe({
          next: (corpCustomers) => {
            const allCust = [...indCustomers, ...corpCustomers];
            this.customers.set(allCust);
            this.customersCount.set(allCust.length);

            // Fetch accounts for all customers
            this.fetchAllAccounts(allCust);
          },
          error: () => {
            this.customers.set(indCustomers);
            this.fetchAllAccounts(indCustomers);
          }
        });
      },
      error: () => {
        this.loading.set(false);
      }
    });
  }

  private fetchAllAccounts(customersList: Customer[]): void {
    if (customersList.length === 0) {
      this.accounts.set([]);
      this.loading.set(false);
      return;
    }

    let loadedAccounts: Account[] = [];
    let pending = customersList.length;

    customersList.forEach(c => {
      if (c.id) {
        this.accountService.getAccountsByCustomer(c.id).subscribe({
          next: (accs) => {
            loadedAccounts = [...loadedAccounts, ...accs];
            pending--;
            if (pending === 0) {
              this.setAccountsData(loadedAccounts);
            }
          },
          error: () => {
            pending--;
            if (pending === 0) {
              this.setAccountsData(loadedAccounts);
            }
          }
        });
      } else {
        pending--;
        if (pending === 0) {
          this.setAccountsData(loadedAccounts);
        }
      }
    });
  }

  private setAccountsData(accs: Account[]): void {
    this.accounts.set(accs);
    const total = accs.reduce((sum, a) => sum + (Number(a.accountBalance) || 0), 0);
    this.totalBalance.set(total);
    this.activeAccountsCount.set(accs.filter(a => a.accountState === 'OPEN').length);
    this.loading.set(false);
  }

  openModal(type: 'DEPOSIT' | 'WITHDRAW' | 'TRANSFER' | 'NEW_ACCOUNT', defaultAccount?: string): void {
    this.activeModal.set(type);
    this.selectedAccountNumber = defaultAccount || (this.accounts().length > 0 ? this.accounts()[0].accountNumber : '');
    this.transferTargetAccountNumber = '';
    this.transactionAmount = null;
    this.modalError.set(null);
    this.modalSuccess.set(null);

    if (this.customers().length > 0) {
      this.newAccountCustomerId = this.customers()[0].id || '';
    }
  }

  closeModal(): void {
    this.activeModal.set(null);
    this.modalError.set(null);
    this.modalSuccess.set(null);
  }

  executeTransaction(): void {
    if (!this.selectedAccountNumber || !this.transactionAmount || this.transactionAmount <= 0) {
      this.modalError.set('Please specify a valid account number and positive amount.');
      return;
    }

    this.modalError.set(null);
    this.modalSuccess.set(null);
    this.transactionLoading.set(true);

    const type = this.activeModal();

    if (type === 'DEPOSIT') {
      this.transactionService.deposit({
        accountNumber: this.selectedAccountNumber,
        amount: this.transactionAmount
      }).subscribe({
        next: () => {
          this.handleTransactionSuccess('Deposit successful! Balance updated.');
        },
        error: (err) => this.handleTransactionError(err)
      });
    } else if (type === 'WITHDRAW') {
      this.transactionService.withdraw({
        accountNumber: this.selectedAccountNumber,
        amount: this.transactionAmount
      }).subscribe({
        next: () => {
          this.handleTransactionSuccess('Withdrawal successful! Cash dispensed.');
        },
        error: (err) => this.handleTransactionError(err)
      });
    } else if (type === 'TRANSFER') {
      if (!this.transferTargetAccountNumber) {
        this.transactionLoading.set(false);
        this.modalError.set('Please specify a destination account number.');
        return;
      }
      this.transactionService.transfer({
        accountNumber: this.selectedAccountNumber,
        accountNumberTo: this.transferTargetAccountNumber,
        amount: this.transactionAmount
      }).subscribe({
        next: () => {
          this.handleTransactionSuccess('Transfer completed successfully!');
        },
        error: (err) => this.handleTransactionError(err)
      });
    }
  }

  executeCreateAccount(): void {
    if (!this.newAccountCustomerId || !this.newAccountNumber) {
      this.modalError.set('Customer and Account Number are required.');
      return;
    }

    this.transactionLoading.set(true);
    this.modalError.set(null);

    const newAcc: Account = {
      customerId: this.newAccountCustomerId,
      accountNumber: this.newAccountNumber,
      accountBalance: this.newAccountInitialBalance || 0,
      accountState: 'OPEN',
      creationDate: new Date().toISOString().split('T')[0],
      accountType: this.newAccountType
    };

    this.accountService.createAccount(newAcc).subscribe({
      next: () => {
        this.handleTransactionSuccess(`Account ${newAcc.accountNumber} created successfully!`);
      },
      error: (err) => this.handleTransactionError(err)
    });
  }

  private handleTransactionSuccess(msg: string): void {
    this.transactionLoading.set(false);
    this.modalSuccess.set(msg);
    setTimeout(() => {
      this.closeModal();
      this.loadData();
    }, 1200);
  }

  private handleTransactionError(err: any): void {
    this.transactionLoading.set(false);
    const msg = err.error?.message || err.error || err.message || 'Operation failed. Check account limits and balance.';
    this.modalError.set(msg);
  }
}
