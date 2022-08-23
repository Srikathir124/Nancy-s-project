import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl :string = "http://localhost:8080/";

  constructor(
    private httpClient:HttpClient
  ) { }

  updateBaseSheet(baseData:string[][]){
    return this.httpClient.post<string>(
      this.baseUrl+"update-basesheet",
      baseData
      );
  }

  updateMasterSheet(masterData:string[][]){
    return this.httpClient.post<string>(
      this.baseUrl+"update-mastersheet",
      masterData
      );
  }

  getDownloadLogs(){
    return this.httpClient.get<string[]>(
      this.baseUrl+"get-download-logs"
    );
  }
}
