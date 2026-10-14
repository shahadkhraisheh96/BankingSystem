import { Component, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterModule, Router } from '@angular/router';
import { AuthService } from './core/services/auth.service';

import { ShinyTextComponent } from './shared/components/shiny-text/shiny-text.component';
import { DecryptedTextComponent } from './shared/components/decrypted-text/decrypted-text.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule, 
    RouterOutlet, 
    RouterModule,
    ShinyTextComponent,
    DecryptedTextComponent
  ],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  readonly isAuthenticated = computed(() => this.authService.isAuthenticated());
  readonly userEmail = computed(() => this.authService.userEmail() || 'Executive Officer');

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
