import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { ProfessionalExperience, ProfessionalExperienceService, User, cleanObject } from '../../../../../../core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, NgIf } from '@angular/common';
import { TpInputComponent } from '../../../../../../shared';

@Component({
  selector: 'app-professional-experience-edit',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, TpInputComponent, CommonModule],
  templateUrl: './professional-experience-edit.component.html',
  styleUrls: ['./professional-experience-edit.component.scss'],
})
export class ProfessionalExperienceEditComponent {
  @Input() id: number;
  @Output() onSaveComplete = new EventEmitter<boolean>();
  private service = inject(ProfessionalExperienceService);
  private formBuilder = inject(FormBuilder);
  years = Array.from({ length: 200 }, (_, i) => 1900 + i);

  public frm: FormGroup;
  public isError = false;
  public isLoaded = true;
  professional: User;
  professionId: number;
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
      experience: ['', [Validators.required]],
      startYear: ['', [Validators.required]],
      endYear: ['', [Validators.required]],
      specialization: ['', [Validators.required]],
      place: ['', [Validators.required]],
    });
  }

  get experience(): AbstractControl {
    return this.frm.get('experience');
  }
  get specialization(): AbstractControl {
    return this.frm.get('specialization');
  }
  get place(): AbstractControl {
    return this.frm.get('place');
  }
  get startYear(): AbstractControl {
    return this.frm.get('startYear');
  }
  get endYear(): AbstractControl {
    return this.frm.get('endYear');
  }

  private setFormValues(data: ProfessionalExperience) {
    this.frm = this.formBuilder.group({
      experience: [data?.experience],
      startYear: [new Date(data.startYear).getFullYear()],
      endYear: [new Date(data.endYear).getFullYear()],
      specialization: [data?.specialization],
      place: [data?.place],
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
  private addNew(value: Partial<ProfessionalExperience> & { toJson: () => ProfessionalExperience }) {
    const valuee = { ...value };
    this.service.create(valuee).subscribe((p) => {
      this.close(true);
    });
  }
  private update(value: Partial<ProfessionalExperience> & { toJson: () => ProfessionalExperience }) {
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
}
