import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CoreModule } from 'src/app/core/core.module';
import { ClassesModule } from 'src/app/features/classes/classes.module';

@NgModule({
  declarations: [HomePageComponent],
  imports: [CommonModule, MatProgressSpinnerModule, CoreModule, ClassesModule],
  exports: [HomePageComponent],
})
export class HomePageModule {}
