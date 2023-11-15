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

  /**
   * Disables the delete button by setting the isDeleteButton property to false.
   */
  onDeleteButton(): void {
    this.isDeleteButton = false;
  }

  /**
   * Cancels the ongoing action (deletion) and inverts the display of the delete button.
   */
  onCancelButton(): void {
    this.invertDisplayButtonDelete();
  }

  /**
   * Inverts the display state of the delete button.
   */
  invertDisplayButtonDelete(): void {
    this.isDeleteButton = !this.isDeleteButton;
  }

  /**
   * Emits a delete event with the specified object (object containg the typeOfClassReceived and studentOrClass properties).
   *
   * @param obj - The object to be deleted.
   */
  onDeleteEvent(obj: any): void {
    this.deleteEvent.emit(obj);
  }

  /**
   * Returns the card title based on the type of class received.
   *
   * @returns The card title.
   */
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
