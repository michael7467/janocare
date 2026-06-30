import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
// import { BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import { RouterLink } from '@angular/router';
import { TpTableModule } from '../../../../../../shared';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProfessionalSpecialization, ProfessionalSpecializationService, Specialization, SpecializationService, User, cleanObject } from '../../../../../../core';
import { BehaviorSubject, Observable, catchError, concat, debounceTime, distinctUntilChanged, map, of, switchMap, tap } from 'rxjs';
import { defaultRequestParams } from '../../../../../../shared/tp-table/config';
import { NgSelectModule } from '@ng-select/ng-select';

@Component({
  selector: 'app-professional-speciality-edit',
  standalone: true,
  imports: [RouterLink, NgSelectModule, ReactiveFormsModule, TpTableModule, CommonModule,
    // ModalModule
  ],
  // providers: [BsModalService],
  templateUrl: './professional-speciality-edit.component.html',
  styleUrls: ['./professional-speciality-edit.component.scss'],
})
export class ProfessionalSpecialityEditComponent {
  @Input() id: number;
  @Output() onSaveComplete = new EventEmitter<boolean>();

  private service = inject(ProfessionalSpecializationService);
  private specialityService = inject(SpecializationService);

  private formBuilder = inject(FormBuilder);

  public frm: FormGroup;
  public isError = false;
  public isLoaded = true;
  professional: User;
  professionId: number;

  public saveLabel: 'Save' | 'Update' = 'Save';
  public mode: 'edit' | 'add' = 'add';

  ngOnInit() {
    this.setData();
    this.loadSpecializations();
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
      specializationId: [[], [Validators.required]],
    });
  }

  get specializationId(): AbstractControl {
    return this.frm.get('specializationId');
  }

  private setFormValues(data: Specialization) {
    this.frm = this.formBuilder.group({
      specializationId: data?.id,
    });
    // this.frm.patchValue({ ...data });
  }
  close(isSaved = false) {
    this.onSaveComplete.emit(isSaved);
  }
  private getData(id: number): void {
    this.specialityService.getById(id).subscribe((data) => {
      if (data) {
        this.setFormValues(data);
      }
    });
  }
  goToNext(form: any) {
    if (this.save(form)) {
    }
  }
  private addNew(value: Partial<ProfessionalSpecialization> & { toJson: () => ProfessionalSpecialization }) {
    const valuee = { ...value };
    this.service.create(valuee).subscribe((p) => {
      this.close(true);
    });
  }
  private update(value) {
    const valuee = { ...value };
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

  // search institution
  //
  specializationLoading = false;
  $specializationInput = new BehaviorSubject<string>('');
  $specializations: Observable<Specialization[]>;

  specializationTrackedBy(item: Specialization) {
    return item.id;
  }
  private loadSpecializations() {
    this.$specializations = concat(
      of([]), // default items
      this.$specializationInput.pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => (this.specializationLoading = true)),
        switchMap((term) => {
          let opts = defaultRequestParams;
          opts.filters = [{ name: 'name', value: term }];
          return this.specialityService
            .getTableData(opts)
            .pipe(map((r) => [...r.data]))
            .pipe(
              catchError(() => of([])), // empty list on error
              tap(() => (this.specializationLoading = false)),
            );
        }),
      ),
    );
  }
  //bank

  public get isRequiredBank() {
    return this.specializationId!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errorsBank() {
    const errors = this.specializationId.errors;
    return {
      required: `${'Specializations'} is Required`,
    };
  }
  public get ErrorsBank(): { type; message }[] {
    if (!this.specializationId.errors) {
      return [];
    }

    return Object.keys(this.specializationId.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errorsBank[errorType],
      };
    });
  }
}
