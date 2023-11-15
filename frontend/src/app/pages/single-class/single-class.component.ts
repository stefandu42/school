import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import { IClass } from 'src/app/core/models/IClass';
import { IStudent } from 'src/app/core/models/IStudent';
import { ClassesService } from 'src/app/core/services/classes.service';
import { StudentsService } from 'src/app/core/services/students.service';

@Component({
  selector: 'app-single-class',
  templateUrl: './single-class.component.html',
  styleUrls: ['./single-class.component.scss'],
})
export class SingleClassComponent implements OnInit {
  class!: IClass;
  students!: IStudent[];
  classId!: number;
  showErrorImage: boolean = false;
  isClassLoading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private studentsService: StudentsService,
    private classesService: ClassesService
  ) {}

  ngOnInit(): void {
    this.classId = +this.route.snapshot.params['id'];

    this.classesService
      .getOneById(this.classId)
      .pipe(
        tap((data) => {
          this.class = data;
          this.isClassLoading = false;
        }),
        catchError((error) => {
          this.isClassLoading = false;
          this.showErrorImage = true;
          return throwError(() => error);
        }),
        switchMap(() =>
          this.classesService.getAllStudentsByIdClass(this.classId)
        )
      )
      .subscribe({
        next: (data) => {
          this.students = data;
        },
        error: (error) => {},
      });
  }

  /**
   * Handles the deletion of a student.
   * Deletes the student with the specified ID.
   * Updates the local students array upon successful deletion.
   *
   * @param obj - The object containing information about the student to be deleted.
   */
  onDeteteStudent(obj: any): void {
    this.studentsService.deleteOneById(obj.studentOrClass.id).subscribe({
      next: (data) => {
        this.students = this.students.filter(
          (student) => student.id !== obj.studentOrClass.id
        );
      },
      error: (error) => {},
    });
  }

  /**
   * Handles the addition of a new student.
   * Adds the new student and updates the local students array upon success.
   *
   * @param newStudent - The new student to be added.
   */
  onAddStudent(newStudent: IStudent): void {
    newStudent.idClass = this.classId;
    this.classesService.createOneStudent(newStudent, this.classId).subscribe({
      next: (data) => {
        this.students.push(data);
      },
      error: (error) => {},
    });
  }
}
