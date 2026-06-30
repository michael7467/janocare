import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
// import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ProfessionalQualificationService, User, cleanObject } from '../../../../../../core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProfessionalQualification } from '../../../../../../core/models/professional/professional-qualification.model';
import { CommonModule, NgIf } from '@angular/common';
import { TpInputComponent } from '../../../../../../shared';
import { FlatpickrModule } from 'angularx-flatpickr';

@Component({
  selector: 'app-professional-qualification-edit',
  standalone: true,
  imports: [ ReactiveFormsModule,NgIf,
    // BsDatepickerModule,
    FlatpickrModule, TpInputComponent, CommonModule],
  templateUrl: './professional-qualification-edit.component.html',
  styleUrls: ['./professional-qualification-edit.component.scss']
})
export class ProfessionalQualificationEditComponent {
  @Input() id: number;
  @Output() onSaveComplete = new EventEmitter<boolean>();

  private service = inject(ProfessionalQualificationService);
  private formBuilder = inject(FormBuilder);

  public frm: FormGroup;
  public isError = false;
  public isLoaded = true;
  professional:User;
  professionId:number;



  public saveLabel: 'Save' | 'Update' = 'Save';
  public mode: 'edit' | 'add' = 'add';

  ngOnInit() {
    this.setData();
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
      qualificationName: ['', [Validators.required]],
      institutionName: ['', [Validators.required]],
      procrumentYear: ['', [Validators.required]],
    });
  }

  get qualificationName(): AbstractControl {
    return this.frm.get('qualificationName');
  }
  get institutionName(): AbstractControl {
    return this.frm.get('institutionName');
  }
  get procrumentYear(): AbstractControl {
    return this.frm.get('procrumentYear');
  }

  private setFormValues(data: ProfessionalQualification) {
    this.frm = this.formBuilder.group({
      qualificationName: data.qualificationName,
      institutionName: data.institutionName,
      procrumentYear: new Date(data.procrumentYear),
    });
    // this.frm.patchValue({ ...data });
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
  private addNew(value: Partial<ProfessionalQualification> & { toJson: () => ProfessionalQualification; }) {
    const valuee={...value};
    console.log(valuee);
    this.service.create(valuee).subscribe((p) => {
      console.log(p);
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
}
