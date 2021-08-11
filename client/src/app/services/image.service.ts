import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const IMAGE_API = 'http://localhost:8080/api/images/'

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) {
  }

  public uploadProfileImage(image: File): Observable<any> {
    const uploadData = new FormData()
    uploadData.append('file', image)

    return this.http.post(IMAGE_API + 'upload-profile-image', uploadData)
  }

  public uploadPostImage(image: File): Observable<any> {
    const uploadData = new FormData()
    uploadData.append('file', image)

    return this.http.post(IMAGE_API + 'upload-post-image', uploadData)
  }

  public getProfileImage(): Observable<any> {
    return this.http.get(IMAGE_API + 'profile-image')
  }

  public getPostImage(postId: number): Observable<any> {
    return this.http.get(IMAGE_API + postId + 'post-image')
  }

}
