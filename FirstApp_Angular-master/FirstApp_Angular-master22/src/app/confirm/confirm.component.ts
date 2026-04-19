import { Component, OnInit } from '@angular/core';
import { MemberService } from '../../Services/member.service';

import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css']
})
export class ConfirmComponent {

  // Boîte de dialogue
  constructor(public dialogRef: MatDialogRef<ConfirmComponent>) { }

}
