import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { NewClassComponent } from './components/new-class/new-class.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { SingleClassComponent } from './pages/single-class/single-class.component';
import { FooterComponent } from './components/footer/footer.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CardClassStudentComponent } from './components/card-class-student/card-class-student.component';
import { MatInputModule } from '@angular/material/input';
import { ErrorInterceptor } from './interceptors/error.interceptor';
import { NewStudentComponent } from './components/new-student/new-student.component';

@NgModule({
  declarations: [
    AppComponent,
    NewClassComponent,
    HomePageComponent,
    SingleClassComponent,
    FooterComponent,
    NavbarComponent,
    CardClassStudentComponent,
    NewStudentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatSlideToggleModule,
    ReactiveFormsModule,
    MatIconModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
