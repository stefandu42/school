import { Component, OnInit } from '@angular/core';
import { IClass } from 'src/app/core/models/IClass';
import { IStudent } from 'src/app/core/models/IStudent';
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
