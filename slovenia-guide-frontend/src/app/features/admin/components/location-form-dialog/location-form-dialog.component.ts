import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MaterialModule } from '../../../../shared/material.module';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Location } from '../../../../core/models/location.model';

@Component({
  selector: 'app-location-form-dialog',
  standalone: true,
  imports: [CommonModule, MaterialModule, ReactiveFormsModule],
  templateUrl: './location-form-dialog.component.html',
})
export class LocationFormDialogComponent implements OnInit {
  private fb = inject(FormBuilder);
  public dialogRef = inject(MatDialogRef<LocationFormDialogComponent>);
  public data: { location: Location } = inject(MAT_DIALOG_DATA);

  locationForm!: FormGroup;
  isEditMode = false;
  months = Array.from({ length: 12 }, (_, i) => i + 1);
  tagInput = new FormControl('');

  ngOnInit(): void {
    this.isEditMode = !!this.data.location;

    this.locationForm = this.fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      region: ['', Validators.required],
      ticketPrice: [0, [Validators.required, Validators.min(0)]],
      visitTimeMinutes: [60, [Validators.required, Validators.min(10)]],
      requiredFitness: ['LOW', Validators.required],
      publicTransportAccessibility: ['GOOD', Validators.required],
      requiredEquipment: [''],
      description: [''],
      tags: [[] as string[]],
      seasonal: [false],
      openingMonth: [1],
      closingMonth: [12],
    });

    if (this.isEditMode) {
      const locationData = {
        ...this.data.location,
        tags: this.data.location.tags.map((tag) => tag.name),
      };
      this.locationForm.patchValue(locationData);
    }
  }

  addTag(event: Event): void {
    const input = event.target as HTMLInputElement;
    const value = input.value.trim();

    if (value) {
      const currentTags = this.locationForm.get('tags')?.value || [];
      if (!currentTags.includes(value)) {
        this.locationForm.get('tags')?.setValue([...currentTags, value]);
      }
    }
    input.value = '';
    event.preventDefault();
  }

  removeTag(tagToRemove: string): void {
    const currentTags = this.locationForm.get('tags')?.value || [];
    this.locationForm
      .get('tags')
      ?.setValue(currentTags.filter((tag: string) => tag !== tagToRemove));
  }

  onSave(): void {
    if (this.locationForm.invalid) {
      return;
    }
    const formValue = this.locationForm.value;

    // This object structure is common to both request types
    const baseRequestData = {
      ...formValue,
      tags: formValue.tags.map((name: string) => ({ name })),
    };

    if (this.isEditMode) {
      // If editing, add the ID to create an UpdateLocationRequest
      const updateRequest = { ...baseRequestData, id: this.data.location.id };
      this.dialogRef.close(updateRequest);
    } else {
      // Otherwise, it's a CreateLocationRequest
      this.dialogRef.close(baseRequestData);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
