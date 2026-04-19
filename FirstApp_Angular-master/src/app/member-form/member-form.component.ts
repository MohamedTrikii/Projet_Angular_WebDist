import { Component, OnInit } from '@angular/core';
import { Form, FormControl, FormGroup, Validators } from '@angular/forms';
import { MemberService } from '../../Services/member.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-member-form',
  templateUrl: './member-form.component.html',
  styleUrls: ['./member-form.component.css']
})
export class MemberFormComponent implements OnInit {

  constructor(private MS: MemberService, private router: Router, private activatedRoute: ActivatedRoute) { }

  form!: FormGroup;

  idcourant!: string;

  // initialize the form with form controls
  ngOnInit() {

    // recuperer les données du membre à modifier en utilisant l'ID de la route
    this.idcourant = this.activatedRoute.snapshot.params['id']; // assuming the route is something like /editMember/:id

    if (this.idcourant) {
      // If there is an ID in the route, fetch the member data and populate the form
      this.MS.GetMemberById(this.idcourant).subscribe((member) => {
        this.form = new FormGroup({
          cin: new FormControl(member.cin, [Validators.required]),
          name: new FormControl(member.name),
          type: new FormControl(member.type),
          createdDate: new FormControl(member.createdDate),
        });
      });
      return; // exit the function to avoid initializing an empty form
    } else {
      // If there is no ID, initialize an empty form for adding a new member
      this.form = new FormGroup({
        cin: new FormControl(null, [Validators.required]),
        name: new FormControl(null),
        type: new FormControl(null),
        createdDate: new FormControl(null),
      });
    }
  }

  sub() {
    // log the form value to the console
    // console.log(this.form.value);

    if (this.idcourant) {
      // If there is an ID, call the UpdateMember method of the MemberService
      this.MS.UpdateMember(this.idcourant, this.form.value).subscribe(() => {
        // redirect to the member list page after successfully updating a member
        this.router.navigate(['']);
      });
      return; // exit the function to avoid calling AddMember
    } else {
      // call the AddMember method of the MemberService and subscribe to the response
      this.MS.AddMember(this.form.value).subscribe(() => {
        // redirect to the member list page after successfully adding a member
        this.router.navigate(['']);
      });
    }
  }

}
