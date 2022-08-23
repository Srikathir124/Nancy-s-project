import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  @ViewChild('userpage') userPageLabel!:ElementRef;
  @ViewChild('adminpage') adminPageLabel!:ElementRef;
  @ViewChild('tracker') trackerLabel !:ElementRef;

  title :string = 'AllocationApp';
  showUserId :string = '';
  editFlag:boolean=false;
  showProfileFlag:boolean=false;
  currViewingPage:string='';

  constructor(
    private router : Router,
    private _snackbar:MatSnackBar
  ){}

  setViewingPage(newLabel:string){
    try{
    this.setLabelColor(this.currViewingPage,'#ddddf9');
    this.setLabelColor(newLabel,'grey');
    }catch(e){
    }
  }
  setLabelColor(label:string,colour:string){
    switch(label){
      case 'userpage':
        this.userPageLabel.nativeElement.style.backgroundColor=colour;break;
      case 'adminpage':
        this.adminPageLabel.nativeElement.style.backgroundColor=colour;break;
      case 'tracker':
        this.trackerLabel.nativeElement.style.backgroundColor=colour;break;
    }
    this.currViewingPage=label;
  }

  getLoginPage(){
    this.router.navigate(['login']);
  }
  getUserPage(){
    this.router.navigate(['home']);
  }
  getRegisterPage(){
    this.router.navigate(['register']);
  }
  getUpdateExcelPage(){
    this.router.navigate(['updateexcel']);
  }
  getDownloadTrackerPage(){
    this.router.navigate(['downloadtracker']);
  }

  sessionStarted():boolean{
    if(localStorage.getItem('UserID')!=null){return true;}
    return false;
  }

  logout(){
    this.router.navigate(['login']);
    this.openSnackBar('Logged Out','grey');
    localStorage.clear();
  }

  openSnackBar(message:string,snackbarclass:string){
    this._snackbar.open(message,'',{
      duration :2000,
      verticalPosition:'top',
      panelClass:[snackbarclass]
    });
  }

  getUserID(){
    return localStorage.getItem('UserID');
  }

  getRoleID(){
    return localStorage.getItem('RoleID');
  }
  getUserRole():string{
    let r = this.getRoleID();
    if(r==='1'){return 'Admin';}
    else if(r==='2'){return 'User';}
    return 'Not Logged In';
  }
}