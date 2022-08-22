import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import * as XLSX from 'xlsx';
import { AdminService } from '../service/admin.service';

@Component({
  selector: 'app-modifyexcel',
  templateUrl: './modifyexcel.component.html',
  styleUrls: ['./modifyexcel.component.css']
})
export class ModifyexcelComponent implements OnInit {

  constructor(
    protected app:AppComponent,
    private service:AdminService
  ) { 
    this.app.setViewingPage('adminpage');
  }

  ngOnInit(): void {}

  baseData: string[][] = [];
  baseSheetMessage='';
  baseSheetValidation(e1:any){
    const target1:DataTransfer = <DataTransfer>e1.target;
    if(target1.files.length==1){
      const reader1: FileReader = new FileReader();
      reader1.onload = (e: any) => {
        const binaryString: string = e.target.result;
        const wb: XLSX.WorkBook = XLSX.read(binaryString, {type: 'binary'});
        const ws: XLSX.WorkSheet = wb.Sheets['Sheet1'];
        this.baseData= (XLSX.utils.sheet_to_json(ws, {header:1}));
        if(this.checkBaseSheetFormat())
        {
          for(let i=1;i<this.baseData.length;i++){
            this.baseData[i][9] = this.ExcelDateToJSDate(Number(this.baseData[i][9])).toDateString();
            this.baseData[i][10] = this.ExcelDateToJSDate(Number(this.baseData[i][10])).toDateString();
            this.baseData[i][52] = this.ExcelDateToJSDate(Number(this.baseData[i][52])).toDateString();
            this.baseData[i][53] = this.ExcelDateToJSDate(Number(this.baseData[i][53])).toDateString();
            this.baseData[i][57] = this.ExcelDateToJSDate(Number(this.baseData[i][57])).toDateString();
          }
          this.service.updateBaseSheet(this.baseData).subscribe();
          this.app.openSnackBar("File uploaded successfully",'greenSnackBar');
        }else{
          this.app.openSnackBar('Invalid Format for BaseSheet ','redSnackBar');
        }
      };
      reader1.readAsBinaryString(target1.files[0]);
    }
    else{
        this.app.openSnackBar('Multiple Files Not Allowed','redSnackBar');
    }
    e1.target.value=null;
  }

  masterData:string[][] = [];
  masterSheetMessage='';
  masterSheetValidation(e2:any){
    const target2:DataTransfer = <DataTransfer>e2.target;
    if(target2.files.length==1){
      const reader2: FileReader = new FileReader();
      reader2.onload = (e: any) => {
        const binaryString: string = e.target.result;
        const wb: XLSX.WorkBook = XLSX.read(binaryString, {type: 'binary'});
        const ws: XLSX.WorkSheet = wb.Sheets['Sheet1'];
        this.masterData= (XLSX.utils.sheet_to_json(ws, {header:1}));
        if(this.checkMasterSheetFormat())
        {
          this.service.updateMasterSheet(this.masterData).subscribe();
          this.app.openSnackBar("File uploaded successfully",'greenSnackBar');
        }else{
          this.app.openSnackBar('Invalid Format for MasterSheet ','redSnackBar');
        }
      };
      reader2.readAsBinaryString(target2.files[0]);
    }
    else{
      this.app.openSnackBar('Multiple Files Not Allowed','redSnackBar');
    }
    e2.target.value=null;
  }

  checkBaseSheetFormat():boolean{
    if(
      this.baseData[0][0] === "Sr.No." &&
      this.baseData[0][1] === "Associate ID" &&
      this.baseData[0][2] === "Associate Name" &&
      this.baseData[0][3] === "Grade HR" &&
      this.baseData[0][4] === "On/Off" &&
      this.baseData[0][5] === "Home Manager ID" &&
      this.baseData[0][6] === "Project ID" &&
      this.baseData[0][7] === "Project Description" &&
      this.baseData[0][8] === "Percent Allocation" &&
      this.baseData[0][9] === "Assignment Start Date" &&
      this.baseData[0][10] === "Date of Joining" &&
      this.baseData[0][11] === "FIN Department ID" &&
      this.baseData[0][12] === "Department Name" &&
      this.baseData[0][13] === "Project Billability" &&
      this.baseData[0][14] === "Project Type" &&
      this.baseData[0][15] === "Project Status" &&
      this.baseData[0][16] === "Project_Solution_Type" &&
      this.baseData[0][17] === "Project Manager ID" &&
      this.baseData[0][18] === "Project Manager Name" &&
      this.baseData[0][19] === "Account ID" &&
      this.baseData[0][20] === "Account Name" &&
      this.baseData[0][21] === "Parent Customer" &&
      this.baseData[0][22] === "Pool ID" &&
      this.baseData[0][23] === "Pool Description" &&
      this.baseData[0][24] === "JobCode" &&
      this.baseData[0][25] === "Designation" &&
      this.baseData[0][26] === "Grade" &&
      this.baseData[0][27] === "Associate Base Hiring Location" &&
      this.baseData[0][28] === "HCM SetID" &&
      this.baseData[0][29] === "Project Owning Department" &&
      this.baseData[0][30] === "Project Owning Practice" &&
      this.baseData[0][31] === "Vertical" &&
      this.baseData[0][32] ==="Billability Status" &&
      this.baseData[0][33] === "Primary State Tag" &&
      this.baseData[0][34] === "Assignment ID" &&
      this.baseData[0][35] === "SO ID" &&
      this.baseData[0][36] === "SO Line" &&
      this.baseData[0][37] === "Critical Flag" &&
      this.baseData[0][38] === "Location ID" &&
      this.baseData[0][39] === "SEZ Flag" &&
      this.baseData[0][40] === "Country" &&
      this.baseData[0][41] === "State" &&
      this.baseData[0][42] === "City" &&
      this.baseData[0][43] === "Assignment End Date" &&
      this.baseData[0][44] === "Assignment Status" &&
      this.baseData[0][45] === "Project Role" &&
      this.baseData[0][46] === "Operational Role" &&
      this.baseData[0][47] === "Assignment location" &&
      this.baseData[0][48] === "Assgn City" &&
      this.baseData[0][49] === "Assgn State" &&
      this.baseData[0][50] === "Assgn Country" &&
      this.baseData[0][51] === "Location Reason code" &&
      this.baseData[0][52] === "Project Start Date" &&
      this.baseData[0][53] === "Project End Date" &&
      this.baseData[0][54] === "Sub Vertical" &&
      this.baseData[0][55] === "SBU1" &&
      this.baseData[0][56] === "SBU2" &&
      this.baseData[0][57] === "Contractor End Date" &&
      this.baseData[0][58] === "Comment" &&
      this.baseData[0][59] === "Pool Resource" &&
      this.baseData[0][60] === "Competency" &&
      this.baseData[0][61]=== "Secondary State Tag" &&
      this.baseData[0][62]=== "Replaced Employee" &&
      this.baseData[0][63]=== "Comments" &&
      this.baseData[0][64]=== "Client Facility"){
      return true;
      }
      return false;
  }

  checkMasterSheetFormat():boolean{
    if(
      this.masterData[0][0] === 'Associate ID' &&
      this.masterData[0][1] === 'Associate Name' &&
      this.masterData[0][2] === 'Home Manager ID' &&
      this.masterData[0][3] === 'HCM Name'	&&
      this.masterData[0][4] === 'HCM Dept'	&&
      this.masterData[0][5] === 'Area'	&&
      this.masterData[0][6] === 'Leader' &&
      this.masterData[0][7] === 'Initiative'){
        return true;
      }
      return false;
  }

  ExcelDateToJSDate(serial:number) {
    var utc_days  = Math.floor(serial - 25569);
    var utc_value = utc_days * 86400;                                        
    var date_info = new Date(utc_value * 1000);
 
    var fractional_day = serial - Math.floor(serial) + 0.0000001;
 
    var total_seconds = Math.floor(86400 * fractional_day);
 
    var seconds = total_seconds % 60;
 
    total_seconds -= seconds;
 
    var hours = Math.floor(total_seconds / (60 * 60));
    var minutes = Math.floor(total_seconds / 60) % 60;
 
    return new Date(date_info.getFullYear(), date_info.getMonth(), date_info.getDate(), hours, minutes, seconds);
 }
}
