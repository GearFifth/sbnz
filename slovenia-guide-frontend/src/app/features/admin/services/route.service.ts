import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../env/env';
import {
  CreateRouteRequest,
  Route,
  UpdateRouteRequest,
} from '../../../core/models/route.model';

@Injectable({
  providedIn: 'root',
})
export class RouteService {
  private http = inject(HttpClient);
  private readonly API_URL = `${environment.apiBaseUrl}/routes`;

  getRoutes(): Observable<Route[]> {
    return this.http.get<Route[]>(this.API_URL);
  }

  createRoute(request: CreateRouteRequest): Observable<Route> {
    return this.http.post<Route>(this.API_URL, request);
  }

  updateRoute(request: UpdateRouteRequest): Observable<Route> {
    return this.http.put<Route>(`${this.API_URL}/${request.id}`, request);
  }

  deleteRoute(id: string): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
