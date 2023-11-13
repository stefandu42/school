import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IClass } from 'src/app/core/models/IClass';
import { IStudent } from 'src/app/core/models/IStudent';

@Component({
  selector: 'app-card-class-student',
  templateUrl: './card-class-student.component.html',
  styleUrls: ['./card-class-student.component.scss'],
})
export class CardClassStudentComponent implements OnInit {
  isDeleteButton: boolean = true;
  @Input() studentOrClass!: IStudent | IClass;
  @Input() typeOfClassReceived!: 'student' | 'class';
  @Output() deleteEvent = new EventEmitter<IClass | IStudent>();

  ngOnInit(): void {}

  onDeleteButton(): void {
    this.isDeleteButton = false;
  }

  onCancelButton(): void {
    this.invertDisplayButtonDelete();
  }

  invertDisplayButtonDelete(): void {
    this.isDeleteButton = !this.isDeleteButton;
  }

  onDeleteEvent(obj: any): void {
    this.deleteEvent.emit(obj);
  }

  getTitle(): string {
    if (this.typeOfClassReceived === 'student') {
      return (
        (this.studentOrClass as IStudent).firstname +
        ' ' +
        (this.studentOrClass as IStudent).surname
      );
    } else {
      return (this.studentOrClass as IClass).name;
    }
  }
}
