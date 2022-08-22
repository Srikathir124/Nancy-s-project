import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { InvalidUrlComponent } from './invalid-url/invalid-url.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { ModifyexcelComponent } from './modifyexcel/modifyexcel.component';

const routes: Routes = [
  {path:'',component:WelcomeComponent},
  {path:'login',component:LoginComponent},
  {path:'home',component: HomeComponent},
  {path:'register',component:RegisterComponent},
  {path:'updateexcel',component:ModifyexcelComponent},
  {path:'**',component:InvalidUrlComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
