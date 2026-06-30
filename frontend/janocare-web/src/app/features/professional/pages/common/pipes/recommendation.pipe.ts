import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'recommendation',
  standalone: true,
})
export class RecommendationPipe implements PipeTransform {
  transform(value: any, args?: any): any {
    // replace this with your actual filter condition

    let total = value?.length;
    let recommended = value.filter((item) => item?.isDoctorRecommended === true)?.length;
    let recommendedPercentage = ((recommended / total) * 100).toFixed(2);
    return recommendedPercentage;
  }
}
