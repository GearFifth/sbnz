import {
  Component,
  OnDestroy,
  OnInit,
  computed,
  inject,
  signal,
} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
  FormArray,
  FormControl,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../../shared/material.module';
import { TravelPlanService } from '../../services/travel-plan.service';
import {
  Alert,
  ItineraryItem,
  TravelPlanResponse,
} from '../../../../core/models/travel-plan.model';
import { Tag } from '../../../../core/models/location.model';
import { TagService } from '../../services/tag.service';

@Component({
  selector: 'app-plan-generator',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MaterialModule],
  templateUrl: './plan-generator.component.html',
})
export class PlanGeneratorComponent implements OnInit, OnDestroy {
  private fb = inject(FormBuilder);
  private travelPlanService = inject(TravelPlanService);
  private tagService = inject(TagService);
  private alertPollingInterval: any;

  preferencesForm: FormGroup;
  allInterests = signal<Tag[]>([]);
  interestInput = new FormControl('');
  isDropdownVisible = signal(false);
  private interestFilterSignal = signal<string>('');

  readonly months = [
    { value: 1, name: 'January' },
    { value: 2, name: 'February' },
    { value: 3, name: 'March' },
    { value: 4, name: 'April' },
    { value: 5, name: 'May' },
    { value: 6, name: 'June' },
    { value: 7, name: 'July' },
    { value: 8, name: 'August' },
    { value: 9, name: 'September' },
    { value: 10, name: 'October' },
    { value: 11, name: 'November' },
    { value: 12, name: 'December' },
  ];

  plan = signal<TravelPlanResponse | null>(null);

  generalAlerts = computed(
    () => this.plan()?.alerts.filter((a) => !a.locationId) || []
  );

  locationAlertsMap = computed(() => {
    const alertsMap = new Map<string, Alert[]>();
    const p = this.plan();
    if (!p) return alertsMap;

    p.alerts.forEach((alert) => {
      if (alert.locationId) {
        const existing = alertsMap.get(alert.locationId) || [];
        alertsMap.set(alert.locationId, [...existing, alert]);
      }
    });
    return alertsMap;
  });

  filteredInterests = computed(() => {
    const filterValue = this.interestFilterSignal().toLowerCase();
    const selectedInterests = this.interestsFormArray.value as string[];
    return this.allInterests().filter(
      (interest) =>
        !selectedInterests.includes(interest.name) &&
        interest.name.toLowerCase().includes(filterValue)
    );
  });

  get interestsFormArray() {
    return this.preferencesForm.get('interests') as FormArray;
  }

  planByDay = computed(() => {
    const p = this.plan();
    if (!p) return [];
    const grouped = p.itinerary.reduce((acc, item) => {
      (acc[item.day] = acc[item.day] || []).push(item);
      return acc;
    }, {} as { [key: number]: ItineraryItem[] });
    return Object.keys(grouped).map((day) => ({
      day: Number(day),
      items: grouped[Number(day)],
    }));
  });

  constructor() {
    this.preferencesForm = this.fb.group({
      numberOfDays: [
        3,
        [Validators.required, Validators.min(1), Validators.max(14)],
      ],
      budget: ['MEDIUM', Validators.required],
      transport: ['CAR', Validators.required],
      fitnessLevel: ['MEDIUM', Validators.required],
      travelMonth: [7, Validators.required],
      interests: this.fb.array(
        [],
        [Validators.required, Validators.minLength(1)]
      ),
    });
  }

  ngOnInit(): void {
    this.tagService.getTags().subscribe((tags) => {
      this.allInterests.set(tags);
    });

    this.interestInput.valueChanges.subscribe((value) => {
      this.interestFilterSignal.set(value || '');
    });
  }

  addInterest(interestName: string): void {
    if (interestName && !this.interestsFormArray.value.includes(interestName)) {
      this.interestsFormArray.push(this.fb.control(interestName));
    }
    this.interestInput.setValue('');
    setTimeout(() => this.isDropdownVisible.set(false), 100);
  }

  removeInterest(index: number): void {
    this.interestsFormArray.removeAt(index);
  }

  generatePlan(): void {
    if (this.preferencesForm.invalid) {
      return;
    }
    this.travelPlanService
      .generatePlan(this.preferencesForm.value)
      .subscribe((response) => {
        this.plan.set(response);
        this.startAlertPolling(response.planId);
      });
  }

  startOver(): void {
    this.plan.set(null);
    clearInterval(this.alertPollingInterval);
    this.interestsFormArray.clear();
    this.preferencesForm.reset({
      numberOfDays: 3,
      budget: 'MEDIUM',
      transport: 'CAR',
      fitnessLevel: 'MEDIUM',
      travelMonth: 7,
    });
  }

  private startAlertPolling(planId: string): void {
    this.alertPollingInterval = setInterval(() => {
      this.travelPlanService
        .getCriticalAlerts(planId)
        .subscribe((newAlerts) => {
          if (newAlerts && newAlerts.length > 0) {
            this.plan.update((currentPlan) => {
              if (!currentPlan) return null;
              const existingAlertMessages = new Set(
                currentPlan.alerts.map((a) => a.message)
              );
              const uniqueNewAlerts = newAlerts.filter(
                (a) => !existingAlertMessages.has(a.message)
              );

              if (uniqueNewAlerts.length > 0) {
                return {
                  ...currentPlan,
                  alerts: [...currentPlan.alerts, ...uniqueNewAlerts],
                };
              }
              return currentPlan;
            });
          }
        });
    }, 10000);
  }

  ngOnDestroy(): void {
    clearInterval(this.alertPollingInterval);
  }
}
