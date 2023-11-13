import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardClassStudentComponent } from './card-class-student.component';

describe('CardClassStudentComponent', () => {
  let component: CardClassStudentComponent;
  let fixture: ComponentFixture<CardClassStudentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardClassStudentComponent]
    });
    fixture = TestBed.createComponent(CardClassStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
