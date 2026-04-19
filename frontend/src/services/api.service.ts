import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user';
import { Project } from '../models/project';
import { Affectation } from '../models/affectation';
import { Category } from '../models/category';

@Injectable({ providedIn: 'root' })
export class ApiService {

  private API = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // USERS
  getUsers() {
    return this.http.get<User[]>(`${this.API}/users`);
  }

  addUser(u: User) {
    return this.http.post<User>(`${this.API}/users`, u);
  }

  editUser(u: User) {
    return this.http.put<User>(`${this.API}/users/${u.id}`, u);
  }

  deleteUser(id: string) {
    return this.http.delete(`${this.API}/users/${id}`);
  }

  // PROJECTS
  getProjects() {
    return this.http.get<Project[]>(`${this.API}/projects`);
  }

  addProject(p: Project) {
    return this.http.post<Project>(`${this.API}/projects`, p);
  }

  editProject(p: Project) {
    return this.http.put<Project>(`${this.API}/projects/${p.id}`, p);
  }

  deleteProject(id: string) {
    return this.http.delete(`${this.API}/projects/${id}`);
  }

  // AFFECTATIONS
  getAffectations() {
    return this.http.get<Affectation[]>(`${this.API}/affectations`);
  }

  addAffectation(a: Affectation) {
    return this.http.post<Affectation>(`${this.API}/affectations`, a);
  }

  editAffectation(a: Affectation) {
    return this.http.put<Affectation>(`${this.API}/affectations/${a.id}`, a);
  }

  deleteAffectation(id: string) {
    return this.http.delete(`${this.API}/affectations/${id}`);
  }

  // CATEGORIES
  getCategories() {
    return this.http.get<Category[]>(`${this.API}/categories`);
  }

  addCategory(c: Category) {
    return this.http.post<Category>(`${this.API}/categories`, c);
  }

  editCategory(category: Category) {
    return this.http.put<Category>(`${this.API}/categories/${category.id}`, category);
  }

  deleteCategory(id: string) {
    return this.http.delete(`${this.API}/categories/${id}`);
  }
}