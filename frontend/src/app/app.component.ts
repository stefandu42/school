import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IClass } from './models/IClass';
import { ClassesService } from './services/classes.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  class!: IClass;
  error!: string;

  constructor(private classesService : ClassesService){}

  ngOnInit(): void {
    
  }

  
}
