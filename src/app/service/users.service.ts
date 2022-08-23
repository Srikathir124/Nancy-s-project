import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private baseUrl :string = "http://localhost:8080/";
  constructor(
    private httpClient: HttpClient
  ) { }

  addUser(userid:string,password:string,roleid:number){
    return this.httpClient.post<string>(
      this.baseUrl+"add-user",
      {"userId":userid,"password":password,"roleId":roleid},
      {responseType:'text' as 'json'}
      );
  }

  authenticate(userid:string,password:string){
    return this.httpClient.post<User>(
      this.baseUrl+"authenticate",   
      {"userId":userid,"password":password},
    );
  }

  getBaseData(){
    return this.httpClient.get(
      this.baseUrl+"get-basedata"
    );
  }

  logDownload(log:string):void{
    this.httpClient.post(
      this.baseUrl+"log-download",
      log
    ).subscribe();
  }
}

export class User{
  status:string = '';
  userid:string = '';
  roleid:number = 0;
}