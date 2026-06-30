import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { ProfessionalMembership, ProfessionalMembershipService, User, cleanObject } from '../../../../../../core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, NgIf } from '@angular/common';
import { TpInputComponent } from '../../../../../../shared';
import { FlatpickrModule } from 'angularx-flatpickr';

@Component({
  selector: 'app-professional-membership-edit',
  standalone: true,
  imports: [ReactiveFormsModule, FlatpickrModule, NgIf, TpInputComponent, CommonModule],
  templateUrl: './professional-membership-edit.component.html',
  styleUrls: ['./professional-membership-edit.component.scss'],
})
export class ProfessionalMembershipEditComponent {
  @Input() id: number;
  @Output() onSaveComplete = new EventEmitter<boolean>();

  private service = inject(ProfessionalMembershipService);
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
      membershipName: ['', [Validators.required]],
      membershipDescription: ['', [Validators.required]],
      membershipYear: ['', [Validators.required]],
    });
  }

  get membershipName(): AbstractControl {
    return this.frm.get('membershipName');
  }
  get membershipDescription(): AbstractControl {
    return this.frm.get('membershipDescription');
  }
  get membershipYear(): AbstractControl {
    return this.frm.get('membershipYear');
  }
  private setFormValues(data: ProfessionalMembership) {
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
  private addNew(value: Partial<ProfessionalMembership> & { toJson: () => ProfessionalMembership }) {
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
    return this.membershipYear!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errors() {
    const errors = this.membershipYear.errors;
    return {
      required: `${'Membership Year'} is Required`,
    };
  }
  public get Errors(): { type; message }[] {
    if (!this.membershipYear.errors) {
      return [];
    }

    return Object.keys(this.membershipYear.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errors[errorType],
      };
    });
  }
}
