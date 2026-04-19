import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Evt } from 'src/Modeles/Evt';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private httpClient: HttpClient) { }

  GetAllEvents() {
    return this.httpClient.get<Evt[]>('http://localhost:3000/events');
  }

  GetEventById(id: string) {
    return this.httpClient.get<Evt>(`http://localhost:3000/events/${id}`);
  }

  AddEvent(event: Evt) {
    return this.httpClient.post<void>('http://localhost:3000/events', event);
  }

  UpdateEvent(id: string, event: Evt) {
    return this.httpClient.put<void>(`http://localhost:3000/events/${id}`, event);
  }

  DeleteEvent(id: string) {
    return this.httpClient.delete<void>(`http://localhost:3000/events/${id}`);
  }
}
