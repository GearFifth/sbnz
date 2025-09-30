import { Location } from './location.model';

// Payload to send to the backend to generate a plan
export interface TravelPreferences {
  numberOfDays: number;
  budget: 'LOW' | 'MEDIUM' | 'HIGH';
  transport: 'CAR' | 'PUBLIC_TRANSPORT';
  fitnessLevel: 'LOW' | 'MEDIUM' | 'HIGH';
  interests: string[];
  travelMonth: number;
}

// A single item in the generated itinerary
export interface ItineraryItem {
  day: number; // Renamed from dayNumber
  location: Location;
  proposedActivity: string; // Renamed from suggestedActivities
}

// An alert or warning for the user
export interface Alert {
  message: string;
  planId: string;
  locationId?: string;
}

// The complete response from the backend
export interface TravelPlanResponse {
  planId: string;
  itinerary: ItineraryItem[]; // Renamed from itineraryItems
  alerts: Alert[];
  tripType: string;
}
