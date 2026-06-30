import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { ProfessionalService, ProfessionalServiceService, User, cleanObject } from '../../../../../../core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, NgIf } from '@angular/common';
import { TpInputComponent } from '../../../../../../shared';

@Component({
  selector: 'app-professional-service-edit',
  standalone: true,
  imports: [ ReactiveFormsModule,NgIf, TpInputComponent, CommonModule],
  templateUrl: './professional-service-edit.component.html',
  styleUrls: ['./professional-service-edit.component.scss']
})
export class ProfessionalServiceEditComponent {
  @Input() id: number;
  @Output() onSaveComplete = new EventEmitter<boolean>();

  private service = inject(ProfessionalServiceService);
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
      serviceName: ['', [Validators.required]],
    });
  }

  get serviceName(): AbstractControl {
    return this.frm.get('serviceName');
  }


  private setFormValues(data: ProfessionalService) {
    this.frm.patchValue({ ...data });
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
  private addNew(value: Partial<ProfessionalService> & { toJson: () => ProfessionalService; }) {
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
}
