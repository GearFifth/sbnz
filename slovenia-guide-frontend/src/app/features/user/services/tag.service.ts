import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../env/env';
import { Tag } from '../../../core/models/location.model';

@Injectable({
  providedIn: 'root',
})
export class TagService {
  private http = inject(HttpClient);
  private readonly API_URL = `${environment.apiBaseUrl}/tags`;

  getTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(this.API_URL);
  }
}
