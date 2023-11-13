import { Component, OnInit } from '@angular/core';
import { IClass } from './core/models/IClass';
import { ClassesService } from './core/services/classes.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
