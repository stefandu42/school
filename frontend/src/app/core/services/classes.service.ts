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

  /**
   * Retrieves all classes.
   *
   * @returns An observable of an array of all classes.
   */
  getAllClasses(): Observable<IClass[]> {
    return this.http.get<IClass[]>(`${environement.API_URL}/classes`);
  }

  /**
   * Retrieves a specific class by its ID.
   *
   * @param idClass - The ID of the class to retrieve.
   * @returns An observable of the class found.
   */
  getOneById(idClass: number): Observable<IClass> {
    return this.http.get<IClass>(`${environement.API_URL}/classes/${idClass}`);
  }

  /**
   * Creates a new class.
   *
   * @param newClass - The class object to be created.
   * @returns An observable of the created class.
   */
  createOne(newClass: IClass): Observable<IClass> {
    return this.http.post<IClass>(`${environement.API_URL}/classes`, newClass);
  }

  /**
   * Deletes a specific class by its ID.
   * @param idClass - The ID of the class to be deleted.
   * @returns An observable of void.
   */
  deleteOneById(idClass: number): Observable<void> {
    return this.http.delete<void>(`${environement.API_URL}/classes/${idClass}`);
  }

  /**
   * Retrieves all students belonging to a specific class by its ID.
   * @param idClass - The ID of the class to retrieve students from.
   * @returns An observable of an array of students.
   */
  getAllStudentsByIdClass(idClass: number): Observable<IStudent[]> {
    return this.http.get<IStudent[]>(
      `${environement.API_URL}/classes/${idClass}/students`
    );
  }

  /**
   * Creates a new student within a specific class.
   * @param newStudent - The student object to be created.
   * @param idClass - The ID of the class the student will be added.
   * @returns An observable of the created student.
   */
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
