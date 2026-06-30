// import { CurrencyPipe, DecimalPipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { Component, forwardRef, Input, OnInit, ViewEncapsulation } from '@angular/core';
import { AbstractControl, ControlValueAccessor, FormControl, FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule, Validators } from '@angular/forms';
import { FlatpickrModule } from 'angularx-flatpickr';

@Component({
  selector: 'app-tp-input',
  templateUrl: './tp-input.component.html',
  styleUrls: ['./tp-input.component.scss'],
  encapsulation: ViewEncapsulation.None,
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, FlatpickrModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TpInputComponent),
      multi: true,
    },
  ],
})
export class TpInputComponent implements ControlValueAccessor, OnInit {
  constructor() {}
  ngOnInit(): void {
    if (!this.placeholder) {
      this.placeholder = this.label;
    }
  }
  // @ViewChild('tpInput') tpInput: ElementRef<HTMLInputElement>;

  @Input() label!: string;
  @Input() type: 'text' | 'password' | 'email' | 'number' | 'textarea' | 'time' | 'date' = 'text';
  @Input() rows: number = 3;
  @Input() placeholder: string;
  @Input() helpText: string;
  @Input() control: FormControl | AbstractControl;
  // public value: string;
  private _value: string;

  // generate setter and getter for _value

  public set value(v: string) {
    // const formattedV = this.type === 'number' && !isNaN(Number(v)) ? this.decimalPipe.transform(v, '', 'en-US') : v;
    // console.log('formatted', formattedV)
    // this._value = formattedV
    this._value = v;
  }
  public get value(): string {
    return this._value;
  }

  public id = Math.random();

  // Function to call when change
  public onChange: (value: any) => void;
  public onTouched: () => void;

  public isDisabled: boolean;

  writeValue(value: any): void {
    this.value = value;
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
