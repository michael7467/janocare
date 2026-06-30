import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-tp-pagination-info',
  templateUrl: './tp-pagination-info.component.html',
  styleUrls: ['./tp-pagination-info.component.scss'],
})
export class TpPaginationInfoComponent {
  @Input()
  public collectionSize: number;
  @Input()
  public page: number;
  @Input()
  public pageSize: number;
  @Input()
  public loading: boolean;

  get start() {
    const start = (this.page - 1) * this.pageSize + 1;
    return start === 0 ? 1 : start;
  }
  get end() {
    const end = this.start + this.pageSize - 1;
    if (end < this.pageSize) {
      return this.pageSize;
    } else if (end > this.collectionSize) {
      return this.collectionSize;
    }
    return end;
  }

  //   // page info
  //   resultStart = (this.service.page - 1) * this.service.pageSize + 1;
  // if (resultStart === 0) resultStart= 1; // *it happens only for the first run*
  // resultEnd = resultStart+this.service.pageSize-1;

  // if (resultEnd < this.service.pageSize)   // happens when records less than per page
  //     resultEnd = this.service.pageSize;
  // else if (resultEnd > this.service.total)  // happens when result end is greater than total records
  //     resultEnd = this.service.total;

  // "displaying $result_start to $result_end of $total"
}
