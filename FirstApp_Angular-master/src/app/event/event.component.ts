import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Evt } from 'src/Modeles/Evt';
import { ConfirmComponent } from '../confirm/confirm.component';
import { EventService } from 'src/Services/event.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ViewChild } from '@angular/core';
import { EventCreateComponent } from '../event-create/event-create.component';
import { EventDetailsComponent } from '../event-details/event-details.component';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})

export class EventComponent implements AfterViewInit, OnInit {

  displayedColumns: string[] = ['id', 'title', 'datedebut', 'datefin', 'place', 'actions'];
  dataSource: MatTableDataSource<Evt> = new MatTableDataSource();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private eventService: EventService, private dialog: MatDialog) { }


  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  ngOnInit(): void {
    this.fetchAll();
  }

  fetchAll() {
    //appeler le service et attendre la reponse
    this.eventService.GetAllEvents().subscribe((data) => {
      this.dataSource.data = data;
    });
  }

  deleteEvent(id: string) {

    // lancer la boite de dialogue de confirmation
    let x = this.dialog.open(ConfirmComponent)

    // attendre la reponse de la boite de dialogue
    x.afterClosed().subscribe((result) => {
      if (result) {
        // si la reponse est positive, alors supprimer l'evenement
        this.eventService.DeleteEvent(id).subscribe(() => {
          // Remove the deleted event from the dataSource array
          // this.dataSource = this.dataSource.filter(event => event.id !== id);

          // Alternatively, you can refresh the event list by calling GetAllEvents again
          this.eventService.GetAllEvents().subscribe((data) => {
            this.dataSource.data = data;
          });
        });
      }
    });
  }

  create() {
    let dialogC = this.dialog.open(EventCreateComponent);
    // after closing the dialog, post the new event to the server and refresh the event list
    dialogC.afterClosed().subscribe((res) => {
      if (res) {
        // appel du service (fonction POST)
        this.eventService.AddEvent(res).subscribe(() => {

        });
      }
    });
  }

  openEdit(id: string) {
    const DialogConfig = new MatDialogConfig();
    DialogConfig.data = id;
    let dialogC = this.dialog.open(EventCreateComponent, DialogConfig);
    // envoyer des données à la boite de dialogue (l'id de l'evenement à modifier)
    // after closing the dialog, post the updated event to the server and refresh the event list
    dialogC.afterClosed().subscribe((res) => {
      if (res) {
        // appel du service (fonction PUT)
        this.eventService.UpdateEvent(id, res).subscribe(() => {

        });
      }
    });
  }

  openDetails(id: string) {
    const DialogConfig = new MatDialogConfig();
    DialogConfig.data = id;
    this.dialog.open(EventDetailsComponent, DialogConfig);
    
  }

  openParticipants(id: string) {
    // navigate to the participants page of the event with the given id
  }
}