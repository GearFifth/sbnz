import { Routes } from '@angular/router';
import { PlanGeneratorComponent } from './features/user/components/plan-generator/plan-generator.component';
import { AdminDashboardComponent } from './features/admin/components/admin-dashboard/admin-dashboard.component';
import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';
import { LocationManagementComponent } from './features/admin/components/location-management/location-management.component';
import { RouteManagementComponent } from './features/admin/components/route-management/route-management.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'home', component: PlanGeneratorComponent },
      {
        path: 'admin',
        canActivate: [adminGuard],
        children: [
          { path: 'dashboard', component: AdminDashboardComponent },
          { path: 'locations', component: LocationManagementComponent },
          { path: 'routes', component: RouteManagementComponent },
          { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
        ],
      },
    ],
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/home' }, // Wildcard redirect
];
