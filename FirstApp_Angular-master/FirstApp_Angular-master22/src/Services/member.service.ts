import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core'; // This import statement is used to import the Injectable decorator from the @angular/core package. The Injectable decorator is used to mark a class as available to be provided and injected as a dependency in Angular's dependency injection system.
import { Observable } from 'rxjs';
import { Member } from 'src/Modeles/Member';

@Injectable({
  providedIn: 'root'
})
export class MemberService {
  // GetMemberByID(idcourant: string) {
  //   throw new Error('Method not implemented.');
  // }

  constructor(private httpClient:HttpClient) { }

  GetAllMembers():Observable<Member[]>{

    return this.httpClient.get<Member[]>('http://localhost:3000/members');
  }


 GetMemberByid(id: string): Observable<Member> {
  return this.httpClient.get<Member>(`http://localhost:3000/members/${id}`);
}

  AddMember(member: Member): Observable<void> {
    return this.httpClient.post<void>('http://localhost:3000/members', member);
  }

UpdateMember(M: Member, idcourant: string): Observable<void> {
  return this.httpClient.put<void>(`http://localhost:3000/members/${idcourant}`, M);
}

UpdateMember2(type:string, idcourant: string): Observable<void> {
  return this.httpClient.patch<void>(`http://localhost:3000/members/${idcourant}`, {type:type,name:'m'});
}

  DeleteMember(id: string): Observable<void> {
    return this.httpClient.delete<void>(`http://localhost:3000/members/${id}`);
  }
}
