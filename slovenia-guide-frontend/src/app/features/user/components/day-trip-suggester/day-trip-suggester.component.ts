import { Component, OnInit, inject, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../../shared/material.module';
import { DayTripService } from '../../services/day-trip.service';
import { LocationService } from '../../../admin/services/location.service';
import { DayTripSuggestion } from '../../../../core/models/day-trip.model';
import { Location } from '../../../../core/models/location.model';

@Component({
  selector: 'app-day-trip-suggester',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './day-trip-suggester.component.html',
})
export class DayTripSuggesterComponent implements OnInit {
  private fb = inject(FormBuilder);
  private dayTripService = inject(DayTripService);
  private locationService = inject(LocationService);

  // Forms
  findTripsForm: FormGroup;
  validateTripForm: FormGroup;

  // Signals for data
  allLocations = signal<Location[]>([]);
  suggestions = signal<DayTripSuggestion[] | null>(null);
  validationResult = signal<{ reachable: boolean; message: string } | null>(
    null
  );

  constructor() {
    this.findTripsForm = this.fb.group({
      baseLocationId: ['', Validators.required],
      maxTravelTime: [60, [Validators.required, Validators.min(10)]],
    });

    this.validateTripForm = this.fb.group({
      baseLocationId: ['', Validators.required],
      destinationLocationId: ['', Validators.required],
      maxTravelTime: [60, [Validators.required, Validators.min(10)]],
    });
  }

  ngOnInit(): void {
    // Učitavamo sve lokacije da bismo popunili padajuće menije
    this.locationService.getLocations().subscribe((locations) => {
      this.allLocations.set(locations);
    });
  }

  onFindTrips(): void {
    if (this.findTripsForm.invalid) return;

    this.dayTripService
      .findDayTrips(this.findTripsForm.value)
      .subscribe((results) => {
        this.suggestions.set(results);
      });
  }

  onValidateTrip(): void {
    if (this.validateTripForm.invalid) return;

    const formValue = this.validateTripForm.value;
    if (formValue.baseLocationId === formValue.destinationLocationId) {
      this.validationResult.set({
        reachable: false,
        message: 'Startna i odredisna lokacija ne mogu biti iste.',
      });
      return;
    }

    this.dayTripService.validateDayTrip(formValue).subscribe((isReachable) => {
      const message = isReachable
        ? 'Destinacija je dostižna u zadatom vremenu.'
        : 'Destinacija nije dostižna u zadatom vremenu.';
      this.validationResult.set({ reachable: isReachable, message });
    });
  }
}
