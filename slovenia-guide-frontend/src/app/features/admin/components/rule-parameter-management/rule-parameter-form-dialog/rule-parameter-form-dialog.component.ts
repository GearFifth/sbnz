import { Component, OnInit, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../../../shared/material.module';
import { RuleParameter } from '../../../../../core/models/rule-parameter.model';

@Component({
  selector: 'app-rule-parameter-form-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './rule-parameter-form-dialog.component.html',
})
export class RuleParameterFormDialogComponent implements OnInit {
  private fb = inject(FormBuilder);
  public dialogRef = inject(MatDialogRef<RuleParameterFormDialogComponent>);
  public data: { parameter: RuleParameter } = inject(MAT_DIALOG_DATA);

  parameterForm!: FormGroup;
  isEditMode = false;

  ngOnInit(): void {
    this.isEditMode = !!this.data.parameter;

    this.parameterForm = this.fb.group({
      paramKey: [{ value: '', disabled: this.isEditMode }, Validators.required],
      paramValue: [0, [Validators.required]],
    });

    if (this.isEditMode) {
      this.parameterForm.patchValue(this.data.parameter);
    }
  }

  onSave(): void {
    if (this.parameterForm.invalid) return;
    this.dialogRef.close(this.parameterForm.getRawValue()); // Use getRawValue() to include disabled fields
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
