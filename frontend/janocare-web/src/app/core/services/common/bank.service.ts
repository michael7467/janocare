import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResourceService } from '../resource.service';
import { environment } from '../../../../environments/environment';
import { Bank } from '../../models';
import { professionalEndPoint } from '../../../';

@Injectable({ providedIn: 'root' })
export class BankService extends ResourceService<Bank> {
  constructor(private http: HttpClient) {
    super(http, Bank, `${environment.apiUrl}/public/banks`);
  }
}

/* 
{{URL}}/common/banks

{
    "success": true,
    "statusCode": 200,
    "message": "Successful",
    "data": [
        {
            "id": 1,
            "bank_name": "Anbessa",
            "is_active": true
        },
        {
            "id": 3,
            "bank_name": "CBE - updated",
            "is_active": false
        },
        {
            "id": 4,
            "bank_name": "CBE1",
            "is_active": false
        }
    ],
    "meta": {
        "page": 1,
        "take": 50,
        "itemCount": 3,
        "pageCount": 1,
        "hasPreviousPage": false,
        "hasNextPage": false
    }
}
*/
