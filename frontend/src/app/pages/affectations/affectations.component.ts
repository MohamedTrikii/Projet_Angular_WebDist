import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Affectation } from '../../../models/affectation';
import { ApiService } from 'src/services/api.service';
import { forkJoin } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ConfirmComponent } from 'src/app/confirm/confirm.component';
import { AssignmentFormComponent } from '../affectation-form/affectation-form.component';

@Component({
  selector: 'app-affectations',
  templateUrl: './affectations.component.html',
})
export class AffectationsComponent implements OnInit {

  displayedColumns: string[] = ['id', 'userId', 'projectId', 'start', 'end', 'actions'];

      dataSource: MatTableDataSource<Affectation> = new MatTableDataSource();

      userNameById: Record<string, string> = {};
      projectNameById: Record<string, string> = {};
    
      @ViewChild(MatPaginator) paginator!: MatPaginator;
      @ViewChild(MatSort) sort!: MatSort;
    
      constructor(private apiService: ApiService, private dialog: MatDialog) { }
    
    
      ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    
      applyFilter(assignment: Event) {
        const filterValue = (assignment.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();
    
        if (this.dataSource.paginator) {
          this.dataSource.paginator.firstPage();
        }
      }
    
      ngOnInit(): void {
        this.loadLookups();
      }

      loadLookups() {
        forkJoin({
          users: this.apiService.getUsers(),
          projects: this.apiService.getProjects(),
        }).subscribe(({ users, projects }) => {
          this.userNameById = users.reduce((acc, user) => {
            if (user.id) {
              acc[user.id] = user.name;
            }
            return acc;
          }, {} as Record<string, string>);

          this.projectNameById = projects.reduce((acc, project) => {
            if (project.id) {
              acc[project.id] = project.name;
            }
            return acc;
          }, {} as Record<string, string>);

          this.fetchAll();
        });
      }
    
      fetchAll() {
        //appeler le service et attendre la reponse
        this.apiService.getAffectations().subscribe((data) => {
          this.dataSource.data = data;
        });
      }

      getUserName(userId: string) {
        return this.userNameById[userId] ?? userId;
      }

      getProjectName(projectId: string) {
        return this.projectNameById[projectId] ?? projectId;
      }
    
      deleteAffectation(id: string) {
    
        // lancer la boite de dialogue de confirmation
        let x = this.dialog.open(ConfirmComponent)
    
        // attendre la reponse de la boite de dialogue
        x.afterClosed().subscribe((result) => {
          if (result) {
            // si la reponse est positive, alors supprimer l'evenement
            this.apiService.deleteAffectation(id).subscribe(() => {
              // Remove the deleted project from the dataSource array
              // this.dataSource = this.dataSource.filter(user => user.id !== id);
    
              // Alternatively, you can refresh the project list by calling getProjects again
              this.apiService.getAffectations().subscribe((data) => {
                this.dataSource.data = data;
              });
            });
          }
        });
      }
    
      create() {
        let dialogC = this.dialog.open(AssignmentFormComponent);
        dialogC.afterClosed().subscribe((res) => {
          if (res) {
            this.apiService.addAffectation(res).subscribe(() => {
              this.fetchAll();
            });
          }
        });
      }

      openEdit(id: string) {
        const affectation = this.dataSource.data.find(a => a.id === id);
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = affectation;
        let dialogC = this.dialog.open(AssignmentFormComponent, dialogConfig);
        dialogC.afterClosed().subscribe((res) => {
          if (res) {
            this.apiService.editAffectation(res).subscribe(() => {
              this.fetchAll();
            });
          }
        });
      }
}
