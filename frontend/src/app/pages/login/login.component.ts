import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  isLoginMode = signal<boolean>(true);
  loading = signal<boolean>(false);
  errorMessage = signal<string | null>(null);
  successMessage = signal<string | null>(null);

  // Form Fields
  userName = '';
  email = '';
  password = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/dashboard']);
    }
  }

  toggleMode(): void {
    this.isLoginMode.update(val => !val);
    this.errorMessage.set(null);
    this.successMessage.set(null);
  }

  onSubmit(): void {
    this.errorMessage.set(null);
    this.successMessage.set(null);

    if (this.isLoginMode()) {
      if (!this.email || !this.password) {
        this.errorMessage.set('Please provide both email and password.');
        return;
      }

      this.loading.set(true);
      this.authService.login({ email: this.email, password: this.password }).subscribe({
        next: () => {
          this.loading.set(false);
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          this.loading.set(false);
          this.errorMessage.set(err.error?.message || err.error || 'Login failed. Please verify your credentials.');
        }
      });
    } else {
      if (!this.userName || !this.email || !this.password) {
        this.errorMessage.set('All fields are required for sign up.');
        return;
      }

      this.loading.set(true);
      this.authService.signup({ userName: this.userName, email: this.email, password: this.password }).subscribe({
        next: () => {
          this.loading.set(false);
          this.successMessage.set('Account registered successfully! You can now sign in.');
          this.isLoginMode.set(true);
          this.password = '';
        },
        error: (err) => {
          this.loading.set(false);
          this.errorMessage.set(err.error?.message || err.error || 'Registration failed. Username may already exist.');
        }
      });
    }
  }
}
