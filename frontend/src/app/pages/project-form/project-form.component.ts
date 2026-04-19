import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-project-form',
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.css']
})
export class ProjectFormComponent {
  projectForm!: FormGroup;

  statusNames = ['En cours', 'Planifié', 'Terminé'];

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<ProjectFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.initializeForm(data);
  }

  private initializeForm(data?: any) {
    this.projectForm = this.fb.group({
      name: [data?.name || '', Validators.required],
      description: [data?.description || '', Validators.required],
      status: [data?.status || '', Validators.required],
    });
  }

  onSubmit() {
    if (this.projectForm.valid) {
      const result = this.data
        ? { id: this.data.id, ...this.projectForm.value }
        : this.projectForm.value;
      this.dialogRef.close(result);
    }
  }
}