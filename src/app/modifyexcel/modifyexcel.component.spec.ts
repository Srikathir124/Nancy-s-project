import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyexcelComponent } from './modifyexcel.component';

describe('ModifyexcelComponent', () => {
  let component: ModifyexcelComponent;
  let fixture: ComponentFixture<ModifyexcelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifyexcelComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifyexcelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
