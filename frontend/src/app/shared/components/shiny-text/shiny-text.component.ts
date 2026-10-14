import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-shiny-text',
  standalone: true,
  imports: [CommonModule],
  template: `
    <span class="shiny-text" [ngClass]="customClass" [style.animationDuration]="speed + 's'">
      <ng-content></ng-content>{{ text }}
    </span>
  `,
  styles: [`
    .shiny-text {
      color: #b5b5b5a4;
      background: linear-gradient(
        120deg,
        rgba(255, 255, 255, 0) 40%,
        rgba(255, 255, 255, 0.95) 50%,
        rgba(255, 255, 255, 0) 60%
      );
      background-size: 200% 100%;
      -webkit-background-clip: text;
      background-clip: text;
      display: inline-block;
      animation: shine 4s linear infinite;
    }

    @keyframes shine {
      0% { background-position: 100% 0; }
      100% { background-position: -100% 0; }
    }
  `]
})
export class ShinyTextComponent {
  @Input() text = '';
  @Input() speed = 4;
  @Input() customClass = '';
}
