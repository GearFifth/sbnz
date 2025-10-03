import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthResponse, UserResponse } from '../models/auth.model';
import { environment } from '../../../env/env';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  private readonly API_BASE_URL = `${environment.apiBaseUrl}/auth`;

  register(details: {
    email: string;
    password: string;
  }): Observable<UserResponse> {
    return this.http.post<UserResponse>(
      `${this.API_BASE_URL}/register`,
      details
    );
  }

  login(credentials: {
    email: string;
    password: string;
  }): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.API_BASE_URL}/login`, credentials)
      .pipe(tap((response) => this.saveToken(response.token)));
  }

  saveToken(token: string): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.setItem('authToken', token);
    }
  }

  logout(): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem('authToken');
    }
  }

  isLoggedIn(): boolean {
    if (typeof localStorage === 'undefined') {
      return false;
    }
    const token = localStorage.getItem('authToken');
    return !!token;
  }

  isAdmin(): boolean {
    if (!this.isLoggedIn()) {
      return false;
    }

    try {
      const token = localStorage.getItem('authToken') as string;
      const payload = JSON.parse(atob(token.split('.')[1]));

      return (
        Array.isArray(payload.role) &&
        payload.role.some((r: { authority: string }) => r.authority === 'ADMIN')
      );
    } catch (e) {
      console.error('Error decoding token:', e);
      return false;
    }
  }
}
