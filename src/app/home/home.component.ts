import { Component } from '@angular/core';
import { AppComponent } from '../app.component';
import { UsersService } from '../service/users.service';
import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';

const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  constructor(
    protected app: AppComponent,
    protected service: UsersService
  ) {
    this.downloadFlag=false;
    this.app.setViewingPage('userpage');
  }

  checked:boolean = false;
  radio:string = '';
  baseDataList:any;
  downloadFlag:boolean=false;

  public downloadExcel(): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.baseDataList,);
    const workbook: XLSX.WorkBook = { Sheets: { 'Details': worksheet }, SheetNames: ['Details'] };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    const data: Blob = new Blob([excelBuffer], {type: EXCEL_TYPE});
    FileSaver.saveAs(data, 'Allocation Details.xlsx');
    const dt = new Date();
    this.service.logDownload(this.app.getUserID()+' downloaded on '+ new Date());
  }

  loadBaseData():void{
    this.service.getBaseData().subscribe({
      next:(value:any)=>{
        this.baseDataList=value;
        console.log(this.baseDataList);
      },
      error:(e) => {
        this.app.openSnackBar("Unable to load Table Data","redSnackBar");
      }
    });
    this.downloadFlag=true;
  }

  baseColumns:string[] = ['Sr.No.', 'Associate ID',	'Associate Name',	'Grade HR',	'Associate Dept',
  	'On/Off',	'Home Manager ID',	'HCM Name',	'HCM Dept','Area',	'Leader',	'SGA',	'Project ID',
    'Project Description',	'Percent Allocation', 'FTE',	'Cost',	'Current Status',	'Assignment Start Date',
    'Date of Joining',	'FIN Department ID',	'Department Name',	'Project Billability',	'Project Type',
    'Project Status',	'Project_Solution_Type',	'Project Manager ID',	'Project Manager Name',	'Account ID',
    'Account Name',	'Parent Customer',	'Pool ID',	'Pool Description',	'JobCode',	'Designation',
    'Grade',	'Associate Base Hiring Location',	'HCM SetID',	'Project Owning Department',
    'Project Owning Practice',	'Vertical',	'Billability Status',	'Primary State Tag',  'Assignment ID',
    'SO ID',	'SO Line',	'Critical Flag',	'Location ID',	'SEZ Flag',	'Country',	'State',	'City',
    'Assignment End Date',	'Assignment Status',	'Project Role',	'Operational Role',	'Assignment location',
    'Assgn City',	'Assgn State',	'Assgn Country',	'Location Reason code',	'Project Start Date',
    'Project End Date',	'Sub Vertical',	'SBU1',	'SBU2',	'Contractor End Date',	'Comment',	
    'Pool Resource',	'Competency',	'Secondary State Tag',	'Replaced Employee',	'Comments',	'Client Facility'
  ];

}

export class BaseData{

}