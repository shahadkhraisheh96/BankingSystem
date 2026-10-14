import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TransactionService } from '../../core/services/transaction.service';
import { AccountService } from '../../core/services/account.service';
import { CustomerService } from '../../core/services/customer.service';
import { Account, ACCOUNT_LIMITS } from '../../core/models/account.model';
import { Transaction } from '../../core/models/transaction.model';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {
  activeTab = signal<'TRANSFER' | 'DEPOSIT' | 'WITHDRAW' | 'HISTORY'>('HISTORY');
  accounts = signal<Account[]>([]);
  transactions = signal<Transaction[]>([]);
  loadingLedger = signal<boolean>(false);
  opLoading = signal<boolean>(false);

  // Search / Selection for Ledger
  selectedLedgerAccount = '';

  // Forms
  depositAccount = '';
  depositAmount: number | null = null;

  withdrawAccount = '';
  withdrawAmount: number | null = null;

  transferFrom = '';
  transferTo = '';
  transferAmount: number | null = null;

  // Alerts
  opError = signal<string | null>(null);
  opSuccess = signal<string | null>(null);

  readonly accountLimits = ACCOUNT_LIMITS;

  constructor(
    private transactionService: TransactionService,
    private accountService: AccountService,
    private customerService: CustomerService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadAccounts();

    // Support query param ?account=100029344
    this.route.queryParams.subscribe(params => {
      if (params['account']) {
        this.selectedLedgerAccount = params['account'];
        this.depositAccount = params['account'];
        this.withdrawAccount = params['account'];
        this.transferFrom = params['account'];
        this.loadLedger();
      }
    });
  }

  loadAccounts(): void {
    // Load individual + corporate to retrieve accounts
    this.customerService.getCustomers('INDIVIDUAL').subscribe({
      next: (ind) => {
        this.customerService.getCustomers('CORPORATE').subscribe({
          next: (corp) => {
            const allCust = [...ind, ...corp];
            let allAccs: Account[] = [];
            let count = allCust.length;
            if (count === 0) return;

            allCust.forEach(c => {
              if (c.id) {
                this.accountService.getAccountsByCustomer(c.id).subscribe({
                  next: (accs) => {
                    allAccs = [...allAccs, ...accs];
                    count--;
                    if (count === 0) {
                      this.accounts.set(allAccs);
                      if (!this.selectedLedgerAccount && allAccs.length > 0) {
                        this.selectedLedgerAccount = allAccs[0].accountNumber;
                        this.depositAccount = allAccs[0].accountNumber;
                        this.withdrawAccount = allAccs[0].accountNumber;
                        this.transferFrom = allAccs[0].accountNumber;
                        this.loadLedger();
                      }
                    }
                  },
                  error: () => {
                    count--;
                  }
                });
              } else {
                count--;
              }
            });
          }
        });
      }
    });
  }

  loadLedger(): void {
    if (!this.selectedLedgerAccount) return;
    this.loadingLedger.set(true);
    this.transactionService.getTransactions(this.selectedLedgerAccount).subscribe({
      next: (txs) => {
        this.transactions.set(txs || []);
        this.loadingLedger.set(false);
      },
      error: () => {
        this.transactions.set([]);
        this.loadingLedger.set(false);
      }
    });
  }

  executeDeposit(): void {
    if (!this.depositAccount || !this.depositAmount || this.depositAmount <= 0) {
      this.opError.set('Please provide valid account number and positive amount.');
      return;
    }

    this.clearAlerts();
    this.opLoading.set(true);

    this.transactionService.deposit({
      accountNumber: this.depositAccount,
      amount: this.depositAmount
    }).subscribe({
      next: () => {
        this.opLoading.set(false);
        this.opSuccess.set(`Successfully deposited $${this.depositAmount} into account ${this.depositAccount}`);
        this.depositAmount = null;
        this.loadAccounts();
        if (this.selectedLedgerAccount === this.depositAccount) {
          this.loadLedger();
        }
      },
      error: (err) => this.handleError(err)
    });
  }

  executeWithdraw(): void {
    if (!this.withdrawAccount || !this.withdrawAmount || this.withdrawAmount <= 0) {
      this.opError.set('Please provide valid account number and positive amount.');
      return;
    }

    this.clearAlerts();
    this.opLoading.set(true);

    this.transactionService.withdraw({
      accountNumber: this.withdrawAccount,
      amount: this.withdrawAmount
    }).subscribe({
      next: () => {
        this.opLoading.set(false);
        this.opSuccess.set(`Successfully withdrew $${this.withdrawAmount} from account ${this.withdrawAccount}`);
        this.withdrawAmount = null;
        this.loadAccounts();
        if (this.selectedLedgerAccount === this.withdrawAccount) {
          this.loadLedger();
        }
      },
      error: (err) => this.handleError(err)
    });
  }

  executeTransfer(): void {
    if (!this.transferFrom || !this.transferTo || !this.transferAmount || this.transferAmount <= 0) {
      this.opError.set('Please fill all transfer fields with valid values.');
      return;
    }

    if (this.transferFrom === this.transferTo) {
      this.opError.set('Origin and destination accounts cannot be identical.');
      return;
    }

    this.clearAlerts();
    this.opLoading.set(true);

    this.transactionService.transfer({
      accountNumber: this.transferFrom,
      accountNumberTo: this.transferTo,
      amount: this.transferAmount
    }).subscribe({
      next: () => {
        this.opLoading.set(false);
        this.opSuccess.set(`Successfully transferred $${this.transferAmount} to account ${this.transferTo}`);
        this.transferAmount = null;
        this.loadAccounts();
        if (this.selectedLedgerAccount === this.transferFrom || this.selectedLedgerAccount === this.transferTo) {
          this.loadLedger();
        }
      },
      error: (err) => this.handleError(err)
    });
  }

  getSelectedAccountType(accountNumber: string): string {
    const acc = this.accounts().find(a => a.accountNumber === accountNumber);
    return acc ? acc.accountType : 'UNKNOWN';
  }

  getWithdrawalLimit(accountNumber: string): number {
    const acc = this.accounts().find(a => a.accountNumber === accountNumber);
    return acc ? (this.accountLimits[acc.accountType] || 0) : 0;
  }

  private handleError(err: any): void {
    this.opLoading.set(false);
    const msg = err.error?.message || err.error || err.message || 'Operation failed. Limit or balance check failed.';
    this.opError.set(msg);
  }

  private clearAlerts(): void {
    this.opError.set(null);
    this.opSuccess.set(null);
  }
}
