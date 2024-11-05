import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Grid} from "./grid/grid";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  // standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ui';
}
