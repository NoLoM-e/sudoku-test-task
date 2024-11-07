import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {Cell} from "../model/cell";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, map, Observable} from "rxjs";
import {ValueGrid} from "../model/value-grid";

@Component({
  selector: 'app-grid',
  // standalone: true,
  templateUrl: "./grid.html",
  // imports: [HttpClientModule]
})
export class Grid implements OnInit {

  size: number = 9;
  // cells: Cell[][];
  grid: Cell[][];

  constructor(private http: HttpClient, private router: Router) {
    this.grid = [];
    for (let i = 0; i < this.size; i++) {
      this.grid[i] = [];
    }
  }

  ngOnInit(): void {
    setTimeout(() => this.getData().subscribe((numbers: number[][]) => {
      for (let i = 0; i < numbers.length; i++) {
        for (let j = 0; j < numbers[i].length; j++) {
          this.grid[i][j] = new Cell(numbers[i][j], i, j, numbers[i][j] != 0);
        }
      }
    }), 100);
  }

  getData(): Observable<number[][]> {
    return this.http.get<ValueGrid>("/sudoku-api/sudoku/solve", { withCredentials: true }, )
      .pipe(map(response => {
          if (response) {
            return response.grid;
          }
          return [];
        }
      ))
      .pipe(catchError((err: HttpErrorResponse, data) => {
        if(err.status == 400) {
          console.log("Got 400 status error, sending request to /levels");
          this.returnToLevels();
          return [];
        }
        else {
          return [];
        }
      }));
  }

  checkInput(event: Event, row: number, col: number) {
    let targetElement = event.target as HTMLInputElement;
    let inputValue = targetElement.value;
    if (!/^[1-9]$/.test(inputValue)) {
      console.log("Input not matched the regex");
      targetElement.value = "";
      return;
    }
    this.http.put<boolean>("/sudoku-api/sudoku/check", {row: row, col: col, value: inputValue}, { withCredentials: true })
      .subscribe({
        next: (response: boolean) => {
          if (response) {
            this.grid[row][col].isRight = true;
            targetElement.classList.remove("is-invalid");
            targetElement.classList.add("is-valid");
            targetElement.value = inputValue; // to replace the value in case of spamming and clearing the field

            setTimeout(() => {
              targetElement.classList.remove("is-valid");
            }, 500);
          } else {
            targetElement.classList.add("is-invalid");

            setTimeout(() => {
              targetElement.classList.remove("is-invalid");
            }, 500);
          }
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  returnToLevels() {
    this.router.navigate(['/levels']);
  }
}
