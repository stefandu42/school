import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IClass } from 'src/app/models/IClass';

@Component({
  selector: 'app-new-class',
  templateUrl: './new-class.component.html',
  styleUrls: ['./new-class.component.scss'],
})
export class NewClassComponent implements OnInit {
  classForm!: FormGroup;
  @Output() addEvent = new EventEmitter<IClass>();

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.classForm = this.formBuilder.group({
      name: [
        null,
        [
          Validators.required,
          Validators.maxLength(30),
          Validators.pattern(/^[^0-9]+$/),
        ],
      ],
    });
  }

  onSubmitForm(): void {
    if (!this.classForm.invalid)
      this.addEvent.emit({ ...this.classForm.value, id: 0 });
  }
}
