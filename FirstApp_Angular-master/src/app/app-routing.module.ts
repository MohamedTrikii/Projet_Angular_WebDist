import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MemberComponent } from './member/member.component';
import { MemberFormComponent } from './member-form/member-form.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ToolComponent } from './tool/tool.component';
import { ArticleComponent } from './article/article.component';
import { EventComponent } from './event/event.component';
import { EventCreateComponent } from './event-create/event-create.component';
import { LoginComponent } from './login/login.component';
import { authGuard } from 'src/app/AuthGuard';

const routes: Routes = [
  {
    path: 'create', component: MemberFormComponent,
    canActivate: [authGuard],
  },
  {
    path: '', component: LoginComponent
  },
  {
    path: ':id/edit', component: MemberFormComponent,
    canActivate: [authGuard],
  },
  {
    path: 'member', component: MemberComponent,
    canActivate: [authGuard],
  },
  {
    path: 'dashboard', component: DashboardComponent,
    canActivate: [authGuard],
  },
  {
    path: 'tools', component: ToolComponent,
    canActivate: [authGuard],
  },
  {
    path: 'articles', component: ArticleComponent,
    canActivate: [authGuard],
  },
  {
    path: 'events', component: EventComponent,
    canActivate: [authGuard],
  },
  {
    path: 'events/create', component: EventCreateComponent,
    canActivate: [authGuard],
  },
  {
    path: 'events/:id/edit', component: EventCreateComponent,
    canActivate: [authGuard],
  },
  {
    path: '**', component: LoginComponent 
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
