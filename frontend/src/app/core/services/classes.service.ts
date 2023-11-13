import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IClass } from '../models/IClass';
import { environement } from 'src/environments/environments';
import { IStudent } from '../models/IStudent';

@Injectable({
  providedIn: 'root',
})
export class ClassesService {
  constructor(private http: HttpClient) {}

  getAllClasses(): Observable<IClass[]> {
    return this.http.get<IClass[]>(`${environement.API_URL}/classes`);
  }

  getOneById(idClass: number): Observable<IClass> {
    return this.http.get<IClass>(`${environement.API_URL}/classes/${idClass}`);
  }

  createOne(newClass: IClass): Observable<IClass> {
    return this.http.post<IClass>(`${environement.API_URL}/classes`, newClass);
  }

  deleteOneById(idClass: number): Observable<void> {
    return this.http.delete<void>(`${environement.API_URL}/classes/${idClass}`);
  }

  getAllStudentsByIdClass(idClass: number): Observable<IStudent[]> {
    return this.http.get<IStudent[]>(
      `${environement.API_URL}/classes/${idClass}/students`
    );
  }

  createOneStudent(
    newStudent: IStudent,
    idClass: number
  ): Observable<IStudent> {
    return this.http.post<IStudent>(
      `${environement.API_URL}/classes/${idClass}/students`,
      newStudent
    );
  }
}
