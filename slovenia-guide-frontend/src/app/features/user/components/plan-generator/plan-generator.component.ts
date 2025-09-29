import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { TravelPlanService } from '../../services/travel-plan.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-plan-generator',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './plan-generator.component.html',
})
export class PlanGeneratorComponent {
  preferencesForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private travelPlanService: TravelPlanService
  ) {
    this.preferencesForm = this.fb.group({
      numberOfDays: [3, [Validators.required, Validators.min(1)]],
      budget: ['MEDIUM', Validators.required],
      transport: ['CAR', Validators.required],
      fitnessLevel: ['MEDIUM', Validators.required],
      interests: [['nature', 'history'], Validators.required],
      travelMonth: [7, Validators.required],
    });
  }

  generatePlan() {
    if (this.preferencesForm.valid) {
      console.log('Generating plan with:', this.preferencesForm.value);
      // this.travelPlanService.generatePlan(this.preferencesForm.value).subscribe(...)
    }
  }
}
