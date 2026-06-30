import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TpTableCellTemplateDirective, TpTableSortDirective } from './directives';
import { TpTableDefaultFilterComponent } from './filter/tp-table-default-filter/tp-table-default-filter.component';
import { TpTableListFilterComponent } from './filter/tp-table-list-filter/tp-table-list-filter.component';
import { TpTablePaginationComponent } from './pagination/tp-table-pagination/tp-table-pagination.component';
import { TpStandardTableComponent } from './tp-standard-table/tp-standard-table.component';
import { NgbDropdownModule, NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';
import { TpPaginationInfoComponent } from './pagination/tp-pagination-info/tp-pagination-info.component';
import { TpDropdownComponent } from './tp-dropdown/tp-dropdown.component';
import { TpDropdownSimpleComponent } from './tp-dropdown-simple/tp-dropdown-simple.component';
import { SorterComponent } from '../table';
import { TpPaginationComponent } from './pagination/tp-pagination/tp-pagination.component';

@NgModule({
  declarations: [
    TpTableDefaultFilterComponent,
    TpTableListFilterComponent,
    TpTablePaginationComponent,
    TpStandardTableComponent,
    TpTableCellTemplateDirective,
    TpTableSortDirective,
    TpPaginationInfoComponent,
    TpPaginationComponent,
    SorterComponent

  ],
  imports: [CommonModule,FormsModule, ReactiveFormsModule,NgbDropdownModule,NgbPaginationModule],
  exports: [TpTableCellTemplateDirective,TpPaginationComponent,SorterComponent,TpTablePaginationComponent, TpStandardTableComponent,TpPaginationInfoComponent],
})
export class TpTableModule {}
