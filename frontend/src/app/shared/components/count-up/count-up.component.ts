import { Component, Input, OnInit, OnChanges, SimpleChanges, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-count-up',
  standalone: true,
  imports: [CommonModule],
  template: `
    <span class="count-up" [ngClass]="customClass">
      {{ prefix }}{{ displayValue() }}{{ suffix }}
    </span>
  `,
  styles: [`
    .count-up {
      display: inline-block;
      font-variant-numeric: tabular-nums;
    }
  `]
})
export class CountUpComponent implements OnInit, OnChanges {
  @Input() to = 0;
  @Input() from = 0;
  @Input() duration = 1200; // ms
  @Input() decimals = 2;
  @Input() prefix = '';
  @Input() suffix = '';
  @Input() customClass = '';

  displayValue = signal<string>('0.00');

  ngOnInit(): void {
    this.animateCount(this.from, this.to);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['to'] && !changes['to'].firstChange) {
      const prev = changes['to'].previousValue || 0;
      this.animateCount(prev, this.to);
    }
  }

  private animateCount(start: number, end: number): void {
    const startTime = performance.now();
    const duration = this.duration;

    const step = (currentTime: number) => {
      const elapsed = currentTime - startTime;
      const progress = Math.min(elapsed / duration, 1);

      // easeOutExpo
      const ease = progress === 1 ? 1 : 1 - Math.pow(2, -10 * progress);
      const current = start + (end - start) * ease;

      this.displayValue.set(this.formatNumber(current));

      if (progress < 1) {
        requestAnimationFrame(step);
      } else {
        this.displayValue.set(this.formatNumber(end));
      }
    };

    requestAnimationFrame(step);
  }

  private formatNumber(num: number): string {
    return num.toLocaleString('en-US', {
      minimumFractionDigits: this.decimals,
      maximumFractionDigits: this.decimals
    });
  }
}
