import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

import { AuroraBackgroundComponent } from '../../shared/components/aurora-background/aurora-background.component';
import { SpotlightCardComponent } from '../../shared/components/spotlight-card/spotlight-card.component';
import { ShinyTextComponent } from '../../shared/components/shiny-text/shiny-text.component';
import { DecryptedTextComponent } from '../../shared/components/decrypted-text/decrypted-text.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    AuroraBackgroundComponent, 
    SpotlightCardComponent, 
    ShinyTextComponent, 
    DecryptedTextComponent
  ],
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
          this.errorMessage.set(this.extractErrorMessage(err, 'Sign in failed'));
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
          this.errorMessage.set(this.extractErrorMessage(err, 'Registration failed'));
        }
      });
    }
  }

  private extractErrorMessage(err: any, fallbackPrefix: string): string {
    if (!err) return `${fallbackPrefix}. Please try again.`;

    // Backend not running / connection refused
    if (err.status === 0) {
      return 'Cannot connect to backend server. Please verify Spring Boot is running on port 8080 (run .\\run-backend.ps1).';
    }

    // String error body (e.g. JSON returned with responseType: text)
    if (typeof err.error === 'string') {
      try {
        const parsed = JSON.parse(err.error);
        if (parsed?.message) return parsed.message;
      } catch {
        if (err.error.trim().length > 0 && !err.error.includes('<html')) {
          return err.error;
        }
      }
    }

    // Object error body
    if (err.error && typeof err.error === 'object' && !(err.error instanceof ProgressEvent)) {
      if (err.error.message) return err.error.message;
      if (err.error.error) return err.error.error;
    }

    // Status code fallbacks
    if (err.status === 400 || err.status === 401) {
      return 'Invalid email or password. Please verify your credentials.';
    }

    if (err.status === 403) {
      return 'Access denied. You do not have permission.';
    }

    if (err.status >= 500) {
      return 'Server error during authentication. Please check backend logs.';
    }

    return `${fallbackPrefix}. Please verify your credentials.`;
  }
}
