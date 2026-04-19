import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent 
{
  email: string = "";
  password: string = "";

  constructor(private authService: AuthService, private router: Router) {}

  Login() {
    console.log("Login function called");
    console.log("Email: " + this.email, "Password: " + this.password);
    //appel de service => ENVOYER Jwt 
    this.authService.signInWithEmailAndPassword(this.email, this.password)
      .then(() => {this.router.navigate(['/member'])})
    

    }

}
