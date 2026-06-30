import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-tp-pagination',
  templateUrl: './tp-pagination.component.html',
  styleUrls: ['./tp-pagination.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TpPaginationComponent {
  @Input()
  public collectionSize: number;
  @Input()
  public page: number;
  @Input()
  public pageSize: number;
  @Input()
  public showMoreButton: boolean;

  @Output()
  public seeMoreClick = new EventEmitter();
  @Output()
  public pageChange = new EventEmitter();
  @Output()
  public pageSizeChange = new EventEmitter();
  public perPageRecords = [5, 10, 20, 50, 100];
}
