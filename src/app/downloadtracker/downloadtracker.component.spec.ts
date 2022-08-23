import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DownloadtrackerComponent } from './downloadtracker.component';

describe('DownloadtrackerComponent', () => {
  let component: DownloadtrackerComponent;
  let fixture: ComponentFixture<DownloadtrackerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DownloadtrackerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DownloadtrackerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
