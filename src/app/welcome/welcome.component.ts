import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent{

  constructor(
    private router: Router
  ) { 
    if(localStorage.getItem('UserID')!=null){
      this.router.navigate(['home']);
    }
  }

  ngOnInit(): void {
  }

  getLoginPage(){
    this.router.navigate(['login']);
  }

}
