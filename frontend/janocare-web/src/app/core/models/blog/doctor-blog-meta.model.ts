import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";

export class DoctorBlogMeta extends ResourceModel<DoctorBlogMeta>  {

    imageTitle: Date;
    imageUrl: string;
    postId: string;


    constructor(model?: Partial<DoctorBlogMeta>) {
        super(model);
    }
    
}