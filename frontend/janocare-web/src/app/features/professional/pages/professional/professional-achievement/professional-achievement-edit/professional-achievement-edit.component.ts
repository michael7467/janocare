import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProfessionalAchievement, ProfessionalAchievementService, User, cleanObject } from '../../../../../../core';
import { CommonModule, NgIf } from '@angular/common';
import { TpInputComponent } from '../../../../../../shared';
import { FlatpickrModule } from 'angularx-flatpickr';

interface data {
  value: string;
}
@Component({
  selector: 'app-professional-achievement-edit',
  standalone: true,
  imports: [ReactiveFormsModule, FlatpickrModule, NgIf, TpInputComponent, CommonModule],
  templateUrl: './professional-achievement-edit.component.html',
  styleUrls: ['./professional-achievement-edit.component.scss'],
})
export class ProfessionalAchievementEditComponent implements OnInit {
  @Input() id: number;
  @Output() onSaveComplete = new EventEmitter<boolean>();

  private service = inject(ProfessionalAchievementService);
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
      awardOrRecognitionName: ['', [Validators.required]],
      awardDescription: ['', Validators.required],
      awardYear: ['', Validators.required],
    });
  }

  get awardOrRecognitionName(): AbstractControl {
    return this.frm.get('awardOrRecognitionName');
  }
  get awardDescription(): AbstractControl {
    return this.frm.get('awardDescription');
  }
  get awardYear(): AbstractControl {
    return this.frm.get('awardYear');
  }

  private setFormValues(data: ProfessionalAchievement) {
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
  private addNew(value: Partial<ProfessionalAchievement> & { toJson: () => ProfessionalAchievement }) {
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

  public get isRequired() {
    return this.awardYear!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errors() {
    const errors = this.awardYear.errors;
    return {
      required: `${'Award Year'} is Required`,
    };
  }
  public get Errors(): { type; message }[] {
    if (!this.awardYear.errors) {
      return [];
    }

    return Object.keys(this.awardYear.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errors[errorType],
      };
    });
  }
}
