import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { AdminService } from '../service/admin.service';

@Component({
  selector: 'app-downloadtracker',
  templateUrl: './downloadtracker.component.html',
  styleUrls: ['./downloadtracker.component.css']
})
export class DownloadtrackerComponent implements OnInit {

  constructor(
    private service: AdminService,
    private app: AppComponent
  ) { }


  logs:string[] = [];
  ngOnInit(): void {
    this.service.getDownloadLogs().subscribe({
      next:(value:string[]) => {
        this.logs=value;
      },
      error:(e) => {
        this.app.openSnackBar("Unable to load Download Logs","redSnackBar")
      }
    });
    this.app.setViewingPage('tracker');
  }

}
