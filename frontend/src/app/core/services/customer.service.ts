import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer, CustomerType, IndividualCustomer, CorporateCustomer } from '../models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private readonly baseUrl = 'http://localhost:8080/api/customers';

  constructor(private http: HttpClient) {}

  addIndividual(customer: Partial<IndividualCustomer>): Observable<IndividualCustomer> {
    return this.http.post<IndividualCustomer>(`${this.baseUrl}/individual`, customer);
  }

  addCorporate(customer: Partial<CorporateCustomer>): Observable<CorporateCustomer> {
    return this.http.post<CorporateCustomer>(`${this.baseUrl}/corporate`, customer);
  }

  getCustomers(type: CustomerType): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${this.baseUrl}/${type}`);
  }

  getCustomerById(type: CustomerType, id: string): Observable<Customer> {
    return this.http.get<Customer>(`${this.baseUrl}/${type}/id/${id}`);
  }

  findCustomerByName(type: CustomerType, name: string): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${this.baseUrl}/${type}/name/${encodeURIComponent(name)}`);
  }
}
