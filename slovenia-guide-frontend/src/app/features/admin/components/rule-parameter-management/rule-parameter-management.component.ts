import { Component, OnInit, inject, signal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../../../../shared/material.module';
import { RuleParameter } from '../../../../core/models/rule-parameter.model';
import { RuleParameterService } from '../../services/rule-parameter.service';
import { RuleParameterFormDialogComponent } from './rule-parameter-form-dialog/rule-parameter-form-dialog.component';

@Component({
  selector: 'app-rule-parameter-management',
  standalone: true,
  imports: [CommonModule, MaterialModule],
  templateUrl: './rule-parameter-management.component.html',
})
export class RuleParameterManagementComponent implements OnInit {
  private parameterService = inject(RuleParameterService);
  private dialog = inject(MatDialog);

  parameters = signal<RuleParameter[]>([]);

  ngOnInit(): void {
    this.loadParameters();
  }

  loadParameters(): void {
    this.parameterService
      .getParameters()
      .subscribe((data) => this.parameters.set(data));
  }

  openFormDialog(parameter?: RuleParameter): void {
    const dialogRef = this.dialog.open(RuleParameterFormDialogComponent, {
      width: '500px',
      data: { parameter },
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result: RuleParameter | undefined) => {
      if (result) {
        this.parameterService
          .saveParameter(result)
          .subscribe(() => this.loadParameters());
      }
    });
  }

  deleteParameter(paramKey: string): void {
    if (confirm('Are you sure you want to delete this parameter?')) {
      this.parameterService
        .deleteParameter(paramKey)
        .subscribe(() => this.loadParameters());
    }
  }
}
