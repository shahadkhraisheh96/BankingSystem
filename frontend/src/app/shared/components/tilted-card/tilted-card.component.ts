import { Component, ElementRef, Input, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tilted-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div 
      class="tilt-container"
      [style.perspective.px]="perspective"
      (mousemove)="onMouseMove($event)"
      (mouseenter)="onMouseEnter()"
      (mouseleave)="onMouseLeave()">
      <div 
        class="tilt-inner"
        [style.transform]="transformStyle()"
        [style.transition]="isHovered() ? 'transform 0.1s ease-out' : 'transform 0.5s cubic-bezier(0.23, 1, 0.32, 1)'">
        <ng-content></ng-content>
        @if (glare) {
          <div 
            class="glare-layer" 
            [style.opacity]="isHovered() ? 0.25 : 0"
            [style.background]="glareBackground()">
          </div>
        }
      </div>
    </div>
  `,
  styles: [`
    :host {
      display: block;
    }

    .tilt-container {
      width: 100%;
      height: 100%;
      transform-style: preserve-3d;
    }

    .tilt-inner {
      width: 100%;
      height: 100%;
      position: relative;
      transform-style: preserve-3d;
      border-radius: var(--radius-md);
    }

    .glare-layer {
      pointer-events: none;
      position: absolute;
      inset: 0;
      border-radius: inherit;
      transition: opacity 0.3s ease;
      z-index: 10;
    }
  `]
})
export class TiltedCardComponent {
  @Input() maxTilt = 12; // degrees
  @Input() scale = 1.02;
  @Input() perspective = 1000;
  @Input() glare = true;

  isHovered = signal<boolean>(false);
  rotateX = signal<number>(0);
  rotateY = signal<number>(0);
  glareX = signal<number>(50);
  glareY = signal<number>(50);

  constructor(private el: ElementRef) {}

  onMouseMove(e: MouseEvent): void {
    const rect = this.el.nativeElement.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    const centerX = rect.width / 2;
    const centerY = rect.height / 2;

    const rotX = ((y - centerY) / centerY) * -this.maxTilt;
    const rotY = ((x - centerX) / centerX) * this.maxTilt;

    this.rotateX.set(rotX);
    this.rotateY.set(rotY);

    this.glareX.set((x / rect.width) * 100);
    this.glareY.set((y / rect.height) * 100);
  }

  onMouseEnter(): void {
    this.isHovered.set(true);
  }

  onMouseLeave(): void {
    this.isHovered.set(false);
    this.rotateX.set(0);
    this.rotateY.set(0);
  }

  transformStyle(): string {
    if (!this.isHovered()) {
      return 'rotateX(0deg) rotateY(0deg) scale(1)';
    }
    return `rotateX(${this.rotateX().toFixed(2)}deg) rotateY(${this.rotateY().toFixed(2)}deg) scale(${this.scale})`;
  }

  glareBackground(): string {
    return `radial-gradient(circle at ${this.glareX()}% ${this.glareY()}%, rgba(255, 255, 255, 0.8) 0%, transparent 60%)`;
  }
}
