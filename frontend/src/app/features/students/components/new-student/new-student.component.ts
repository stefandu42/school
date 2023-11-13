import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IStudent } from 'src/app/core/models/IStudent';

@Component({
  selector: 'app-new-student',
  templateUrl: './new-student.component.html',
  styleUrls: ['./new-student.component.scss'],
})
export class NewStudentComponent implements OnInit {
  studentForm!: FormGroup;
  @Output() addEvent = new EventEmitter<IStudent>();

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.studentForm = this.formBuilder.group({
      firstname: [
        null,
        [
          Validators.required,
          Validators.maxLength(50),
          Validators.pattern(/^[^0-9]+$/),
        ],
      ],
      surname: [
        null,
        [
          Validators.required,
          Validators.maxLength(50),
          Validators.pattern(/^[^0-9]+$/),
        ],
      ],
    });
  }

  onSubmitForm(): void {
    if (!this.studentForm.invalid)
      this.addEvent.emit({ ...this.studentForm.value, id: 0 });
  }
}
