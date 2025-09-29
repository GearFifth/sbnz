import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { LoaderService } from '../../../core/services/loader.service';

@Component({
  selector: 'app-loader',
  standalone: true,
  imports: [CommonModule, MatProgressSpinnerModule],
  templateUrl: './loader.component.html',
})
export class LoaderComponent {
  public loaderService = inject(LoaderService);
}
