import { Component, OnInit, inject, signal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../../shared/material.module';
import { RouteService } from '../../services/route.service';
import { RouteFormDialogComponent } from './route-form-dialog/route-form-dialog.component';
import {
  CreateRouteRequest,
  Route,
  UpdateRouteRequest,
} from '../../../../core/models/route.model';

@Component({
  selector: 'app-route-management',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './route-management.component.html',
})
export class RouteManagementComponent implements OnInit {
  private routeService = inject(RouteService);
  private dialog = inject(MatDialog);

  routes = signal<Route[]>([]);

  ngOnInit(): void {
    this.loadRoutes();
  }

  loadRoutes(): void {
    this.routeService.getRoutes().subscribe((data) => this.routes.set(data));
  }

  openFormDialog(route?: Route): void {
    const dialogRef = this.dialog.open(RouteFormDialogComponent, {
      width: '500px',
      data: { route },
      disableClose: true,
    });

    dialogRef
      .afterClosed()
      .subscribe(
        (result: CreateRouteRequest | UpdateRouteRequest | undefined) => {
          if (result) {
            if (route) {
              this.routeService
                .updateRoute(result as UpdateRouteRequest)
                .subscribe(() => this.loadRoutes());
            } else {
              this.routeService
                .createRoute(result as CreateRouteRequest)
                .subscribe(() => this.loadRoutes());
            }
          }
        }
      );
  }

  deleteRoute(id: string): void {
    if (confirm('Are you sure you want to delete this route?')) {
      this.routeService.deleteRoute(id).subscribe(() => this.loadRoutes());
    }
  }
}
