export class Cell {
  value: number;
  xCoordinate: number;
  yCoordinate: number;

  isRight: boolean;

  constructor(value: number, x: number, y: number, isRight: boolean) {
    this.value = value;
    this.xCoordinate = x;
    this.yCoordinate = y;
    this.isRight = isRight;
  }
}
