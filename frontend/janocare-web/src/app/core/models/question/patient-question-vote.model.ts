import { ResourceModel } from "../common/resource.model";
import { Patient } from "../patient";
import { ProfessionalUser } from "../professional";
import { User } from "../user";
import { PatientQuestion } from "./patient-question.model";

export class PatientQuestionVote extends ResourceModel<PatientQuestionVote>  {


    voteType: string;
     vote:string;
    patientQuestionId:number;
    questionId:number;
    userId:number;
    patientQuestion:PatientQuestion;
    user:User;
    constructor(model?: Partial<PatientQuestionVote>) {
        super(model);
    }
    
}