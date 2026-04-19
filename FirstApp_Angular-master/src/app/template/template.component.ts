import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.css']
})
export class TemplateComponent {

  constructor(private authService: AuthService, private router: Router) {}
  logout() {
    this.authService.signOut()
      .then(() => {this.router.navigate(['/login'])})

}}
