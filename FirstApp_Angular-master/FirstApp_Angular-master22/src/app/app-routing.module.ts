import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MemberComponent } from './member/member.component';
import { MemberFormComponent } from './member-form/member-form.component';
import { ToolsComponent } from './tools/tools.component';
import { ArticlesComponent } from './articles/articles.component';
import { EventsComponent } from './events/events.component';
import { TemplateComponent } from './template/template.component';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
// Dashboard
  { path: 'dashboard', component: DashboardComponent },

  // Members
  { path: 'member', component: MemberComponent },
  { path: 'member/create', component: MemberFormComponent },
  { path: 'member/:id/edit', component: MemberFormComponent },

  // Tools
  { path: 'tools', component: ToolsComponent },

  // Articles
  { path: 'articles', component: ArticlesComponent },

  // Events
  { path: 'events', component: EventsComponent },

  // Template layout (si besoin de route principale)
  { path: 'template', component: TemplateComponent },

  // Route par défaut
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  // Wildcard / 404
  { path: '**', component: DashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
