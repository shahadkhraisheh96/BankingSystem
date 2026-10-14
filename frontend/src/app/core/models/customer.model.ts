export type CustomerType = 'INDIVIDUAL' | 'CORPORATE';

export interface Customer {
  id?: string;
  phoneNumber: number;
  registrationDate: string;
  customerType: CustomerType;
}

export interface IndividualCustomer extends Customer {
  firstName: string;
  lastName: string;
  birthDate: string;
  nationalId: number;
}

export interface CorporateCustomer extends Customer {
  companyName: string;
  taxId: number;
}
