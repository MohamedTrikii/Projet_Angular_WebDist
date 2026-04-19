import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core'; // This import statement is used to import the Injectable decorator from the @angular/core package. The Injectable decorator is used to mark a class as available to be provided and injected as a dependency in Angular's dependency injection system.
import { Observable } from 'rxjs';
import { Member } from 'src/Modeles/Member';

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  constructor(private httpClient:HttpClient) { }

  GetAllMembers():Observable<Member[]>{
    // generer une requte http
    // en mode GET
    return this.httpClient.get<Member[]>('http://localhost:3000/members');
  }

  GetMemberById(id: string):Observable<Member>{
    return this.httpClient.get<Member>(`http://localhost:3000/members/${id}`);
  }

  AddMember(member: Member): Observable<void> {
    return this.httpClient.post<void>('http://localhost:3000/members', member);
  }

  UpdateMember(id: string, member: Member): Observable<void> {
    return this.httpClient.put<void>(`http://localhost:3000/members/${id}`, member);
  }

  // This method uses HTTP PATCH to update only the fields that are provided in the member object, 
  // rather than replacing the entire member resource.
  UpdateMember2(id: string, type: String): Observable<void> {
    return this.httpClient.patch<void>(`http://localhost:3000/members/${id}`, {Type: type, name: "modified"});
  }

  DeleteMember(id: string): Observable<void> {
    return this.httpClient.delete<void>(`http://localhost:3000/members/${id}`);
  }
}
