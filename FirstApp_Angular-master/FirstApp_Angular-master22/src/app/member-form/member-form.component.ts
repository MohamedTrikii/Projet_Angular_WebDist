import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MemberService } from '../../Services/member.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-member-form',
  templateUrl: './member-form.component.html',
  styleUrls: ['./member-form.component.css']
})
export class MemberFormComponent implements OnInit {
  
  form!: FormGroup;
  idcourant!: string;

  constructor(
    private MS: MemberService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  // initialize the form with form controls
  ngOnInit() {

    /// recuperer la route active 
    this.idcourant = this.activatedRoute.snapshot.params['id'];

    // chercher id 
    if (this.idcourant) {

      // if id existe => je suis dans edit 
      this.MS.GetMemberByid(this.idcourant).subscribe((M) => {
        this.form = new FormGroup({
          cin: new FormControl(M.cin, [Validators.required]),
          name: new FormControl(M.name, [Validators.required]),
          type: new FormControl(M.type, [Validators.required]),
          createdDate: new FormControl(M.createdDate, [Validators.required]),

        });
      });

    } else {

      // sinon => je suis dans create 
      this.form = new FormGroup({
        cin: new FormControl(null, [Validators.required]),
        name: new FormControl(null),
        type: new FormControl(null),
        createdDate: new FormControl(null),
      });

    }
  }

  sub() {
    if(this.idcourant){
      this.MS.UpdateMember(this.form.value,this.idcourant).subscribe(
        () => {
      this.router.navigate(['']);
    } )}
    else {
    this.MS.AddMember(this.form.value).subscribe(() => {
      this.router.navigate(['']);
    });
  }}
}