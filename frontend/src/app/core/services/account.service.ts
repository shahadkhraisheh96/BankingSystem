import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from '../models/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private readonly baseUrl = 'http://localhost:8080/api/accounts';

  constructor(private http: HttpClient) {}

  createAccount(account: Account): Observable<Account> {
    return this.http.post<Account>(`${this.baseUrl}/add`, account);
  }

  getAccountsByCustomer(customerId: string): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.baseUrl}/customer/${customerId}`);
  }

  getAccountByNumber(accountNumber: string): Observable<Account> {
    return this.http.get<Account>(`${this.baseUrl}/number/${accountNumber}`);
  }
}
