import { Component, ElementRef, HostListener, Input, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-spotlight-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div 
      class="spotlight-card-wrapper" 
      [ngClass]="customClass"
      (mousemove)="onMouseMove($event)"
      (mouseenter)="onMouseEnter()"
      (mouseleave)="onMouseLeave()">
      <div 
        class="spotlight-layer" 
        [style.opacity]="isHovered() ? 1 : 0"
        [style.background]="spotlightBackground()">
      </div>
      <div class="spotlight-content">
        <ng-content></ng-content>
      </div>
    </div>
  `,
  styles: [`
    :host {
      display: block;
    }

    .spotlight-card-wrapper {
      position: relative;
      border-radius: var(--radius-md);
      overflow: hidden;
      background: var(--bg-card);
      border: 1px solid var(--border-subtle);
      transition: border-color 0.3s ease, box-shadow 0.3s ease;
    }

    .spotlight-card-wrapper:hover {
      border-color: rgba(99, 102, 241, 0.4);
      box-shadow: 0 12px 30px rgba(0, 0, 0, 0.45);
    }

    .spotlight-layer {
      pointer-events: none;
      position: absolute;
      inset: 0;
      transition: opacity 0.3s ease;
      z-index: 1;
    }

    .spotlight-content {
      position: relative;
      z-index: 2;
      height: 100%;
    }
  `]
})
export class SpotlightCardComponent {
  @Input() spotlightColor = 'rgba(99, 102, 241, 0.22)';
  @Input() customClass = '';
  @Input() spotlightSize = 350;

  isHovered = signal<boolean>(false);
  mouseX = signal<number>(0);
  mouseY = signal<number>(0);

  constructor(private el: ElementRef) {}

  onMouseMove(e: MouseEvent): void {
    const rect = this.el.nativeElement.getBoundingClientRect();
    this.mouseX.set(e.clientX - rect.left);
    this.mouseY.set(e.clientY - rect.top);
  }

  onMouseEnter(): void {
    this.isHovered.set(true);
  }

  onMouseLeave(): void {
    this.isHovered.set(false);
  }

  spotlightBackground(): string {
    return `radial-gradient(${this.spotlightSize}px circle at ${this.mouseX()}px ${this.mouseY()}px, ${this.spotlightColor}, transparent 80%)`;
  }
}
