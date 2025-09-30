import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import {
  DayTripRequest,
  DayTripSuggestion,
  ValidateDayTripRequest,
} from '../../../core/models/day-trip.model';
import { environment } from '../../../../env/env';

@Injectable({
  providedIn: 'root',
})
export class DayTripService {
  private http = inject(HttpClient);
  private readonly API_URL = `${environment.apiBaseUrl}/day-trips`;

  findDayTrips(request: DayTripRequest): Observable<DayTripSuggestion[]> {
    return this.http.post<DayTripSuggestion[]>(`${this.API_URL}/find`, request);
  }

  validateDayTrip(request: ValidateDayTripRequest): Observable<boolean> {
    return this.http.post<boolean>(`${this.API_URL}/validate`, request);
  }
}
