import { Component, Inject, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ConfirmComponent } from 'src/app/confirm/confirm.component';
import { Category } from 'src/models/category';
import { ApiService } from 'src/services/api.service';
import { CategoryFormComponent } from '../categories-form/categories-form.component';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent {

  displayedColumns: string[] = ['id', 'name', 'actions'];
      dataSource: MatTableDataSource<Category> = new MatTableDataSource();
    
      @ViewChild(MatPaginator) paginator!: MatPaginator;
      @ViewChild(MatSort) sort!: MatSort;
    
      constructor(private apiService: ApiService, private dialog: MatDialog) { }
    
    
      ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    
      applyFilter(category: Event) {
        const filterValue = (category.target as HTMLInputElement).value;
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
        this.apiService.getCategories().subscribe((data) => {
          this.dataSource.data = data;
        });
      }
    
      deleteCategory(id: string) {
    
        // lancer la boite de dialogue de confirmation
        let x = this.dialog.open(ConfirmComponent)
    
        // attendre la reponse de la boite de dialogue
        x.afterClosed().subscribe((result) => {
          if (result) {
            // si la reponse est positive, alors supprimer l'evenement
            this.apiService.deleteCategory(id).subscribe(() => {
              // Remove the deleted category from the dataSource array
              // this.dataSource = this.dataSource.filter(user => user.id !== id);
    
              // Alternatively, you can refresh the category list by calling getCategories again
              this.apiService.getCategories().subscribe((data) => {
                this.dataSource.data = data;
              });
            });
          }
        });
      }
    
      create() {
        let dialogC = this.dialog.open(CategoryFormComponent);
        dialogC.afterClosed().subscribe((res) => {
          if (res) {
            this.apiService.addCategory(res).subscribe(() => {
              this.fetchAll();
            });
          }
        });
      }

      openEdit(id: string) {
        const category = this.dataSource.data.find(c => c.id === id);
        const dialogConfig = new MatDialogConfig();
        dialogConfig.data = category;
        let dialogC = this.dialog.open(CategoryFormComponent, dialogConfig);
        dialogC.afterClosed().subscribe((res) => {
          if (res) {
            this.apiService.editCategory(res).subscribe(() => {
              this.fetchAll();
            });
          }
        });
      }

}
