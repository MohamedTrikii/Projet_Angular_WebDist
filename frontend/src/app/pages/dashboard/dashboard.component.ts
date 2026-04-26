import { Component, OnInit } from '@angular/core';
import { Category } from 'src/models/category';
import { User } from 'src/models/user';
import { Project } from 'src/models/project';
import { Affectation } from 'src/models/affectation';
import { ApiService } from 'src/services/api.service';
import { ChartOptions, ChartDataset } from 'chart.js';

/**
 * Composant Dashboard - affiche les statistiques globales de l'application.
 * Charge les données depuis l'API et affiche les compteurs.
 */
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private apiService: ApiService) {}

  // Données chargées depuis l'API
  categories: Category[] = [];
  users: User[] = [];
  projects: Project[] = [];
  affectations: Affectation[] = [];

  // Compteurs affichés sur le dashboard
  employeeCount = 0;
  categoryCount = 0;
  projectCount = 0;
  affectationCount = 0;

  // Charts data (exemples, à adapter selon les besoins)
  chartOptions: ChartOptions = {};
  chartDataDoughnut: ChartDataset[] = [
    {
      label: 'Répartition des employés par catégorie',
      data: []
    }
  ]
  chartLabelsDoughnut: string[] = [];

  chartDataPie: ChartDataset[] = [
    {
      data: []
    }
  ]
  chartLabelsPie: string[] = [];

  ngOnInit(): void {
    this.loadDashboardData();
  }

  /** Charger toutes les données du dashboard */
  loadDashboardData(): void {
    // Charger les utilisateurs (employés)
    this.apiService.getUsers().subscribe(users => {
      this.users = users;
      this.employeeCount = users.length;
      const categories = [...new Set(users.map(u => u.category))];
      this.chartLabelsDoughnut = categories;
      this.chartDataDoughnut = [{
        label: 'Répartition des employés par catégorie',
        data: categories.map(c => users.filter(u => u.category === c).length)
      }];
    });

    // Charger les catégories
    this.apiService.getCategories().subscribe(categories => {
      this.categories = categories;
      this.categoryCount = categories.length;
    });

    // Charger les projets
    this.apiService.getProjects().subscribe(projects => {
      this.projects = projects;
      this.projectCount = projects.length;
      const projectStatuses = [...new Set(projects.map(p => p.status))];
      this.chartLabelsPie = projectStatuses;
      this.chartDataPie = [{
        data: projectStatuses.map(status => projects.filter(p => p.status === status).length)
      }];
    });

    // Charger les affectations
    this.apiService.getAffectations().subscribe(affectations => {
      this.affectations = affectations;
      this.affectationCount = affectations.length;
    });
  }
}
