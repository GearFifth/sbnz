import { Location } from './location.model';

export interface Route {
  id: string;
  locationA: Location;
  locationB: Location;
  travelTimeMinutes: number;
}

export interface CreateRouteRequest {
  locationAId: string;
  locationBId: string;
  travelTimeMinutes: number;
}

export interface UpdateRouteRequest {
  id: string;
  travelTimeMinutes: number;
}
