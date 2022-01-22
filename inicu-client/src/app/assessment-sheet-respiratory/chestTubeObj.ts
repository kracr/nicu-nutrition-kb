import { Tube } from './tube';
export class ChestTubeObj {
  emptyTube : Tube;
  leftTubes : Array<Tube>;
  rightTubes : Array<Tube>;
  rightObjectToShow : null
  leftObjectToShow : null
  pastLeftTubes : Array<Tube>;
  pastRightTubes : Array<Tube>;
  isPastTubeRight : boolean;
  isPastTubeLeft : boolean;
}
