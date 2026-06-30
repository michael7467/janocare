import { CommonModule, NgClass, NgFor, NgIf } from '@angular/common';
import { Component, forwardRef, Input, NO_ERRORS_SCHEMA, OnInit, ViewEncapsulation } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl, AbstractControl, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbDropdownModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

export interface IdropdownMeta {
  mainLabel: string;
  subLabel: string;
  imgSrcBase?: string;
  imgAltField?: string;
  defaultValue: string;
  searchField: 'mainLabel' | 'subLabel';
  valueField: string;
  hasLabel: boolean;
  hasImage?: boolean;
  label?: string;
  viewValidationMessages: boolean;
}

@Component({
  selector: 'app-tp-dropdown-simple',
  templateUrl: './tp-dropdown-simple.component.html',
  styleUrls: ['./tp-dropdown-simple.component.scss'],
  // encapsulation: ViewEncapsulation.None,
  encapsulation: ViewEncapsulation.None,
  standalone: true,
  imports: [CommonModule,NgIf,NgFor,NgClass, FormsModule,NgbPaginationModule,NgClass,NgIf,NgbDropdownModule, ReactiveFormsModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TpDropdownSimpleComponent),
      multi: true,
    },
  ],
  schemas: [NO_ERRORS_SCHEMA]
})
export class TpDropdownSimpleComponent implements ControlValueAccessor, OnInit {
  constructor() {}
  ngOnInit(): void {
    this.init();
  }
  @Input() data!: any[];
  selectedItem: any;
  @Input() meta!: IdropdownMeta;
  @Input() isLoading!: boolean;
  filteredData: any[];

  // @Input() label!: string;
  // @Input() type: 'text' | 'password' | 'email' | 'number' | 'textarea' = 'text';
  // @Input() rows: number = 3;
  // @Input() placeholder: string;
  @Input() control?: FormControl | AbstractControl;
  public value: string;

  public id = this.getRand(1, 1000);
  public theme = 'info';
  // Function to call when change
  // public onChange: (value: any) => void;
  public onChange(value: any) {}
  public onTouched: () => void;

  public isDisabled: boolean;

  private init() {
    console.log(this.data);
    this.filteredData = this.data;
    this.selectedItem = {};
    this.selectedItem[this.meta.valueField] = this.meta.defaultValue || null;
    this.selectedItem[this.meta.mainLabel] = this.meta.label;
    this.selectedItem[this.meta.mainLabel] = `Select ${this.meta.label}...`;
    this.selectedItem[this.meta.subLabel] = 'Select...';
    this.meta.searchField = this.meta.searchField || 'subLabel';
    this.meta.hasImage = this.meta.hasImage || false;
  }
  private getRand(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }
  reset() {
    this.onTouched();
    this.writeValue(null);
    this.onChange(null);
    this.init();
  }
  selectItem(value) {
    this.onTouched();
    // this.selectedItem = this.filteredData.find((i) => i[this.meta.valueField] === value);
    // this.value = value;
    this.writeValue(value);
    this.onChange(value);
  }
  searchItem(e: any) {
    if (e.target.value) {
      const searchVal: string = e.target.value.toString();
      const idx = this.meta.searchField
      this.filteredData = this.data.filter((d) => d[this.meta[idx]].toLowerCase().includes(searchVal.toLowerCase()));
    } else {
      this.filteredData = this.data;
    }
  }

  writeValue(value: any): void {
    if (value) {
      this.selectedItem = this.filteredData.find((i) => i[this.meta.valueField] === value);
      // this.selectItem(value);
    }
    this.value = value;
    // this.value = value ?? this.selectedItem[this.meta.valueField];
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
          'is-valid': this.control?.touched && !this.control?.invalid,
          'is-invalid': this.control?.touched && this.control?.invalid,
        }
      : null;
  }

  private get errors() {
    const errors = this.control!.errors;
    return {
      required: `${this.meta.label || 'Field'} is Required`,
      email: `${this.meta.label || 'Field'} should be a valid email`,
      min: `${this.meta.label || 'Field'} should be a minimum of ${errors ? errors['min']?.min : ''}`,
      max: `${this.meta.label || 'Field'} should be a maximum of ${errors ? errors['max']?.max : ''}`,
      maxlength: `${this.meta.label || 'Field'} should not be more than ${errors ? errors['maxlength']?.requiredLength : ''} characters`,
      minlength: `${this.meta.label || 'Field'} should be atleast ${errors ? errors['minlength']?.requiredLength : ''} characters`,
    };
    
   
  }
  public get isRequired() {
    return this.control!.hasValidator(Validators.required) ? '*' : '';
  }

  public get Errors(): { type; message }[] {
    if (!this.control?.errors) {
      return [];
    }

    return Object.keys(this.control?.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errors[errorType],
      };
    });
  }
}
