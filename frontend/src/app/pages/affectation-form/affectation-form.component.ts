import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { User } from 'src/models/user';
import { Project } from 'src/models/project';
import { ApiService } from 'src/services/api.service';

/**
 * Composant formulaire pour créer/modifier une affectation (employé <-> projet).
 * Charge la liste des utilisateurs et projets pour les selects.
 */
@Component({
  selector: 'app-affectation-form',
  templateUrl: './affectation-form.component.html',
  styleUrls: ['./affectation-form.component.css']
})
export class AssignmentFormComponent implements OnInit {
  assignmentForm: FormGroup;

  // Listes pour les selects du formulaire
  users: User[] = [];
  projects: Project[] = [];

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<AssignmentFormComponent>,
    private apiService: ApiService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    // Initialiser le formulaire avec les données existantes (si modification)
    this.assignmentForm = this.fb.group({
      userId: [data?.userId || '', Validators.required],
      projectId: [data?.projectId || '', Validators.required],
      start: [data?.start || '', Validators.required],
      end: [data?.end || '', Validators.required]
    });
  }

  ngOnInit(): void {
    // Charger les utilisateurs et projets pour les menus déroulants
    this.apiService.getUsers().subscribe(users => this.users = users);
    this.apiService.getProjects().subscribe(projects => this.projects = projects);
  }

  /** Soumettre le formulaire et fermer le dialog */
  onSubmit() {
    if (this.assignmentForm.valid) {
      const result = this.data
        ? { id: this.data.id, ...this.assignmentForm.value }
        : this.assignmentForm.value;
      this.dialogRef.close(result);
    }
  }
}