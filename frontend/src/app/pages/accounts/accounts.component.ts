import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AccountService } from '../../core/services/account.service';
import { CustomerService } from '../../core/services/customer.service';
import { Account, AccountType, ACCOUNT_LIMITS } from '../../core/models/account.model';
import { Customer } from '../../core/models/customer.model';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  accounts = signal<Account[]>([]);
  customers = signal<Customer[]>([]);
  loading = signal<boolean>(true);

  // Filters
  filterCustomerId = '';
  searchAccountNumber = '';

  // New Account Dialog
  showCreateModal = signal<boolean>(false);
  newCustomerId = '';
  newAccountNumber = '';
  newAccountType: AccountType = 'CURRENT';
  newInitialBalance: number = 2500;
  createLoading = signal<boolean>(false);
  createError = signal<string | null>(null);
  createSuccess = signal<string | null>(null);

  readonly accountLimits = ACCOUNT_LIMITS;

  constructor(
    private accountService: AccountService,
    private customerService: CustomerService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading.set(true);
    this.customerService.getCustomers('INDIVIDUAL').subscribe({
      next: (ind) => {
        this.customerService.getCustomers('CORPORATE').subscribe({
          next: (corp) => {
            const allCust = [...ind, ...corp];
            this.customers.set(allCust);
            if (allCust.length > 0 && !this.newCustomerId) {
              this.newCustomerId = allCust[0].id || '';
            }
            this.loadAllAccounts(allCust);
          },
          error: () => this.loadAllAccounts(ind)
        });
      },
      error: () => this.loading.set(false)
    });
  }

  private loadAllAccounts(custList: Customer[]): void {
    let allAccs: Account[] = [];
    let pending = custList.length;
    if (pending === 0) {
      this.accounts.set([]);
      this.loading.set(false);
      return;
    }

    custList.forEach(c => {
      if (c.id) {
        this.accountService.getAccountsByCustomer(c.id).subscribe({
          next: (accs) => {
            allAccs = [...allAccs, ...accs];
            pending--;
            if (pending === 0) {
              this.accounts.set(allAccs);
              this.loading.set(false);
            }
          },
          error: () => {
            pending--;
            if (pending === 0) {
              this.accounts.set(allAccs);
              this.loading.set(false);
            }
          }
        });
      } else {
        pending--;
        if (pending === 0) {
          this.accounts.set(allAccs);
          this.loading.set(false);
        }
      }
    });
  }

  filteredAccounts(): Account[] {
    return this.accounts().filter(a => {
      const matchCustomer = this.filterCustomerId ? a.customerId === this.filterCustomerId : true;
      const matchNumber = this.searchAccountNumber 
        ? a.accountNumber.toLowerCase().includes(this.searchAccountNumber.toLowerCase()) 
        : true;
      return matchCustomer && matchNumber;
    });
  }

  openCreateModal(): void {
    this.showCreateModal.set(true);
    this.createError.set(null);
    this.createSuccess.set(null);
  }

  closeCreateModal(): void {
    this.showCreateModal.set(false);
    this.createError.set(null);
    this.createSuccess.set(null);
  }

  submitCreateAccount(): void {
    if (!this.newCustomerId || !this.newAccountNumber) {
      this.createError.set('Please fill out all required fields.');
      return;
    }

    this.createLoading.set(true);
    this.createError.set(null);

    const newAcc: Account = {
      customerId: this.newCustomerId,
      accountNumber: this.newAccountNumber,
      accountBalance: this.newInitialBalance || 0,
      accountState: 'OPEN',
      creationDate: new Date().toISOString().split('T')[0],
      accountType: this.newAccountType
    };

    this.accountService.createAccount(newAcc).subscribe({
      next: () => {
        this.createLoading.set(false);
        this.createSuccess.set(`Account ${newAcc.accountNumber} successfully created!`);
        setTimeout(() => {
          this.closeCreateModal();
          this.loadData();
        }, 1200);
      },
      error: (err) => {
        this.createLoading.set(false);
        this.createError.set(err.error?.message || err.error || 'Failed to create account.');
      }
    });
  }
}
