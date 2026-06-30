import { ResourceModel } from "../common/resource.model";
import { Patient } from "../patient";
import { ProfessionalUser } from "../professional";
import { PatientQuestionVote } from "./patient-question-vote.model";

export class PatientQuestion extends ResourceModel<PatientQuestion>  {

    publishedAt: Date;
    status:string;
    views:number;
    upVotes:number;
    downVotes:number;
    content:string;
    professionalId:number;
    postCategoryId:number;
    professional:ProfessionalUser;
    patientQuestionVotes:PatientQuestionVote[];
    constructor(model?: Partial<PatientQuestion>) {
        super(model);
    }
    
}