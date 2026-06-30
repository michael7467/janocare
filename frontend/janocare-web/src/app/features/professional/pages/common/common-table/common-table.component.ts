import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, forwardRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgbNavChangeEvent, NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { DateRangePickerModule, ImgFallbackDirective, ListComponent, Order, PageChangedEvent, RemovePortPipe, TpTableModule } from '../../../../../shared';
import { AuthService } from '../../../../../core';
import { routes } from '../../../../../shared/routes/routes';

@Component({
  selector: 'app-common-table',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TpTableModule,
    NgbNavModule,
    RemovePortPipe,
    DateRangePickerModule,
    ImgFallbackDirective,
    RouterLink],
  viewProviders: [
    {
      provide: ListComponent,
      useExisting: forwardRef(() => CommonTableComponent),
    },
  ],
  templateUrl: './common-table.component.html',
  styleUrls: ['./common-table.component.scss']
})
export class CommonTableComponent  implements OnInit{
  @Input() data: any[];
 @Input() columns: any;
 @Input()
 public collectionSize: number;
 @Input()
 public page: number;
 @Input()
 public pageSize: number;
 @Input()
 public loading: boolean;
 @Input()
 public isTableOnly: boolean;
  @Input() serviceInput: string;
  @Output() onNavChangeNew = new EventEmitter<NgbNavChangeEvent>();

  @Output() onSortBy = new EventEmitter<string>();
  @Output() onEditClick = new EventEmitter<number>();
  @Output() onViewClick = new EventEmitter<number>();
  @Output() onViewSlotClick = new EventEmitter<any>();
  @Output() onDateRange = new EventEmitter<any>();


  @Output() onApptEditClick = new EventEmitter<{id:number,type:string}>();
  @Output() onApptCancelEditClick = new EventEmitter<{id:number,type:string}>();




  public routes=routes;

  constructor(public auth:AuthService){}
 ngOnInit(): void {

 }
  onNavChange(changeEvent: NgbNavChangeEvent) {
    // this.orderes = this.allorderes.filter(country => country.status == status);
    this.onNavChangeNew.emit(changeEvent);

  }
  onChangeSortBy(changeEvent:string){
    this.onSortBy.emit(changeEvent);
  }
  onEditClickEvent(id: number) {
    
  }
  rangeSelected(range) {
 
    this.onDateRange.emit(range);
  }
  actionClicked(item:any,action:string){

    if(action==='Edit'){
      this.onEditClick.emit(item?.id);
    }
    else if(action==='View'){
      console.log(item?.slotDate);
      this.onViewClick.emit(item?.slotDate);
    }
    console.log(action);
  
  }
  actionApptClicked(item:any,action:string){
    console.log(item)
    this.onApptEditClick.emit({id:item,type:action});
  }
  actionApptCancelClicked(item:any,action:string){
    console.log(item)
    this.onApptCancelEditClick.emit({id:item,type:action});
  }
  getFileName(filePath: string): string {
  
    let parts = filePath.split('/');
    let fileName = parts.pop() || parts.pop();  // handle potential trailing slash
        return fileName;
  }
  setSort(sortBy: any, order: Order): void {
  
    // this.service.setSorting(order, sortBy);
    // this.onSortChange.next({ column: sortBy, direction: order });
 }
 getEndTime(startTime: string, slotInterval: string) {
  if(startTime===null || startTime==='') {
    return '';
  }
  let time = new Date('01/01/2021 ' + startTime);
  time.setMinutes(time.getMinutes() + parseInt(slotInterval));
  return time.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  
}
}
