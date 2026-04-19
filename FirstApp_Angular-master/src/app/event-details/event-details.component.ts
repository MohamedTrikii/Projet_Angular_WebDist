import { Component, Inject, inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EventService } from 'src/Services/event.service';

@Component({
  selector: 'app-event-details',
  templateUrl: './event-details.component.html',
  styleUrls: ['./event-details.component.css']
})
export class EventDetailsComponent {

  constructor(private dialogRef: MatDialogRef<EventDetailsComponent>, private ES: EventService,
    @Inject (MAT_DIALOG_DATA) data:any) {
      if(data) {
        console.log("data", data);
        this.ES.GetEventById(data).subscribe((res) => {
          this.event = new FormGroup({
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

    event!: FormGroup;

    ngOnInit() {
      // initialize the form with form controls
      this.event = new FormGroup({
        title: new FormControl(null, [Validators.required]),
        datedebut: new FormControl(null),
        datefin: new FormControl(null),
        place: new FormControl(null),
      });
    }

    close() {
      this.dialogRef.close();
    }
}
