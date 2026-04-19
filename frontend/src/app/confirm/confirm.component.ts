import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css'],
})
export class ConfirmComponent {

  // injec de dependances 
  // forçage le composant à être utilisé uniquement comme une boîte de dialogue
  constructor(public dialogRef: MatDialogRef<ConfirmComponent>) { }

}
