import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlatpickrDefaults, FlatpickrDirective } from 'angularx-flatpickr';
import { SharedModule } from '../../../shared/shared.module';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FlatpickrDirective,
    SharedModule
  ]
})
export class PagesModule { }
