import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewClassComponent } from './components/new-class/new-class.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { SingleClassComponent } from './pages/single-class/single-class.component';

const routes: Routes = [
  { path: 'class/:id', component: SingleClassComponent },
  { path: '', component: HomePageComponent },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
