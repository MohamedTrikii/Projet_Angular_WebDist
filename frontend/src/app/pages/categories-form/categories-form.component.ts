import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-categories-form',
  templateUrl: './categories-form.component.html',
  styleUrls: ['./categories-form.component.css']
})
export class CategoryFormComponent {
  categoryForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<CategoryFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.initializeForm(data);
  }

  private initializeForm(data?: any) {
    this.categoryForm = this.fb.group({
      name: [data?.name || '', Validators.required],
    });
  }

  onSubmit() {
    if (this.categoryForm.valid) {
      const result = this.data
        ? { id: this.data.id, ...this.categoryForm.value }
        : this.categoryForm.value;
      this.dialogRef.close(result);
    }
  }
}
