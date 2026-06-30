import { CommonModule, NgClass, NgIf } from '@angular/common';
import { Component, forwardRef, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl, AbstractControl, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

interface IdropdownMeta {
  mainLabel: string;
  subLabel: string;
  imgSrc: string;
  imgAlt: string;
  defaultValue: string;
  searchField: 'mainLabel' | 'subLabel';
  valueField: string;
  hasLabel: boolean;
}
@Component({
  selector: 'app-tp-dropdown',
  templateUrl: './tp-dropdown.component.html',
  styleUrls: ['./tp-dropdown.component.scss'],
  encapsulation: ViewEncapsulation.None,
  standalone: true,
  imports: [CommonModule, FormsModule,NgClass,NgIf,NgbDropdownModule, ReactiveFormsModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TpDropdownComponent),
      multi: true,
    },
  ],
})
export class TpDropdownComponent implements ControlValueAccessor, OnInit {
  constructor() {}
  ngOnInit(): void {
    if (!this.placeholder) {
      this.placeholder = this.label;
    }
  }
  @Input() data!: any[];
  @Input() meta!: IdropdownMeta;
  @Input() isLoading!: boolean;
  filteredData: any[];

  @Input() label!: string;
  @Input() type: 'text' | 'password' | 'email' | 'number' | 'textarea' = 'text';
  @Input() rows: number = 3;
  @Input() placeholder: string;
  @Input() control!: FormControl | AbstractControl;
  public value: string;

  public id = Math.random();

  // Function to call when change
  public onChange: (value: any) => void;
  public onTouched: () => void;

  public isDisabled: boolean;
  selectItem(val) {
    this.onTouched();
    this.value = val;
    this.onChange(val);
  }
  searchItem(e: any) {
    if (e.target.value) {
      const x: string = e.target.value.toString().trim();
      this.filteredData = this.data.filter((d) => d[this.meta.searchField].includes(x.toLowerCase()));
    } else {
      this.filteredData = this.data;
    }
  }

  writeValue(value: any): void {
    this.value = value ?? this.data[this.meta.defaultValue] ?? '';
  }
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }
  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }
  setDisabledState?(isDisabled: boolean): void {
    this.isDisabled = isDisabled;
  }

  public get inputClass() {
    return this.control
      ? {
          'is-valid': this.control.touched && !this.control.invalid,
          'is-invalid': this.control.touched && this.control.invalid,
        }
      : null;
  }

  private get errors() {
    const errors = this.control.errors;
    return {
      required: `${this.label || 'Field'} is Required`,
      email: `${this.label || 'Field'} should be a valid email`,
      min: `${this.label || 'Field'} should be a minimum of ${errors ? errors['min']?.min : ''}`,
      max: `${this.label || 'Field'} should be a maximum of ${errors ? errors['max']?.max : ''}`,
      maxlength: `${this.label || 'Field'} should not be more than ${errors ? errors['maxlength']?.requiredLength : ''} characters`,
      minlength: `${this.label || 'Field'} should be atleast ${errors ? errors['minlength']?.requiredLength : ''} characters`,
    };
  }
  public get isRequired() {
    return this.control.hasValidator(Validators.required) ? '*' : '';
  }

  public get Errors(): { type; message }[] {
    if (!this.control.errors) {
      return [];
    }

    return Object.keys(this.control.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errors[errorType],
      };
    });
  }
}
