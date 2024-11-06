import { Component } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-level-picker',
  templateUrl: './level-picker.component.html',
  styleUrl: './level-picker.component.css'
})
export class LevelPickerComponent {

  constructor(private http: HttpClient, private router: Router) {
  }

  generateLevelByNumber(level: number) {
    this.http.get("/sudoku-api/sudoku/level/" + level, { withCredentials:true, observe: 'response'  })
      .subscribe({
        next: (response: HttpResponse<any>) => {
          if (response) {
            setTimeout(() => {
              this.router.navigate([response.headers.get("X-Redirect-Location")])
            }, 1000);
          }
        },
        error: (err) => {
          console.log("unable to generate level : " + err);
        }
      });
  }

  generateRandomLevel() {
    this.http.get("/sudoku-api/sudoku/random", { withCredentials:true, observe: 'response' })
      .subscribe({
        next: (response: HttpResponse<any>) => {
          console.log(response);
          console.log(response.headers);
          if (response) {
            setTimeout(() => {
              this.router.navigate([response.headers.get("X-Redirect-Location")])
            }, 1000);
          }
        },
        error: (err) => {
          console.log("unable to generate level");
          console.log(err);
        }
      });
  }
}
