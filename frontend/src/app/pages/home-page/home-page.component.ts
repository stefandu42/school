import { Component, OnInit } from '@angular/core';
import { IClass } from 'src/app/core/models/IClass';
import { ClassesService } from 'src/app/core/services/classes.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent implements OnInit {
  constructor(private classesService: ClassesService) {}

  classes!: IClass[];

  ngOnInit(): void {
    this.classesService.getAllClasses().subscribe({
      next: (data) => {
        this.classes = data;
      },
      error: (error) => {},
    });
  }

  /**
   * Handles the deletion of a class.
   * Deletes the class with the specified ID.
   * Updates the local classes array upon successful deletion.
   *
   * @param obj - The object containing information about the class to be deleted.
   */
  onDeteteClass(obj: any): void {
    this.classesService.deleteOneById(obj.studentOrClass.id).subscribe({
      next: (data) => {
        this.classes = this.classes.filter(
          (cls) => cls.id !== obj.studentOrClass.id
        );
      },
      error: (error) => {},
    });
  }

  /**
   * Handles the addition of a new class.
   * Adds the new class and updates the local classes array upon success.
   *
   * @param newClass - The new class to be added.
   */
  onAddClass(newClass: IClass): void {
    /* RETURNS UNDESIRED LOG IN CONSOLE
    this.classesService.createOne(newClass).pipe(
      tap((data) => this.classes.push(data))
    ).subscribe();*/

    this.classesService.createOne(newClass).subscribe({
      next: (data) => {
        this.classes.push(data);
      },
      error: (error) => {},
    });
  }
}
