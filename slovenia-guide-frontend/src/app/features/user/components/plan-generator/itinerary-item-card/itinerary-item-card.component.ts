import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ItineraryItem,
  Alert,
} from '../../../../../core/models/travel-plan.model';

@Component({
  selector: 'app-itinerary-item-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './itinerary-item-card.component.html',
})
export class ItineraryItemCardComponent {
  @Input({ required: true }) item!: ItineraryItem;
  @Input() alerts: Alert[] | undefined = [];
}
