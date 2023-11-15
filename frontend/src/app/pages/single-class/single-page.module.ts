import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SingleClassComponent } from './single-class.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { StudentsModule } from 'src/app/features/students/students.module';
import { CoreModule } from 'src/app/core/core.module';

@NgModule({
  declarations: [SingleClassComponent],
  imports: [CommonModule, CoreModule, MatProgressSpinnerModule, StudentsModule],
  exports: [SingleClassComponent],
})
export class SinglePageModule {}
