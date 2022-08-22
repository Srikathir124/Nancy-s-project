import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { UsersService } from '../service/users.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  userid: string = '';
  password: string ='';
  password2: string ='';
  roleid: number=0;

  errorMessage: string = '';

  constructor(
    private router: Router,
    public app:AppComponent,
    private service: UsersService
  ) {}

  registerNewUser(){
    if(this.validateForm()){
      this.service.addUser(this.userid,this.password,this.roleid).subscribe({
        next:(value:string)=> {
          if(value==="Success"){
            this.app.openSnackBar('User '+this.userid+' Added Successfully','greenSnackBar');
            this.resetForm();
          }
          else if(value==="User Already Exists"){
            this.app.openSnackBar('User Already Exists','redSnackBar');
          }
          else{console.log(value);this.errorMessage="Invalid response received from backend";}
        },
        error:(e) => {
          console.log(e);
          alert("Error happened");
        }
      });
    }
    else{
      this.app.openSnackBar('Invalid Data','redSnackBar');
    }
  }
  getLoginPage(){
    this.router.navigate(['login']);
  }
  resetForm(){
    this.userid='';
    this.password='';
    this.password2='';
    this.roleid=0;
    this.errorMessage='';
  }

  validateForm():boolean{
    return this.userIdValidation()&&this.passwordValidation()&&this.roleValidation();
  }
    
  userIdErrorMessage:string='';
  userIdValidation():boolean{
    if(this.userid.length<6){
      this.userIdErrorMessage='UserId should contain atleast 6 charcters';return false;
    }
    this.userIdErrorMessage='';
    return true;
  }

  passwordErrorMessage:string='';
  password2ErrorMessage:string='';
  passwordValidation():boolean{
    this.passwordErrorMessage='';
    this.password2ErrorMessage='';
    if(this.password.length<6){
      this.passwordErrorMessage='Password should contain atleast 6 characters';return false;
    }
    if(!this.password.match('[0-9]')){
      this.passwordErrorMessage='Password should contain atleast 1 number';return false;
    }
    if(!this.password.match('[a-zA-Z]')){
      this.passwordErrorMessage='Password should contain atleast 1 letter';return false;
    }
    if(!(this.password2===this.password)){
      this.password2ErrorMessage="Passwords did not match";return false;
    }
    return true;
  }
  
  roleErrorMessage:string='';
  roleStatus:boolean=false;
  roleValidation():boolean{
    if(this.roleid==0){
      this.roleErrorMessage='Role is required';
      return false;
    }
    this.roleErrorMessage='';
    return true;
  }

  getRoleID(){
    return localStorage.getItem('RoleID');
  }

}