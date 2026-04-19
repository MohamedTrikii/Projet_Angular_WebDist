import { Component, OnInit } from '@angular/core';
import { Member } from 'src/Modeles/Member';
import { MemberService } from '../../Services/member.service';
import { ConfirmComponent } from '../confirm/confirm.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-member',
  templateUrl: './member.component.html',
  styleUrls: ['./member.component.css']
})
export class MemberComponent implements OnInit {

  // injection de dépendances
  constructor(private memberService: MemberService, private dialog: MatDialog) { }

  // déclarer le tableau
  dataSource: Member[] = [];
  displayedColumns: string[] = ['id', 'cin', 'name', 'type', 'createdDate', 'actions'];

  ngOnInit(): void {
    // appeler le service et attendre la réponse
    this.memberService.GetAllMembers().subscribe((data) => {  
      this.dataSource = data;
    });
  }

  deleteMember(id: string) {
    // lancer la boîte
    let x = this.dialog.open(ConfirmComponent);

    // attendre le click de l'utilisateur
    x.afterClosed().subscribe((res) => {
      if (res) {
        // si click = confirm
        this.memberService.DeleteMember(id).subscribe(() => {
          this.memberService.GetAllMembers().subscribe((res) => {
            this.dataSource = res;
          });
        });
      }
    });
  }
}