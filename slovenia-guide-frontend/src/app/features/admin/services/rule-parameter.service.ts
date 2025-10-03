import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { RuleParameter } from '../../../core/models/rule-parameter.model';
import { environment } from '../../../../env/env';

@Injectable({
  providedIn: 'root',
})
export class RuleParameterService {
  private http = inject(HttpClient);
  private readonly API_URL = `${environment.apiBaseUrl}/rule-parameters`;

  getParameters(): Observable<RuleParameter[]> {
    return this.http.get<RuleParameter[]>(this.API_URL);
  }

  saveParameter(parameter: RuleParameter): Observable<RuleParameter> {
    return this.http.post<RuleParameter>(this.API_URL, parameter);
  }

  deleteParameter(paramKey: string): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${paramKey}`);
  }
}
