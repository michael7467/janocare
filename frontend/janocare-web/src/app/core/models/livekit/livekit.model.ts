import { ResourceModel } from '../common/resource.model';

export class Livekit extends ResourceModel<Livekit>  {

    to: number;
    accessToken:string;
    constructor(model?: Partial<Livekit>) {
        super(model);
    }
    
}