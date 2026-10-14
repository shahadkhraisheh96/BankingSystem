import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, SignupRequest, User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = 'http://localhost:8080/api/auth';
  private readonly tokenKey = 'bank_jwt_token';
  private readonly userKey = 'bank_user_email';

  readonly token = signal<string | null>(this.getStoredToken());
  readonly userEmail = signal<string | null>(this.getStoredEmail());
  readonly isAuthenticated = signal<boolean>(!!this.getStoredToken());

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<string> {
    return this.http.post(`${this.baseUrl}/login`, credentials, { responseType: 'text' }).pipe(
      tap(token => {
        this.saveAuth(token, credentials.email);
      })
    );
  }

  signup(userData: SignupRequest): Observable<string> {
    const payload: User = {
      userName: userData.userName,
      email: userData.email,
      password: userData.password
    };
    return this.http.post(`${this.baseUrl}/signup`, payload, { responseType: 'text' });
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
    this.token.set(null);
    this.userEmail.set(null);
    this.isAuthenticated.set(false);
  }

  getToken(): string | null {
    return this.token();
  }

  private saveAuth(token: string, email: string): void {
    localStorage.setItem(this.tokenKey, token);
    localStorage.setItem(this.userKey, email);
    this.token.set(token);
    this.userEmail.set(email);
    this.isAuthenticated.set(true);
  }

  private getStoredToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  private getStoredEmail(): string | null {
    return localStorage.getItem(this.userKey);
  }
}
