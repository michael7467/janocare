import { ResourceModel } from "../common/resource.model";

export class ProfessionalReview extends ResourceModel<ProfessionalReview>  {

    isReviewAnonymous: boolean;
    waitTimeRating: string;
    mannerRating: string;
    overallRating: number;
    review: string;
    isDoctorRecommended:boolean;

    professionalId: number;
    patientId: number;

    constructor(model?: Partial<ProfessionalReview>) {
        super(model);
    }
  
  
  }