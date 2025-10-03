import { Component, inject, OnInit, signal } from '@angular/core';
import { LocationService } from '../../services/location.service';
import { finalize } from 'rxjs';
import {
  CreateLocationRequest,
  UpdateLocationRequest,
  Location,
} from '../../../../core/models/location.model';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../../shared/material.module';
import { MatDialog } from '@angular/material/dialog';
import { LocationFormDialogComponent } from './location-form-dialog/location-form-dialog.component';

@Component({
  selector: 'app-location-management',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './location-management.component.html',
})
export class LocationManagementComponent implements OnInit {
  private locationService = inject(LocationService);
  private dialog = inject(MatDialog);

  locations = signal<Location[]>([]);

  ngOnInit(): void {
    this.loadLocations();
  }

  loadLocations(): void {
    this.locationService
      .getLocations()
      .subscribe((data) => this.locations.set(data));
  }

  openFormDialog(location?: Location): void {
    const dialogRef = this.dialog.open(LocationFormDialogComponent, {
      width: '600px',
      data: { location },
      disableClose: true,
    });

    dialogRef
      .afterClosed()
      .subscribe(
        (result: CreateLocationRequest | UpdateLocationRequest | undefined) => {
          if (result) {
            if (location) {
              this.locationService
                .updateLocation(result as UpdateLocationRequest)
                .subscribe(() => this.loadLocations());
            } else {
              this.locationService
                .createLocation(result as CreateLocationRequest)
                .subscribe(() => this.loadLocations());
            }
          }
        }
      );
  }

  deleteLocation(id: string): void {
    if (confirm('Are you sure you want to delete this location?')) {
      this.locationService
        .deleteLocation(id)
        .subscribe(() => this.loadLocations());
    }
  }
}
