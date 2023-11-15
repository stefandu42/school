import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environement } from 'src/environments/environments';

@Injectable({
  providedIn: 'root',
})
export class StudentsService {
  constructor(private http: HttpClient) {}

  /**
   * Deletes a specific student by his ID.
   *
   * @param idStudent - The ID of the student to be deleted.
   * @returns An observable of void.
   */
  deleteOneById(idStudent: number): Observable<void> {
    return this.http.delete<void>(
      `${environement.API_URL}/students/${idStudent}`
    );
  }
}
