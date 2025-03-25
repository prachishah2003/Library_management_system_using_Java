import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminOverdueBooksComponent } from './admin-overdue-books.component';

describe('AdminOverdueBooksComponent', () => {
  let component: AdminOverdueBooksComponent;
  let fixture: ComponentFixture<AdminOverdueBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminOverdueBooksComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminOverdueBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
