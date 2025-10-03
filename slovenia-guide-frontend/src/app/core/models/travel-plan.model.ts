import { Location } from './location.model';

export interface TravelPreferences {
  numberOfDays: number;
  budget: 'LOW' | 'MEDIUM' | 'HIGH';
  transport: 'CAR' | 'PUBLIC_TRANSPORT';
  fitnessLevel: 'LOW' | 'MEDIUM' | 'HIGH';
  interests: string[];
  travelMonth: number;
}

export interface ItineraryItem {
  day: number;
  location: Location;
  proposedActivity: string;
}

export interface Alert {
  message: string;
  planId: string;
  locationId?: string;
}

export interface TravelPlanResponse {
  planId: string;
  itinerary: ItineraryItem[];
  alerts: Alert[];
  tripType: string;
}
