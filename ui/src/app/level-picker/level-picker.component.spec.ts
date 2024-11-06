import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LevelPickerComponent } from './level-picker.component';

describe('LevelPickerComponent', () => {
  let component: LevelPickerComponent;
  let fixture: ComponentFixture<LevelPickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LevelPickerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LevelPickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
