import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminReturnsComponent } from './admin-returns.component';

describe('AdminReturnsComponent', () => {
  let component: AdminReturnsComponent;
  let fixture: ComponentFixture<AdminReturnsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminReturnsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminReturnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
