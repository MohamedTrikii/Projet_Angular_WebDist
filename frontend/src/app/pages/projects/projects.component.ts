import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { Project } from '../../../models/project';
import { ApiService } from 'src/services/api.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ConfirmComponent } from 'src/app/confirm/confirm.component';
import { ProjectFormComponent } from '../project-form/project-form.component';

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
})
export class ProjectsComponent implements OnInit {

  displayedColumns: string[] = ['id', 'name', 'description', 'status', 'actions'];
    dataSource: MatTableDataSource<Project> = new MatTableDataSource();
  
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;
  
    constructor(private apiService: ApiService, private dialog: MatDialog) { }
  
  
    ngAfterViewInit() {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }
  
    applyFilter(project: Event) {
      const filterValue = (project.target as HTMLInputElement).value;
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
      this.apiService.getProjects().subscribe((data) => {
        this.dataSource.data = data;
      });
    }
  
    deleteProject(id: string) {
  
      // lancer la boite de dialogue de confirmation
      let x = this.dialog.open(ConfirmComponent)
  
      // attendre la reponse de la boite de dialogue
      x.afterClosed().subscribe((result) => {
        if (result) {
          // si la reponse est positive, alors supprimer l'evenement
          this.apiService.deleteProject(id).subscribe(() => {
            // Remove the deleted project from the dataSource array
            // this.dataSource = this.dataSource.filter(user => user.id !== id);
  
            // Alternatively, you can refresh the project list by calling getProjects again
            this.apiService.getProjects().subscribe((data) => {
              this.dataSource.data = data;
            });
          });
        }
      });
    }
  
    create() {
      let dialogC = this.dialog.open(ProjectFormComponent);
      dialogC.afterClosed().subscribe((res) => {
        if (res) {
          this.apiService.addProject(res).subscribe(() => {
            this.fetchAll();
          });
        }
      });
    }

    openEdit(id: string) {
      const project = this.dataSource.data.find(p => p.id === id);
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = project;
      let dialogC = this.dialog.open(ProjectFormComponent, dialogConfig);
      dialogC.afterClosed().subscribe((res) => {
        if (res) {
          this.apiService.editProject(res).subscribe(() => {
            this.fetchAll();
          });
        }
      });
    }
}