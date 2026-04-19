import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Category } from 'src/models/category';
import { ApiService } from 'src/services/api.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

  userForm!: FormGroup;

  roles = ['ADMIN', 'USER'];

  categories: Category[] = [];
  categoryNames: string[] = [];

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<UserFormComponent>,
    private apiService: ApiService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.initializeForm(data);
  }

  ngOnInit(): void {
    this.apiService.getCategories().subscribe(categories => {
      this.categories = categories;
      this.categoryNames = categories.map(c => c.name);
    });
  }

  private initializeForm(data?: any) {
    this.userForm = this.fb.group({
      name: [data?.name || '', Validators.required],
      email: [data?.email || '', [Validators.required, Validators.email]],
      password: [data?.password || '', data ? [] : [Validators.required]],
      category: [data?.category || '', Validators.required],
      role: [data?.role || 'USER', Validators.required]
    });
  }

  onSubmit() {
    if (this.userForm.valid) {
      const result = this.data
        ? { id: this.data.id, ...this.userForm.value }
        : this.userForm.value;
      this.dialogRef.close(result);
    }
  }
}