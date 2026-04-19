import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Evt } from 'src/Modeles/Evt';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private httpClient: HttpClient) { }

  GetAllEvents(): Observable<Evt[]> {
    return this.httpClient.get<Evt[]>('http://localhost:3000/Evts');
  }

  GetEventByid(id: string): Observable<Evt> {
    return this.httpClient.get<Evt>(`http://localhost:3000/Evts/${id}`);
  }

  AddEvent(evt: Evt): Observable<void> {
    return this.httpClient.post<void>('http://localhost:3000/Evts', evt);
  }

  UpdateEvent(E: Evt, idcourant: string): Observable<void> {
    return this.httpClient.put<void>(`http://localhost:3000/Evts/${idcourant}`, E);
  }

  UpdateEvent2(type: string, idcourant: string): Observable<void> {
    return this.httpClient.patch<void>(`http://localhost:3000/Evts/${idcourant}`, { type: type });
  }

  DeleteEvent(id: string): Observable<void> {
    return this.httpClient.delete<void>(`http://localhost:3000/Evts/${id}`);
  }

}