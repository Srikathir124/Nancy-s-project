import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { User, UsersService } from '../service/users.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{

  userid:string = '';
  password:string = '';
  token:string = '';

  constructor(
    private router: Router,
    private service: UsersService,
    private app:AppComponent
  ) {
    if(localStorage.getItem('UserID')!=null){
      this.router.navigate(['home']);
    }
  }
  
  getHomePage(){
    if(this.userIdValidation()&&this.passwordValidation()){
      this.service.authenticate(this.userid,this.password).subscribe({
        next:(user:User) => {
            if(user.status==='Valid Credentials'){
              this.router.navigate(['home']);
              localStorage.setItem('UserID',String(this.userid));
              localStorage.setItem('RoleID',String(user.roleid));
              this.app.openSnackBar('Logged In as '+this.userid,'greenSnackBar');
            }
            else if(user.status==='Invalid Credentials'){
              this.app.openSnackBar('Invalid UserName/Password','redSnackBar');
            }
            else if(user.status==='User Not Found'){
              this.app.openSnackBar('User Not Exists','redSnackBar');
            }
            else{
              alert("Invalid Response");
            }
        },
        error:(e)=>{
          console.log(e);
          alert("Error Response");
        }
      });
    }else{
      this.app.openSnackBar('Invalid Credentials','redSnackBar');
    }
  }

  userIdErrorMessage:string='';
  userIdValidation():boolean{
    if(this.userid.length<6){
      this.userIdErrorMessage='Invalid UserID';return false;
    }
    this.userIdErrorMessage='';
    return true;
  }

  passwordErrorMessage:string='';
  passwordValidation():boolean{
    if(this.password.length<6||!this.password.match('[0-9]')||!this.password.match('[a-zA-Z]')){
      this.passwordErrorMessage='Invalid Password';return false;
    }
    this.passwordErrorMessage='';
    return true;
  }

}