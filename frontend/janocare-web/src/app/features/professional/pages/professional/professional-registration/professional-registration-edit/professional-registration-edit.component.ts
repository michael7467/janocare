import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
// import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { AuthService, ProfessionalRegistration, ProfessionalRegistrationService, User, cleanObject } from '../../../../../../core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, NgIf } from '@angular/common';
import { TpInputComponent } from '../../../../../../shared';
import { NgxDropzoneChangeEvent, NgxDropzoneModule } from 'ngx-dropzone';
import { DropzoneModule } from 'ngx-dropzone-wrapper';
import { FlatpickrModule } from 'angularx-flatpickr';

@Component({
  selector: 'app-professional-registration-edit',
  standalone: true,
  imports: [ ReactiveFormsModule,NgIf,FlatpickrModule,
    // BsDatepickerModule,
    DropzoneModule,NgxDropzoneModule, TpInputComponent, CommonModule],
  templateUrl: './professional-registration-edit.component.html',
  styleUrls: ['./professional-registration-edit.component.scss']
})
export class ProfessionalRegistrationEditComponent {
  @Input() id: number;
  @Output() onSaveComplete = new EventEmitter<boolean>();

  private service = inject(ProfessionalRegistrationService);
  private formBuilder = inject(FormBuilder);
  public frm: FormGroup;
  public isError = false;
  public isLoaded = true;
  professional:User;
  professionId:number;
  fileName:string;
  fileToUpload: File;

  isImageSelected:boolean=false;
  isTouched:boolean=false;

  public saveLabel: 'Save' | 'Update' = 'Save';
  public mode: 'edit' | 'add' = 'add';

  ngOnInit() {
    this.setData();
  }
  files: any[] = [];

  onSelect(event: NgxDropzoneChangeEvent) {

    this.isImageSelected=true;
   // Get the current files
   this.fileToUpload=event.addedFiles[0];
   const currentFiles = this.frm.get('certificatePhoto').value || [];

   // Append the newly selected files
   this.frm.get('certificatePhoto').setValue(event.addedFiles);
   this.files=[];
    for (let addedFile of event.addedFiles) {
   
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.files.push({ name: addedFile.name, preview: e.target.result });
      };
      reader.readAsDataURL(addedFile);
    }
    this.frm.patchValue({ certificatePhoto: event.addedFiles[0] });
  }
  private setData() {
    if (this.id) {
      this.mode = 'edit';
      this.saveLabel = 'Update';
      this.getData(this.id);
    }
    this.createForm();
  }
  onTouched() {
    this.isTouched=true;
  }
  private createForm() {
    this.frm = this.formBuilder.group({
      registrationName: ['', [Validators.required]],
      registrationDate: ['', [Validators.required]],
      certificatePhoto: ['', [Validators.required]],
      // filess: ['', [Validators.required]],

    });
  }

  get registrationName(): AbstractControl {
    return this.frm.get('registrationName');
  }
  get registrationDate(): AbstractControl {
    return this.frm.get('registrationDate');
  }
  get certificatePhoto(): AbstractControl {
    return this.frm.get('certificatePhoto');
  }
  // get filess(): AbstractControl {
  //   return this.frm.get('filess');
  // }

  private setFormValues(data: ProfessionalRegistration) {
    console.log(data);
    this.fileName=data.certificatePhoto;

    this.frm = this.formBuilder.group({
      registrationName: data.registrationName,
      registrationDate: new Date(data.registrationDate),
      certificatePhoto: data.certificatePhoto,
    });
    // reader.readAsDataURL(data.certificatePhoto);
  }
  close(isSaved = false) {
    this.onSaveComplete.emit(isSaved);
  }
  private getData(id: number): void {
    this.service.getById(id).subscribe((data) => {
      if (data) {
        this.setFormValues(data);
        this.getFileName(data.certificatePhoto);
      }
    });
  }
  goToNext(form: any) {
    if (this.save(form)) {
    }
  }
  private addNew(value: Partial<ProfessionalRegistration> & { toJson: () => ProfessionalRegistration; }) {
    const valuee={...value};
   console.log(valuee)
    this.service.createProfessionalRegistration(valuee,this.fileToUpload).subscribe((p) => {
      this.close(true);
    });
  }
  private update(value) {
    const valuee={...value};

    this.service.updateProfessinalRegistration({ ...valuee, id: this.id },this.fileToUpload).subscribe((p) => {
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
  onFileSelected(event) {
    const file: File = event.target.files[0];
    if(file){
      this.fileName=file.name;
      this.fileToUpload = file;
    }
    this.frm.patchValue({ certificatePhoto: file });
  } 
  getFileName(filePath: string): void {
  
    let parts = filePath.split('/');
    let fileName = parts.pop() || parts.pop();  // handle potential trailing slash
    this.fileName=fileName;
  }
  public get isRequired() {
    return this.registrationDate!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errors() {
    const errors = this.registrationDate.errors;
    return {
      required: `${'Completion Date'} is Required`,
    };
  }
  public get Errors(): { type; message }[] {
    if (!this.registrationDate.errors) {
      return [];
    }

    return Object.keys(this.registrationDate.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errors[errorType],
      };
    });
  }
  public get isRequiredImage() {
    return this.certificatePhoto!.hasValidator(Validators.required) ? '*' : '';
  }
  private get errorsImage() {
    const errors = this.certificatePhoto.errors;
    return {
      required: `${'Image'} is Required`,
    };
  }
  public get ErrorsImage(): { type; message }[] {
    if (!this.certificatePhoto.errors) {
      return [];
    }

    return Object.keys(this.certificatePhoto.errors).map((errorType) => {
      return {
        type: errorType,
        message: this.errorsImage[errorType],
      };
    });
  }
}
