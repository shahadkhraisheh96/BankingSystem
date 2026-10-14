import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-aurora-background',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="aurora-wrapper" [ngClass]="customClass">
      <div class="aurora-glow glow-1"></div>
      <div class="aurora-glow glow-2"></div>
      <div class="aurora-glow glow-3"></div>
      <div class="grid-overlay" *ngIf="showGrid"></div>
      <div class="aurora-content">
        <ng-content></ng-content>
      </div>
    </div>
  `,
  styles: [`
    :host {
      display: block;
      width: 100%;
      height: 100%;
    }

    .aurora-wrapper {
      position: relative;
      width: 100%;
      min-height: 100%;
      overflow: hidden;
      background-color: var(--bg-main);
    }

    .aurora-glow {
      position: absolute;
      border-radius: 50%;
      filter: blur(80px);
      opacity: 0.45;
      pointer-events: none;
      z-index: 1;
      will-change: transform;
    }

    .glow-1 {
      width: 500px;
      height: 500px;
      background: radial-gradient(circle, #6366f1 0%, rgba(99, 102, 241, 0) 70%);
      top: -100px;
      left: 15%;
      animation: float-1 16s ease-in-out infinite alternate;
    }

    .glow-2 {
      width: 600px;
      height: 600px;
      background: radial-gradient(circle, #06b6d4 0%, rgba(6, 182, 212, 0) 70%);
      top: 30%;
      right: 10%;
      animation: float-2 20s ease-in-out infinite alternate;
    }

    .glow-3 {
      width: 450px;
      height: 450px;
      background: radial-gradient(circle, #10b981 0%, rgba(16, 185, 129, 0) 70%);
      bottom: -80px;
      left: 30%;
      animation: float-3 18s ease-in-out infinite alternate;
    }

    .grid-overlay {
      position: absolute;
      inset: 0;
      background-image: 
        linear-gradient(to right, rgba(255, 255, 255, 0.03) 1px, transparent 1px),
        linear-gradient(to bottom, rgba(255, 255, 255, 0.03) 1px, transparent 1px);
      background-size: 40px 40px;
      pointer-events: none;
      z-index: 2;
    }

    .aurora-content {
      position: relative;
      z-index: 3;
      width: 100%;
      height: 100%;
    }

    @keyframes float-1 {
      0% { transform: translate(0, 0) scale(1); }
      100% { transform: translate(80px, 60px) scale(1.15); }
    }

    @keyframes float-2 {
      0% { transform: translate(0, 0) scale(1.1); }
      100% { transform: translate(-100px, -50px) scale(0.95); }
    }

    @keyframes float-3 {
      0% { transform: translate(0, 0) scale(0.95); }
      100% { transform: translate(60px, -80px) scale(1.1); }
    }
  `]
})
export class AuroraBackgroundComponent {
  @Input() showGrid = true;
  @Input() customClass = '';
}
