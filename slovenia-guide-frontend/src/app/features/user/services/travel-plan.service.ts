import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import {
  Alert,
  TravelPlanResponse,
  TravelPreferences,
} from '../../../core/models/travel-plan.model';
import { environment } from '../../../../env/env';

@Injectable({
  providedIn: 'root',
})
export class TravelPlanService {
  private http = inject(HttpClient);
  private readonly API_URL = `${environment.apiBaseUrl}/travel-plan`;

  generatePlan(preferences: TravelPreferences): Observable<TravelPlanResponse> {
    return this.http.post<TravelPlanResponse>(
      `${this.API_URL}/generate`,
      preferences
    );
  }

  getCriticalAlerts(planId: string): Observable<Alert[]> {
    return this.http.get<Alert[]>(`${this.API_URL}/${planId}/critical-alerts`);
  }
}
