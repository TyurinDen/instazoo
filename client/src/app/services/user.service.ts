import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/User";

const USER_API = 'http://localhost:8080/api/users'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public getUserById(id: number): Observable<any>{
    return this.http.get(USER_API + id)
  }

  public getCurrentUser(): Observable<any> {
    return this.http.get(USER_API + '/current')
  }

  public updateUser(user: User): Observable<any> {
    return this.http.post(USER_API + '/update', user)
  }

}
