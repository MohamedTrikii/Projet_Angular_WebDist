import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig} from '@angular/material/dialog';
import { MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../models/user';
import { ApiService } from 'src/services/api.service';
import { UserFormComponent } from '../user-form/user-form.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ConfirmComponent } from 'src/app/confirm/confirm.component';


@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  displayedColumns: string[] = ['id', 'name', 'email', 'role', 'category', 'actions'];
  dataSource: MatTableDataSource<User> = new MatTableDataSource();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private apiService: ApiService, private dialog: MatDialog) { }


  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(user: Event) {
    const filterValue = (user.target as HTMLInputElement).value;
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
    this.apiService.getUsers().subscribe((data) => {
      this.dataSource.data = data;
    });
  }

  deleteUser(id: string) {

    // lancer la boite de dialogue de confirmation
    let x = this.dialog.open(ConfirmComponent)

    // attendre la reponse de la boite de dialogue
    x.afterClosed().subscribe((result) => {
      if (result) {
        // si la reponse est positive, alors supprimer l'evenement
        this.apiService.deleteUser(id).subscribe(() => {
          // Remove the deleted user from the dataSource array
          // this.dataSource = this.dataSource.filter(user => user.id !== id);

          // Alternatively, you can refresh the user list by calling getUsers again
          this.apiService.getUsers().subscribe((data) => {
            this.dataSource.data = data;
          });
        });
      }
    });
  }

  create() {
    let dialogC = this.dialog.open(UserFormComponent);
    dialogC.afterClosed().subscribe((res) => {
      if (res) {
        this.apiService.addUser(res).subscribe(() => {
          this.fetchAll();
        });
      }
    });
  }

  openEdit(id: string) {
    const user = this.dataSource.data.find(u => u.id === id);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = user;
    let dialogC = this.dialog.open(UserFormComponent, dialogConfig);
    dialogC.afterClosed().subscribe((res) => {
      if (res) {
        this.apiService.editUser(res).subscribe(() => {
          this.fetchAll();
        });
      }
    });
  }
}