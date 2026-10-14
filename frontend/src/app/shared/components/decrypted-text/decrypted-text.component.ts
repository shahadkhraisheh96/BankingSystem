import { Component, Input, OnInit, OnChanges, SimpleChanges, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-decrypted-text',
  standalone: true,
  imports: [CommonModule],
  template: `
    <span class="decrypted-text" [ngClass]="customClass">
      {{ displayText() }}
    </span>
  `,
  styles: [`
    .decrypted-text {
      display: inline-block;
      font-family: inherit;
      white-space: pre-wrap;
    }
  `]
})
export class DecryptedTextComponent implements OnInit, OnChanges {
  @Input() text = '';
  @Input() speed = 35;
  @Input() maxIterations = 10;
  @Input() characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+~|}{[]:;?><,./-=';
  @Input() customClass = '';

  displayText = signal<string>('');
  private intervalId: any = null;

  ngOnInit(): void {
    this.startDecryption();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['text'] && !changes['text'].firstChange) {
      this.startDecryption();
    }
  }

  startDecryption(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }

    if (!this.text) {
      this.displayText.set('');
      return;
    }

    let iteration = 0;
    const target = this.text;

    this.intervalId = setInterval(() => {
      const resolvedChars = Math.floor(iteration / 3);

      const scrambled = target
        .split('')
        .map((char, index) => {
          if (char === ' ') return ' ';
          if (index < resolvedChars) {
            return target[index];
          }
          return this.characters[Math.floor(Math.random() * this.characters.length)];
        })
        .join('');

      this.displayText.set(scrambled);

      if (resolvedChars >= target.length) {
        this.displayText.set(target);
        clearInterval(this.intervalId);
      }

      iteration++;
    }, this.speed);
  }
}
