import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
constructor(private router: Router) {}
islogin:boolean=false;
ngOnInit() {
 this.router.events.subscribe(() => {this.islogin =this.router.url.includes('/login');
 console.log("is login: ", this.islogin)
});
}}
