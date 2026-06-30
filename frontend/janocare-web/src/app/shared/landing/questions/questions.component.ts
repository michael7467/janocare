// import { CommonModule, DatePipe } from '@angular/common';
// import { Component, OnInit, inject } from '@angular/core';
// import { RouterModule } from '@angular/router';
// import { PatientQuestionModel, PatientQuestionService } from '../../../core';
// import { defaultRequestParams } from 'shared/lib/tp-table/config';
// import { ImgFallbackDirective, RemovePortPipe } from 'shared';

// @Component({
//   selector: 'app-questions',
//   standalone: true,
//   imports: [CommonModule, RouterModule, RemovePortPipe, ImgFallbackDirective, DatePipe],
//   templateUrl: './questions.component.html',
//   styleUrl: './questions.component.scss',
// })
// export class QuestionsComponent implements OnInit {
//   topQuestions: PatientQuestionModel[];

//   service = inject(PatientQuestionService);

//   ngOnInit(): void {
//     this._fetchData();
//   }

//   private _fetchData() {
//     this.service.getTableData({ ...defaultRequestParams, take: 3 }).subscribe((questions) => {
//       this.topQuestions = questions.data;
//     });
//   }
// }
