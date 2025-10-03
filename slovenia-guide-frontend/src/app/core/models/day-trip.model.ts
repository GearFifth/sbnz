import { Location } from './location.model';

export interface DayTripRequest {
  baseLocationId: string;
  maxTravelTime: number;
}

export interface ValidateDayTripRequest {
  baseLocationId: string;
  destinationLocationId: string;
  maxTravelTime: number;
}

export interface DayTripSuggestion {
  location: Location;
  travelTimeFromBase: number;
}
