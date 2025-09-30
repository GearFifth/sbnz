import { Location } from './location.model';

// Interface for the POST /find request payload
export interface DayTripRequest {
  baseLocationId: string;
  maxTravelTime: number;
}

// Interface for the POST /validate request payload
export interface ValidateDayTripRequest {
  baseLocationId: string;
  destinationLocationId: string;
  maxTravelTime: number;
}

// Interface for the object returned in the suggestion list
export interface DayTripSuggestion {
  location: Location;
  travelTimeFromBase: number;
}
