import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsersComponent } from './pages/users/users.component';
import { AffectationsComponent } from './pages/affectations/affectations.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { LoginComponent } from './auth/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { DashboardLayoutComponent } from './layout/dashboard-layout/dashboard-layout.component';
import { AuthGuard } from './auth/auth.guard';
import { RoleGuard } from './auth/role.guard';
import { CategoriesComponent } from './pages/categories/categories.component';

export const routes:Routes=[
 {path:'login',component:LoginComponent},
 {path:'',component:DashboardLayoutComponent,canActivate:[AuthGuard],children:[
   {path:'dashboard',component:DashboardComponent,canActivate:[RoleGuard],data:{role:'ADMIN'}},
   {path:'users',component:UsersComponent,canActivate:[RoleGuard],data:{role:'ADMIN'}},
   {path:'projects',component:ProjectsComponent,canActivate:[RoleGuard],data:{role:'ADMIN'}},
   {path:'affectations',component:AffectationsComponent,canActivate:[RoleGuard],data:{role:'ADMIN'}},
   {path:'categories',component:CategoriesComponent,canActivate:[RoleGuard],data:{role:'ADMIN'}},
   {path:'',redirectTo:'dashboard',pathMatch:'full'}
 ]},
 {path:'**',redirectTo:'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
