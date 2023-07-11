import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from '@app/user/_service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            let error;
            switch (true) {
                case err.status === 401:
                    error = err.error.message || err.error.errors || err.statusText;

                    // auto logout if 401 response returned from api
                    // this.authenticationService.logout();
                    // if (!err.message.includes('/users/login')) {
                    //     // Reload to return to login page
                    //     location.reload();
                    // }
                    break;

                case err.statusText === 'Unknown Error':
                    error = err.error.message || err.error.errors || 'Service is temporary turn down, please connect admin';
                    break;

                default:
                    error = err.error.message || err.error.errors || err.statusText;
            }

            return throwError(error);
        }));
    }
}
