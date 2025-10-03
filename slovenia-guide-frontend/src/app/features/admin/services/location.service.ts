import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../../env/env';
import {
  CreateLocationRequest,
  Location,
  UpdateLocationRequest,
} from '../../../core/models/location.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  private http = inject(HttpClient);
  private readonly API_URL = `${environment.apiBaseUrl}/locations`;

  getLocations(): Observable<Location[]> {
    return this.http.get<Location[]>(this.API_URL);
  }

  createLocation(request: CreateLocationRequest): Observable<Location> {
    return this.http.post<Location>(this.API_URL, request);
  }

  updateLocation(request: UpdateLocationRequest): Observable<Location> {
    return this.http.put<Location>(`${this.API_URL}/${request.id}`, request);
  }

  deleteLocation(id: string): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
