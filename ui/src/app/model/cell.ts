export class Cell {
  value: number | undefined;
  xCoordinate: number;
  yCoordinate: number;

  isRight: boolean | undefined;

  constructor(value: number, x: number, y: number) {
    this.value = undefined;
    this.xCoordinate = x;
    this.yCoordinate = y;
    this.isRight = undefined;
  }
}
