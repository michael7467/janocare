import { Component, EventEmitter, Input, NO_ERRORS_SCHEMA, NgModule, Output, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { routes } from '../../../../../../shared/routes/routes';

import { AbstractControl, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, NgIf } from '@angular/common';
import { ImgFallbackDirective, TpInputComponent } from '../../../../../../shared';
import { AuthService, City, CityService, Country, CountryService, State, StateService, User, UserProfile, cleanObject } from '../../../../../../core';
// import { MatFormFieldModule } from '@angular/material/form-field';
// import { MatInputModule } from '@angular/material/input';
// import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { BehaviorSubject, Observable, catchError, concat, debounceTime, distinctUntilChanged, map, of, startWith, switchMap, tap } from 'rxjs';
import { NgSelectModule } from '@ng-select/ng-select';
import { defaultRequestParams } from '../../../../../../shared/tp-table/config';

import { TpDropdownComponent } from '../../../../../../shared/tp-table/tp-dropdown/tp-dropdown.component';
import { TpDropdownSimpleComponent } from '../../../../../../shared/tp-table/tp-dropdown-simple/tp-dropdown-simple.component';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
interface IdropdownMeta {
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
  selector: 'app-personal-info-setting',
  standalone: true,
  imports: [ ReactiveFormsModule,NgbDropdownModule,ImgFallbackDirective,NgSelectModule,FormsModule,
    // MatAutocompleteModule,
    TpDropdownSimpleComponent, 
    // MatFormFieldModule,
    NgIf, TpInputComponent, CommonModule],
  templateUrl: './personal-info-setting.component.html',
  styleUrls: ['./personal-info-setting.component.scss'],
  schemas: [NO_ERRORS_SCHEMA]
})
export class PersonalInfoSettingComponent {
  @Input() id: number;
  @Output() onSaveComplete = new EventEmitter<boolean>();
  items = [
    { id: 1, name: 'Item 1' },
    { id: 2, name: 'Item 2' },
    // more items
  ];
  metaData:IdropdownMeta={
    mainLabel: 'Gender',
    subLabel: 'Gender',
    defaultValue: 'Male',
    searchField: 'mainLabel',
    valueField: 'value',
    hasLabel: true,
    viewValidationMessages: false
  }
 $genders= [{name:'Male',value:'Male'},{name:'Female',value:'Female'}];

  private authService=inject(AuthService);
  private countryService=inject(CountryService);
  private stateService=inject(StateService);
  private cityService=inject(CityService);
  private router=inject(Router);

  private formBuilder = inject(FormBuilder);

  public frm: FormGroup;
  public isError = false;
  public isLoaded = true;
  
  professional:User;
  professionId:number;
  countries: Country[];
  states: State[];
  cities: City[];



  public saveLabel: 'Save' | 'Update' = 'Save';
  public mode: 'edit' | 'add' = 'add';

  ngOnInit() {
    this.authService.userProfile$.subscribe((p)=>{
     
      this.professional=p;
      this.professionId=this.professional?.professionalId;
      this.id=this.professional?.userProfile?.id;
    });
    this.setData();
    this.getCountry();
    this.loadCountries();
  }
  getCountry() {
    this.countryService.getAll().subscribe((data) => {
      this.countries = data;
    });
  }
  getState(countryId: number) {
    this.stateService.getStateByCountryId(countryId).subscribe((data) => {
      this.states = data;
    });
  }
  getCities(stateId: number) {
    this.cityService.getCityByStateId(stateId).subscribe((data) => {
      this.cities = data;
    });
  }
  private setData() {
    if (this.id) {
      this.mode = 'edit';
      this.saveLabel = 'Update';
      this.getData(this.id);
    }
    this.createForm();
  }
  private getData(id: number): void {
     setTimeout(() => {
      this.setFormValues(this.professional.userProfile);
     
      this.loadStates(this.professional?.userProfile?.country?.id);
      this.loadcities(this.professional?.userProfile?.state?.id);

     }, 100);
   
  }
  private createForm() {
    this.frm = this.formBuilder.group({
      firstName: ['', [Validators.required]],
      // middleName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      // nationalId: ['', [Validators.required]],
      cityId: [[], [Validators.required]],
      stateId: [[], [Validators.required]],
      countryId: [[], [Validators.required]],
      gender: [null, [Validators.required]],

    });
  }


 // search country
 //
countryLoading = false;

$countryInput = new BehaviorSubject<string>('');

$countries!: Observable<Country[]>;

countryTrackedBy(item: Country) {
  return item.id;
}

private loadCountries() {

  this.$countries = concat(

    of([]),

    this.$countryInput.pipe(

      debounceTime(300),

      distinctUntilChanged(),

      tap(() => this.countryLoading = true),

      switchMap((term) => {

        const opts = {
          ...defaultRequestParams,
          filters: [
            {
              name: 'countryName',
              value: term
            }
          ]
        };

        return this.countryService
          .getTableData(opts)
          .pipe(
            map((r) => r.data ?? []),

            catchError(() => of([])),

            tap(() => this.countryLoading = false)
          );
      })
    )
  );
}

    // search state
  //
  stateLoading = false;
  $stateInput = new BehaviorSubject<string>('');
  $states: Observable<State[]>;

  stateTrackedBy(item: State) {
    return item.id;
  }
  private loadStates(countryId:number) {
    this.$states = concat(
      of([]), // default items
      this.$stateInput.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => (this.countryLoading = true)),
        switchMap((term) => {
          let opts = defaultRequestParams;
          opts.filters=[{name: 'stateName', value: term},{name:'countryId',value:countryId}]
          return (
            this.stateService
              .getTableData(opts)
              .pipe(map((r) => [...r.data]))
              .pipe(
                catchError(() => of([])), // empty list on error
                tap(() => {this.stateLoading = false;
                this.countryLoading=false}),
              )
          );
        }),
      ),
    );
  }
  cityLoading = false;
  $cityInput = new BehaviorSubject<string>('');
  $cities: Observable<City[]>;

  cityTrackedBy(item: City) {
    return item.id;
  }
  
  private loadcities(stateId:number) {
    this.$cities = concat(
      of([]), // default items
      this.$cityInput.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => (this.countryLoading = true)),
        switchMap((term) => {
          let opts = defaultRequestParams;
          opts.filters=[{name: 'cityName', value: term},{name:'stateId',value:stateId}]
          return (
            this.cityService
              .getTableData(opts)
              .pipe(map((r) => [...r.data]))
              .pipe(
                catchError(() => of([])), // empty list on error
                tap(() => {
                  this.cityLoading = false;
                  this.stateLoading = false;
                  this.countryLoading=false
              }),
              )
          );
        }),
      ),
    );
  }
  onCountryChange(event:any) {
    this.loadStates(event.id);
  }
  onStateChange(event:any){
    this.loadcities(event.id);
  }

 
  // onCountryChange(event) {

  //   this.getState(event.target.value);
  //   const selectedCountryId = event.target.value;

  // }
  // onStateChange(event: any) {
  //  console.log(event);
 
  // }
  get firstName(): AbstractControl {
    return this.frm.get('firstName');
  }
  get middleName(): AbstractControl {
    return this.frm.get('middleName');
  }
  get lastName(): AbstractControl {
    return this.frm.get('lastName');
  }
  get nationalId(): AbstractControl {
    return this.frm.get('nationalId');
  }
  get cityId(): AbstractControl {
    return this.frm.get('cityId');
  }
  get stateId(): AbstractControl {
    return this.frm.get('stateId');
  }
  get countryId(): AbstractControl {
      return this.frm.get('countryId');
  }
  get gender(): AbstractControl {
    return this.frm.get("gender");
  }
  private setFormValues(data: UserProfile) {


     
      let gender=(data?.gender?.toString()==="NA" || data?.gender?.toString()===null)?null:data.gender;
     
      console.log(data);
      this.frm = this.formBuilder.group({
        firstName: [data?.firstName,Validators.required],
        // middleName: [data?.middleName,Validators.required],
        lastName: [data?.lastName,Validators.required],
        // nationalId: [data?.nationalId,Validators.required],
        cityId: [data?.city?.id,Validators.required],
        stateId: [data?.state?.id,Validators.required],
        countryId: [data?.country?.id,Validators.required],
        gender: [gender,Validators.required],
      });

  }
  close(isSaved = false) {
    this.onSaveComplete.emit(isSaved);
  }

  goToNext(form: any) {
    if (this.save(form)) {
    }
  }
  private addNew(value: Partial<UserProfile> & { toJson: () => UserProfile; }) {

    const valuee={...value};
    this.authService.updateUserProfile({ ...valuee, id: this.id }).subscribe((p) => {
      this.close(true);
    });
  }
  private update(value) {
    console.log(value);
    const valuee={...value};
    this.authService.updateUserProfile({ ...valuee, id: this.id }).subscribe((p) => {
      this.router.navigateByUrl(routes.professionalInfo);
    });
  }

  private save(form: any): boolean {
    if (!form.valid) {
      return false;
    }
    const updatedData = { ...form.value };
    if (this.mode === 'add') {
      this.addNew(cleanObject(updatedData));
    } else if (this.mode === 'edit') {
      this.update(cleanObject(updatedData));
    } else {
      return false;
    }
    return true;
  }



  public get isRequiredCountry() {
    return this.countryId!.hasValidator(Validators.required) ? '*' : '';
  }
  public get isRequiredState() {
    return this.stateId!.hasValidator(Validators.required) ? '*' : '';
  }
  public get isRequiredCity() {
    return this.cityId!.hasValidator(Validators.required) ? '*' : '';
  }
  public get isRequired() {
    return this.gender!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errors() {
    const errors = this.gender.errors;
    return {
      required: `${'Gender'} is Required`,
    };
  }
  public get Errors(): { type; message }[] {
    if (!this.gender.errors) {
      return [];
    }

    return Object.keys(this.gender.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errors[errorType],
      };
    });
  }
  // Country
  private get errorsCountry() {
    const errors = this.countryId.errors;
    return {
      required: `${'Country'} is Required`,
    };
  }
  public get ErrorsCountry(): { type; message }[] {
    if (!this.countryId.errors) {
      return [];
    }

    return Object.keys(this.countryId.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errorsCountry[errorType],
      };
    });
  }
  //State error
  private get errorsState() {

    return {
      required: `${'State'} is Required`,
    };
  }
  public get ErrorsStates(): { type; message }[] {
    if (!this.stateId.errors) {
      return [];
    }

    return Object.keys(this.stateId.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errorsState[errorType],
      };
    });
  }
  //City Error
  private get errorsCity() {
  
    return {
      required: `${'City'} is Required`,
    };
  }
  public get ErrorsCity(): { type; message }[] {
    if (!this.gender.errors) {
      return [];
    }

    return Object.keys(this.cityId.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errorsCity[errorType],
      };
    });
  }

}
