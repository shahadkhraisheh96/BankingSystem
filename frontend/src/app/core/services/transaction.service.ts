import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transaction, DepositRequest, WithdrawRequest, TransferRequest } from '../models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private readonly baseUrl = 'http://localhost:8080/api/transactions';

  constructor(private http: HttpClient) {}

  deposit(req: DepositRequest): Observable<string> {
    return this.http.post(`${this.baseUrl}/deposit`, req, { responseType: 'text' });
  }

  withdraw(req: WithdrawRequest): Observable<string> {
    return this.http.post(`${this.baseUrl}/withdraw`, req, { responseType: 'text' });
  }

  transfer(req: TransferRequest): Observable<string> {
    return this.http.post(`${this.baseUrl}/transfer`, req, { responseType: 'text' });
  }

  getTransactions(accountNumber: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.baseUrl}/number/${accountNumber}`);
  }
}
