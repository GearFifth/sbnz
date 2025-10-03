import { Routes } from '@angular/router';
import { PlanGeneratorComponent } from './features/user/components/plan-generator/plan-generator.component';
import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';
import { LocationManagementComponent } from './features/admin/components/location-management/location-management.component';
import { RouteManagementComponent } from './features/admin/components/route-management/route-management.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { DayTripSuggesterComponent } from './features/user/components/day-trip-suggester/day-trip-suggester.component';
import { RuleParameterManagementComponent } from './features/admin/components/rule-parameter-management/rule-parameter-management.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'home', component: PlanGeneratorComponent },
      { path: 'day-trips', component: DayTripSuggesterComponent },
      {
        path: 'admin',
        canActivate: [adminGuard],
        children: [
          { path: 'locations', component: LocationManagementComponent },
          { path: 'routes', component: RouteManagementComponent },
          { path: 'parameters', component: RuleParameterManagementComponent },
          { path: '', redirectTo: 'locations', pathMatch: 'full' },
        ],
      },
    ],
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/home' }, // Wildcard redirect
];
