import { Directive, Input, TemplateRef } from '@angular/core';

@Directive({ selector: '[tpTableCellTemplate]' })
export class TpTableCellTemplateDirective {
  @Input() public tpTableCellTemplate!: string;

  constructor(public templateRef: TemplateRef<HTMLElement>) {}
}
