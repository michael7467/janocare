import { CommonModule, DatePipe } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterModule } from '@angular/router';
// import { PostModel, PostService } from '../../../core';
import { ImgFallbackDirective, RemovePortPipe } from '../../../shared';
import { defaultRequestParams } from '../../../shared/tp-table/config';

@Component({
  selector: 'app-articles',
  standalone: true,
  imports: [CommonModule, RouterModule, DatePipe, ImgFallbackDirective, RemovePortPipe],
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss'],
})
export class ArticlesComponent implements OnInit {
  // topPosts: PostModel[];

  // postsService = inject(PostService);

  ngOnInit(): void {
    this._fetchData();
  }

  private _fetchData() {
    // this.postsService.getTableData({ ...defaultRequestParams, take: 3 }).subscribe((posts) => {
    //   this.topPosts = posts.data;
    // });
  }
}
