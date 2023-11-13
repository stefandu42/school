import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { SingleClassComponent } from './pages/single-class/single-class.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CoreModule } from './core/core.module';
import { ClassesModule } from './features/classes/classes.module';
import { StudentsModule } from './features/students/students.module';

@NgModule({
  declarations: [AppComponent, HomePageComponent, SingleClassComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatProgressSpinnerModule,
    CoreModule,
    ClassesModule,
    StudentsModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
