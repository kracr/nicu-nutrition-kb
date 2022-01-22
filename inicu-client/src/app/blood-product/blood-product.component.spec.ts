import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodProductComponent } from './blood-product.component';

describe('BloodProductComponent', () => {
  let component: BloodProductComponent;
  let fixture: ComponentFixture<BloodProductComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BloodProductComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BloodProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
