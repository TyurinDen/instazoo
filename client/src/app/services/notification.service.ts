import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackBar: MatSnackBar) { }

  public shorSnackBar(message: string): void {
    this.snackBar.open(message, undefined, {
      duration: 2000
    })
  }

}
