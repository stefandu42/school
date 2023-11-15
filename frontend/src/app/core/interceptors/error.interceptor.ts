import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';
import { openSnackBar } from '../../utils/snackbar';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private snackBar: MatSnackBar) {}

  /**
   * Intercepts HTTP requests and handles errors globally by displaying a snack bar notification.
   *
   * @param request - The intercepted HTTP request.
   * @param next - The next HTTP handler in the chain.
   * @returns An observable of the HTTP event with a specific message, depending on the status error.
   */
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = 'The server encountered an error';
        if (error.status !== 500 && error.status !== 0)
          message = error.error.message;
        openSnackBar(this.snackBar, message, 'Close');

        return throwError(() => error);
      })
    );
  }
}
