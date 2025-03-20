import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRequestedBooksComponentComponent } from './admin-requested-books-component.component';

describe('AdminRequestedBooksComponentComponent', () => {
  let component: AdminRequestedBooksComponentComponent;
  let fixture: ComponentFixture<AdminRequestedBooksComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminRequestedBooksComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRequestedBooksComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
