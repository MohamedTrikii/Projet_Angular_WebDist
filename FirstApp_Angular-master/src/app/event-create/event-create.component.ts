import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EventService } from 'src/Services/event.service';


@Component({
  selector: 'app-event-create',
  templateUrl: './event-create.component.html',
  styleUrls: ['./event-create.component.css']
})
export class EventCreateComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<EventCreateComponent>, private ES: EventService,
    @Inject (MAT_DIALOG_DATA) data:any){
      if(data) {
        console.log("data", data);
        this.ES.GetEventById(data).subscribe((res) => {
          this.form = new FormGroup({
            id: new FormControl(res.id),
            title: new FormControl(res.title, [Validators.required]),
            datedebut: new FormControl(res.datedebut),
            datefin: new FormControl(res.datefin),
            place: new FormControl(res.place),
          });
        });
      }
      else {
        this.ngOnInit();
      }
    }

form!: FormGroup;

ngOnInit() {


  // initialize the form with form controls
  this.form = new FormGroup({
    title: new FormControl(null, [Validators.required]),
    datedebut: new FormControl(null),
    datefin: new FormControl(null),
    place: new FormControl(null),
  });
}

save() {
    this.dialogRef.close(this.form.value);
  };


close() {
  this.dialogRef.close();
}
}
