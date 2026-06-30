import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";

export class BlogCategory extends ResourceModel<BlogCategory>  {

 
    title: string;
    metaTitle: string;
    slug: string;

    constructor(model?: Partial<BlogCategory>) {
        super(model);
    }
    
}