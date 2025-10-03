export interface Tag {
  id: string;
  name: string;
}

export interface Location {
  id: string;
  name: string;
  type: string;
  region: string;
  ticketPrice: number;
  visitTimeMinutes: number;
  requiredFitness: 'LOW' | 'MEDIUM' | 'HIGH';
  publicTransportAccessibility:
    | 'EXCELLENT'
    | 'GOOD'
    | 'COMPLICATED'
    | 'CAR_ONLY';
  requiredEquipment: string;
  description: string;
  tags: Tag[];
  seasonal: boolean;
  openingMonth: number;
  closingMonth: number;
}

export interface CreateLocationRequest extends Omit<Location, 'id' | 'tags'> {
  tags: { name: string }[];
}

export interface UpdateLocationRequest extends Omit<Location, 'tags'> {
  tags: { name: string }[];
}
