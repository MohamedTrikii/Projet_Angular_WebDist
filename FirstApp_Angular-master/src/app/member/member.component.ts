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

  //injec de dependances
  constructor(private memberService: MemberService, private dialog: MatDialog) { }
  //declarer le tableau
  dataSource: Member[] = [];
  displayedColumns: string[] = ['id', 'cin', 'name', 'type', 'createdDate', 'actions'];
  ngOnInit(): void {
    //appeler le service et attendre la reponse
    this.memberService.GetAllMembers().subscribe((data) => {
      this.dataSource = data;
    });
  }

  deleteMember(id: string) {

    // lancer la boite de dialogue de confirmation
    let x = this.dialog.open(ConfirmComponent)

    // attendre la reponse de la boite de dialogue
    x.afterClosed().subscribe((result) => {
      if (result) {
        // si la reponse est positive, alors supprimer le membre
        this.memberService.DeleteMember(id).subscribe(() => {
          // Remove the deleted member from the dataSource array
          // this.dataSource = this.dataSource.filter(member => member.id !== id);

          // Alternatively, you can refresh the member list by calling GetAllMembers again
          this.memberService.GetAllMembers().subscribe((data) => {
            this.dataSource = data;
          });
        });
      }
    });
  }
}