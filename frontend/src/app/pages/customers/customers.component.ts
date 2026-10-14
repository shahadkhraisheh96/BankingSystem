import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CustomerService } from '../../core/services/customer.service';
import { Customer, CustomerType, IndividualCustomer, CorporateCustomer } from '../../core/models/customer.model';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  customers = signal<Customer[]>([]);
  selectedType = signal<CustomerType>('INDIVIDUAL');
  loading = signal<boolean>(true);
  searchQuery = '';

  // Modal
  showModal = signal<boolean>(false);
  modalType: CustomerType = 'INDIVIDUAL';
  createLoading = signal<boolean>(false);
  modalError = signal<string | null>(null);
  modalSuccess = signal<string | null>(null);

  // Individual Form Fields
  indFirstName = '';
  indLastName = '';
  indBirthDate = '1995-05-15';
  indNationalId: number | null = null;
  indPhone: number | null = null;

  // Corporate Form Fields
  corpCompanyName = '';
  corpTaxId: number | null = null;
  corpPhone: number | null = null;

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.loading.set(true);
    this.customerService.getCustomers(this.selectedType()).subscribe({
      next: (data) => {
        this.customers.set(data || []);
        this.loading.set(false);
      },
      error: () => {
        this.customers.set([]);
        this.loading.set(false);
      }
    });
  }

  changeType(type: CustomerType): void {
    this.selectedType.set(type);
    this.searchQuery = '';
    this.loadCustomers();
  }

  filteredCustomers(): Customer[] {
    if (!this.searchQuery) return this.customers();
    const q = this.searchQuery.toLowerCase();
    return this.customers().filter(c => {
      const idMatch = c.id?.toLowerCase().includes(q);
      if (c.customerType === 'INDIVIDUAL') {
        const ind = c as IndividualCustomer;
        const nameMatch = (ind.firstName + ' ' + ind.lastName).toLowerCase().includes(q);
        return idMatch || nameMatch;
      } else {
        const corp = c as CorporateCustomer;
        const compMatch = corp.companyName?.toLowerCase().includes(q);
        return idMatch || compMatch;
      }
    });
  }

  openRegisterModal(type: CustomerType): void {
    this.modalType = type;
    this.modalError.set(null);
    this.modalSuccess.set(null);
    this.showModal.set(true);
  }

  closeModal(): void {
    this.showModal.set(false);
    this.modalError.set(null);
    this.modalSuccess.set(null);
  }

  submitCustomer(): void {
    this.modalError.set(null);
    this.modalSuccess.set(null);
    this.createLoading.set(true);

    const today = new Date().toISOString().split('T')[0];

    if (this.modalType === 'INDIVIDUAL') {
      if (!this.indFirstName || !this.indLastName || !this.indNationalId || !this.indPhone) {
        this.createLoading.set(false);
        this.modalError.set('Please fill in all individual customer fields.');
        return;
      }

      const newInd: Partial<IndividualCustomer> = {
        firstName: this.indFirstName,
        lastName: this.indLastName,
        birthDate: this.indBirthDate,
        nationalId: Number(this.indNationalId),
        phoneNumber: Number(this.indPhone),
        registrationDate: today
      };

      this.customerService.addIndividual(newInd).subscribe({
        next: () => this.handleSuccess('Individual Client successfully registered!'),
        error: (err) => this.handleError(err)
      });
    } else {
      if (!this.corpCompanyName || !this.corpTaxId || !this.corpPhone) {
        this.createLoading.set(false);
        this.modalError.set('Please fill in all corporate customer fields.');
        return;
      }

      const newCorp: Partial<CorporateCustomer> = {
        companyName: this.corpCompanyName,
        taxId: Number(this.corpTaxId),
        phoneNumber: Number(this.corpPhone),
        registrationDate: today
      };

      this.customerService.addCorporate(newCorp).subscribe({
        next: () => this.handleSuccess('Corporate Client successfully registered!'),
        error: (err) => this.handleError(err)
      });
    }
  }

  private handleSuccess(msg: string): void {
    this.createLoading.set(false);
    this.modalSuccess.set(msg);
    setTimeout(() => {
      this.closeModal();
      this.loadCustomers();
    }, 1200);
  }

  private handleError(err: any): void {
    this.createLoading.set(false);
    this.modalError.set(err.error?.message || err.error || 'Failed to onboard client.');
  }

  asIndividual(c: Customer): IndividualCustomer {
    return c as IndividualCustomer;
  }

  asCorporate(c: Customer): CorporateCustomer {
    return c as CorporateCustomer;
  }
}
