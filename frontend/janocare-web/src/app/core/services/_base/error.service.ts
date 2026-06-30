import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ErrorService {
  getClientErrorMessage(error: Error): string {
    if (!error) {
      const response = 'Something went wrong... :(';
      // :diappointed:
      return response;
    }
    return error && error.message ? error.message : error.toString();
  }

  getServerErrorMessage(error: HttpErrorResponse): string {
    return navigator.onLine ? error.error.message || error.message : 'No Internet Connection';
  }
}
