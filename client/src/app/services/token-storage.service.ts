import {Injectable} from '@angular/core';
import {User} from "../models/User";

const TOKEN_KEY = 'auth-token'
const USER_KEY = 'auth-user'

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() {
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY)
    window.sessionStorage.setItem(TOKEN_KEY, token)
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY)
  }

  public saveUser(user: User): void {
    window.sessionStorage.removeItem(USER_KEY)
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user))
  }

  public getUser(): User | null {
    let user = window.sessionStorage.getItem(USER_KEY)
    if (user != null) {
      return JSON.parse(user)
    }
    return null
  }

  public logout(): void {
    window.sessionStorage.clear()
    window.location.reload()
  }

}
