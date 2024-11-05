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
  grid: number[][];

  constructor(private http: HttpClient) {
    this.getData();
    this.grid = [];
    for (let i = 0; i < this.size; i++) {
      this.grid[i] = [];
    }
    this.getData().subscribe((numbers: number[][]) => {
      this.grid = numbers;
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
}
