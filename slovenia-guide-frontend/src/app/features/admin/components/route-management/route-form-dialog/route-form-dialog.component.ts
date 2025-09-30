import { Component, OnInit, inject, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { LocationService } from '../../../services/location.service';
import { Location } from '../../../../../core/models/location.model';
import { Route } from '../../../../../core/models/route.model';

@Component({
  selector: 'app-route-form-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './route-form-dialog.component.html',
})
export class RouteFormDialogComponent implements OnInit {
  private fb = inject(FormBuilder);
  private locationService = inject(LocationService);
  public dialogRef = inject(MatDialogRef<RouteFormDialogComponent>);
  public data: { route: Route } = inject(MAT_DIALOG_DATA);

  isEditMode = false;
  routeForm!: FormGroup;
  locations = signal<Location[]>([]);

  ngOnInit(): void {
    this.isEditMode = !!this.data.route;
    this.loadLocations();

    this.routeForm = this.fb.group({
      locationAId: [
        { value: '', disabled: this.isEditMode },
        Validators.required,
      ],
      locationBId: [
        { value: '', disabled: this.isEditMode },
        Validators.required,
      ],
      travelTimeMinutes: [10, [Validators.required, Validators.min(1)]],
    });

    if (this.isEditMode) {
      this.routeForm.patchValue({
        locationAId: this.data.route.locationA.id,
        locationBId: this.data.route.locationB.id,
        travelTimeMinutes: this.data.route.travelTimeMinutes,
      });
    }
  }

  loadLocations(): void {
    this.locationService
      .getLocations()
      .subscribe((data) => this.locations.set(data));
  }

  onSave(): void {
    if (this.routeForm.invalid) return;

    if (this.isEditMode) {
      this.dialogRef.close({
        id: this.data.route.id,
        travelTimeMinutes: this.routeForm.value.travelTimeMinutes,
      });
    } else {
      if (this.routeForm.value.locationAId === this.routeForm.value.locationBId)
        return;
      this.dialogRef.close(this.routeForm.value);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
