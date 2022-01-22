import { NursingNotes } from './nursingNotes';

export class NursingNotesModel {

pastNotesList : Array<NursingNotes>;
currentNotes : NursingNotes;

  constructor() {
    this.currentNotes = new NursingNotes();
    this.pastNotesList = new Array<NursingNotes>();
  }

}