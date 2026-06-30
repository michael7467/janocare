import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService, Bank, InstitutionUser, InstitutionUserService, ProfessionalInfo, ProfessionalInfoService, User, cleanObject } from '../../../../../../core';
import { CommonModule, NgIf } from '@angular/common';
import { TpInputComponent } from '../../../../../../shared';
import { NgSelectModule } from '@ng-select/ng-select';
import { BehaviorSubject, Observable, catchError, concat, debounceTime, distinctUntilChanged, map, of, switchMap, tap } from 'rxjs';
import { defaultRequestParams } from '../../../../../../shared/tp-table/config';
import { FlatpickrModule } from 'angularx-flatpickr';

@Component({
  selector: 'app-professional-info-edit',
  standalone: true,
  imports: [ ReactiveFormsModule,FlatpickrModule,NgIf,NgSelectModule, TpInputComponent, CommonModule],
  templateUrl: './professional-info-edit.component.html',
  styleUrls: ['./professional-info-edit.component.scss']
})
export class ProfessionalInfoEditComponent {
  @Input() id: number;
  @Output() onSaveComplete = new EventEmitter<boolean>();

  private service = inject(ProfessionalInfoService);
  private institutionService=inject(InstitutionUserService)
  private formBuilder = inject(FormBuilder);

  $daysList= [
    {name:'Monday',value:'MONDAY'},
    {name:'Tuesday',value:'TUESDAY'},
    {name:'Wednesday',value:'WEDNESDAY'},
    {name:'Thursday',value:'THURSDAY'},
    {name:'Friday',value:'FRIDAY'},
    {name:'Saturday',value:'SATURDAY'},
    {name:'Sunday',value:'SUNDAY'}

  ];
  public frm: FormGroup;
  public isError = false;
  public isLoaded = true;
  user:User;
  public saveLabel: 'Save' | 'Update' = 'Save';
  public mode: 'edit' | 'add' = 'add';

  ngOnInit() {
    this.setData();
    this.loadInstitutions();
  }

  private setData() {
    if (this.id) {
      this.mode = 'edit';
      this.saveLabel = 'Update';
      this.getData(this.id);
    }
    this.createForm();
  }

  private createForm() {
    this.frm = this.formBuilder.group({
   
      daysOfWeek: [null, [Validators.required]],
      startTime: ['', [Validators.required]],
      endTime: ['', [Validators.required]],
      officeNumber: ['', [Validators.required]],
      institutionUserId: [null, [Validators.required]],
    });
  }
  get institutionUserId(): AbstractControl {
    return this.frm.get('institutionUserId');
  }
  get daysOfWeek(): AbstractControl {
    return this.frm.get('daysOfWeek');
  }
  get startTime(): AbstractControl {
    return this.frm.get('startTime');
  }
  get endTime(): AbstractControl {
    return this.frm.get('endTime');
  } 
   get officeNumber(): AbstractControl {
    return this.frm.get('officeNumber');
  }

  private setFormValues(data: ProfessionalInfo) {
    this.frm = this.formBuilder.group({
      daysOfWeek: data.daysOfWeek,
      startTime: data.startTime,
      endTime: data.endTime,
      officeNumber:data.officeNumber,
    });
  }
  close(isSaved = false) {
    this.onSaveComplete.emit(isSaved);
  }
  private getData(id: number): void {
    this.service.getById(id).subscribe((data) => {
      if (data) {
        this.setFormValues(data);
      }
    });
  }
  goToNext(form: any) {
    if (this.save(form)) {
    }
  }
  private addNew(value: Partial<ProfessionalInfo> & { toJson: () => ProfessionalInfo; }) {
    const valuee={...value};
    this.service.create(valuee).subscribe((p) => {
      this.close(true);
    });
  }
  private update(value) {
    const valuee={...value};
    this.service.update({ ...valuee, id: this.id }).subscribe((p) => {
      this.close(true);
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
  public get isRequired() {
    return this.daysOfWeek!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errors() {
    const errors = this.daysOfWeek.errors;
    return {
      required: `${'Days of Week'} is Required`,
    };
  }
  public get Errors(): { type; message }[] {
    if (!this.daysOfWeek.errors) {
      return [];
    }

    return Object.keys(this.daysOfWeek.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errors[errorType],
      };
    });
  }

     // search institution
 //
 bankLoading = false;
 $bankInput = new BehaviorSubject<string>('');
 $banks: Observable<InstitutionUser[]>;

 bankTrackedBy(item: InstitutionUser) {
   return item.id;
 }
 private loadInstitutions() {
   this.$banks = concat(
     of([]), // default items
     this.$bankInput.pipe(
       debounceTime(300),
       distinctUntilChanged(),
       tap(() => (this.bankLoading = true)),
       switchMap((term) => {
         let opts = defaultRequestParams;
         opts.filters=[{name: 'bankName', value: term}];
         return (
           this.institutionService
             .getTableData(opts)
             .pipe(map((r) => [...r.data]))
             .pipe(
               catchError(() => of([])), // empty list on error
               tap(() => (this.bankLoading = false)),
             )
         );
       }),
     ),
   );
 }
//bank

  public get isRequiredBank() {
    return this.institutionUserId!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errorsBank() {
    const errors = this.institutionUserId.errors;
    return {
      required: `${'Institution'} is Required`,
    };
  }
  public get ErrorsBank(): { type; message }[] {
    if (!this.institutionUserId.errors) {
      return [];
    }

    return Object.keys(this.institutionUserId.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errors[errorType],
      };
    });
  }
}
