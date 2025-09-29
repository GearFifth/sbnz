import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { ThemeService } from '../../../core/services/theme.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
})
export class NavbarComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  themeService = inject(ThemeService);

  isAdmin = false;

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
