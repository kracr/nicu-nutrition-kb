import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvestigationMgmntComponent } from './investigation-mgmnt.component';

describe('InvestigationMgmntComponent', () => {
  let component: InvestigationMgmntComponent;
  let fixture: ComponentFixture<InvestigationMgmntComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvestigationMgmntComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvestigationMgmntComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
