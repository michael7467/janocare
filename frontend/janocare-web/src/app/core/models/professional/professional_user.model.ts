import { User } from "../user";
import { ProfessionType } from "./profession-type.model";

export class ProfessionalUser{
    id: number;
    practicing_from: string;
    consultation_fee: string;
    booking_fee: string;
    instant_consultation_fee: string;
    up_votes:string;
    down_votes:string;
    view_counts:string;
    status: string;
    wallet_balance:string;
    professionTypeId:number;
    user:User;
    professionType:ProfessionType
}