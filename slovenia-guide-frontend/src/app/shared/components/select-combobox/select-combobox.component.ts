import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  EventEmitter,
  forwardRef,
  HostListener,
  inject,
  Input,
  OnChanges,
  Output,
  SimpleChanges,
  TemplateRef,
} from '@angular/core';
import {
  ControlValueAccessor,
  FormsModule,
  NG_VALUE_ACCESSOR,
} from '@angular/forms';

export type SelectComboboxSize = 'sm' | 'md' | 'lg';
export type SelectComboboxSortDirection = 'asc' | 'desc';

@Component({
  selector: 'app-select-combobox',
  standalone: true,
  imports: [CommonModule, FormsModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SelectComboboxComponent),
      multi: true,
    },
  ],
  templateUrl: './select-combobox.component.html',
  styleUrl: './select-combobox.component.scss',
})
export class SelectComboboxComponent
  implements ControlValueAccessor, OnChanges
{
  @Input() options: any[] = [];
  @Input() placeholder: string = 'Select an option...';
  @Input() optionDisplayKey: string = '';
  @Input() optionValueKey: string = '';
  @Input() optionDetailKey: string = '';
  @Input() optionTemplate: TemplateRef<any> | null = null;
  @Input() displayFn: ((option: any) => string) | null = null;
  @Input() size: SelectComboboxSize = 'md';
  @Input() emitObject: boolean = false;
  @Input() sortable: boolean = false;
  @Input() sortDirection: SelectComboboxSortDirection = 'asc';

  @Output() selectionChange = new EventEmitter<any>();

  public displayValue: string = '';
  public isOpen: boolean = false;
  public isClosing: boolean = false;
  public openUpward: boolean = false;
  private _formDisabled = false;
  private internalValue: any;
  private onChange = (_: any) => {};
  private onTouched = () => {};
  private elementRef = inject(ElementRef);

  public sortedOptions: any[] = [];

  public get isDisabled(): boolean {
    return this._formDisabled || !this.options || this.options.length <= 0;
  }

  public ngOnChanges(changes: SimpleChanges): void {
    if (changes['options'] || changes['sortable'] || changes['sortDirection']) {
      this.updateSortedOptions();
    }

    if (changes['options']) {
      if (this.internalValue !== null && this.internalValue !== undefined) {
        this.updateDisplayValue(this.internalValue);
      }
      const newOptions = changes['options'].currentValue;
      if (newOptions && newOptions.length === 1 && !this.internalValue) {
        this.selectOption(newOptions[0]);
      }
    }
  }

  private updateSortedOptions(): void {
    if (!this.options) {
      this.sortedOptions = [];
      return;
    }

    if (!this.sortable || !this.optionDisplayKey) {
      this.sortedOptions = this.options;
      return;
    }

    this.sortedOptions = [...this.options].sort((a, b) => {
      const valA = a[this.optionDisplayKey];
      const valB = b[this.optionDisplayKey];

      if (typeof valA === 'string' && typeof valB === 'string') {
        return this.sortDirection === 'asc'
          ? valA.localeCompare(valB)
          : valB.localeCompare(valA);
      }

      if (typeof valA === 'number' && typeof valB === 'number') {
        return this.sortDirection === 'asc' ? valA - valB : valB - valA;
      }

      return 0;
    });
  }

  public writeValue(value: any): void {
    this.internalValue = value;
    this.updateDisplayValue(value);
  }

  public registerOnChange(fn: any): void {
    this.onChange = fn;
  }
  public registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  public setDisabledState?(isDisabled: boolean): void {
    this._formDisabled = isDisabled;
  }

  private updateDisplayValue(value: any): void {
    if (value === null || value === undefined) {
      this.displayValue = '';
      return;
    }
    let selectedOption: any;
    if (this.emitObject && typeof value === 'object' && value !== null) {
      selectedOption = this.options.find(
        (opt) => opt[this.optionValueKey] === value[this.optionValueKey]
      );
    } else {
      selectedOption = this.options.find(
        (opt) => opt[this.optionValueKey] === value
      );
    }

    if (selectedOption) {
      this.displayValue = this.displayFn
        ? this.displayFn(selectedOption)
        : `${selectedOption[this.optionDisplayKey]}${
            this.optionDetailKey
              ? ' ' + selectedOption[this.optionDetailKey]
              : ''
          }`;
    } else {
      this.displayValue = '';
    }
  }

  public selectOption(option: any): void {
    const valueToEmit = option
      ? this.emitObject
        ? option
        : option[this.optionValueKey]
      : null;

    this.internalValue = valueToEmit;
    this.updateDisplayValue(valueToEmit);

    this.onChange(valueToEmit);
    this.selectionChange.emit(valueToEmit);
    this.onTouched();
    this.closeDropdown();
  }

  public toggleDropdown(): void {
    if (this.isDisabled) {
      return;
    }
    if (this.isOpen && !this.isClosing) {
      this.closeDropdown();
    } else {
      this.openDropdown();
    }
  }

  public openDropdown(): void {
    if (this.isOpen) return;
    this.checkPosition();
    this.isClosing = false;
    this.isOpen = true;
  }

  public closeDropdown(): void {
    if (!this.isOpen || this.isClosing) return;
    this.isClosing = true;
  }

  public onAnimationEnd(): void {
    if (this.isClosing) {
      this.isOpen = false;
      this.isClosing = false;
    }
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.closeDropdown();
    }
  }

  @HostListener('focusout', ['$event'])
  onFocusOut(event: FocusEvent): void {
    if (!this.elementRef.nativeElement.contains(event.relatedTarget as Node)) {
      this.closeDropdown();
    }
  }

  @HostListener('window:resize')
  onWindowResize(): void {
    if (this.isOpen) this.checkPosition();
  }

  private checkPosition(): void {
    const inputRect = this.elementRef.nativeElement.getBoundingClientRect();
    const spaceBelow = window.innerHeight - inputRect.bottom;
    this.openUpward = spaceBelow < 240;
  }
}
