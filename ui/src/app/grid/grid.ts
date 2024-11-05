import {Component, Inject, OnInit} from "@angular/core";
import {RouterOutlet} from "@angular/router";
import {Cell} from "../model/cell";
import {HttpClient, HttpClientModule, HttpErrorResponse} from "@angular/common/http";
import {catchError, map, Observable} from "rxjs";
import {ValueGrid} from "../model/value-grid";

@Component({
  selector: 'app-grid',
  // standalone: true,
  templateUrl: "./grid.html",
  // imports: [HttpClientModule]
})
export class Grid{

  size: number = 9;
  // cells: Cell[][];
  grid: Cell[][];

  constructor(private http: HttpClient) {
    this.getData();
    this.grid = [];
    for (let i = 0; i < this.size; i++) {
      this.grid[i] = [];
    }
    this.getData().subscribe((numbers: number[][]) => {
      for (let i = 0; i < numbers.length; i++) {
        for (let j = 0; j < numbers[i].length; j++) {
          this.grid[i][j] = new Cell(numbers[i][j], i, j, numbers[i][j] != 0);
        }
      }
    });
  }

  getData(): Observable<number[][]> {
    return this.http.get<ValueGrid>("http://127.0.0.1:8080/sudoku/solve", { withCredentials: true }, )
      .pipe(map(response => {
          if (response) {
            return response.grid;
          }
          return [];
        }
      ))
      .pipe(catchError((err: HttpErrorResponse, data) => {
        if(err.status == 400) {
          console.log("Got 400 status error, sending request to /random");
          return this.http.get<ValueGrid>("http://127.0.0.1:8080/sudoku/random", { withCredentials: true })
            .pipe(map(response => {
              if (response) {
                return response.grid;
              }
              return [];
            }))
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
    this.http.put<boolean>("http://127.0.0.1:8080/sudoku/check", {row: row, col: col, value: inputValue}, { withCredentials: true })
      .subscribe({
        next: (response: boolean) => {
          if (response) {
            this.grid[row][col].isRight = true;
            targetElement.classList.remove("is-invalid");
            targetElement.classList.add("is-valid");
          } else {
            targetElement.classList.remove("is-valid");
            targetElement.classList.add("is-invalid");
          }
        },
        error: (err) => {
          console.log(err);
        }
      });
  }
}
