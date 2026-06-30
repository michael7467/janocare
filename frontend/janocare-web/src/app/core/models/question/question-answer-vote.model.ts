import { ResourceModel } from "../common/resource.model";
import { Patient } from "../patient";
import { ProfessionalUser } from "../professional";

import { User } from "../user";
import { PatientQuestion } from "./patient-question.model";
import { QuestionAnswer } from "./question-answer.model";


export class QuestionAnswerVote extends ResourceModel<QuestionAnswerVote>  {


    vote: string;
    voteType: string;
    userId:number;
    answerId:number;
    patientQuestion:PatientQuestion;
    user:User;
    questionAnswer:QuestionAnswer;
    constructor(model?: Partial<QuestionAnswerVote>) {
        super(model);
    }
    
}