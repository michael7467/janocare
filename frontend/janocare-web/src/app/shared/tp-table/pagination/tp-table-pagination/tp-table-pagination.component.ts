import { ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Page, PageChangedEvent } from '../../model';

@Component({
  selector: 'tp-table-pagination',
  templateUrl: './tp-table-pagination.component.html',
  styleUrls: ['./tp-table-pagination.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,

})
export class TpTablePaginationComponent implements OnInit, OnDestroy {
  private subscription = new Subscription();
  private _totalItems = 0;
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
  
  // @Input()
  // public showMoreButton: boolean;

  itemsPerPageControl = new FormControl(0);
  totalPages = 0;
  pages!: Page[];

 
  @Input() itemsPerPageOptions!: number[];
  @Input() set itemsPerPage(value: number) {
    this.itemsPerPageControl.setValue(value, { emitEvent: false });
    this.totalPages = this.calculateTotalPages();
    this.pages = this.getPages(this.page, this.totalPages);
  }
  @Input() set totalItems(value: number) {
    this._totalItems = value;
    this.totalPages = this.calculateTotalPages();
    this.pages = this.getPages(this.page, this.totalPages);
  }
  @Input() maxSize!: number;
  @Input() nextText!: string;
  @Input() previousText!: string;

  @Output() paginationChanged = new EventEmitter<PageChangedEvent>();

  get itemsPerPage(): number {
    return this.itemsPerPageControl.value || 20;
  }
  get totalItems(): number {
    return this._totalItems;
  }

  constructor(private cdRef: ChangeDetectorRef) {}

  ngOnInit(): void {
    console.log(this.page);
    this.subscription.add(
      this.itemsPerPageControl.valueChanges.subscribe((itemsPerPage) => {
        this.cdRef.markForCheck();
        this.totalPages = this.calculateTotalPages();
        this.pages = this.getPages(this.page, this.totalPages);
        this.page = Math.min(this.page, this.totalPages);
        this.paginationChanged.emit({ itemsPerPage: itemsPerPage || 20, page: this.page });
      }),
    );
    this.totalPages = this.calculateTotalPages();
    this.pages = this.getPages(this.page, this.totalPages);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  selectPage(page: number, event?: Event): void {
    console.log(this.page);
    if (event) event.preventDefault();
    this.page = page;
    this.pages = this.getPages(this.page, this.totalPages);
    this.paginationChanged.emit({ page: page, itemsPerPage: this.itemsPerPage });
  }

  private calculateTotalPages(): number {
    const totalPages = this.itemsPerPage < 1 ? 1 : Math.ceil(this.totalItems / this.itemsPerPage);
    return Math.max(totalPages || 0, 1);
  }

  private getPages(currentPage: number, totalPages: number): Page[] {
    const pages: Page[] = [];
    let startPage = 1;
    let endPage = totalPages;
    const isMaxSized = !!this.maxSize && this.maxSize < totalPages;

    if (isMaxSized && this.maxSize) {
      startPage = Math.max(currentPage - Math.floor(this.maxSize / 2), 1);
      endPage = startPage + this.maxSize - 1;

      if (endPage > totalPages) {
        endPage = totalPages;
        startPage = endPage - this.maxSize + 1;
      }
    }

    for (let num = startPage; num <= endPage; num++) {
      const page = { number: num, text: num.toString(), active: num === currentPage };
      pages.push(page);
    }

    if (isMaxSized) {
      if (startPage > 1) {
        const previousPageSet = { number: startPage - 1, text: '...', active: false };
        pages.unshift(previousPageSet);
      }

      if (endPage < totalPages) {
        const nextPageSet = { number: endPage + 1, text: '...', active: false };
        pages.push(nextPageSet);
      }
    }
    return pages;
  }
}
