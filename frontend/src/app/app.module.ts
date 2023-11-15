import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreModule } from './core/core.module';
import { ClassesModule } from './features/classes/classes.module';
import { StudentsModule } from './features/students/students.module';
import { HomePageModule } from './pages/home-page/home-page.module';
import { SinglePageModule } from './pages/single-class/single-page.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CoreModule,
    ClassesModule,
    StudentsModule,
    HomePageModule,
    SinglePageModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
