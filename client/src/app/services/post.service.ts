import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Post} from "../models/Post";
import {Observable} from "rxjs";

const POST_API = 'http://localhost:8080/api/posts/'

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) {
  }

  public createPost(post: Post): Observable<any> {
    return this.http.post(POST_API + 'create', post)
  }

  public getAllPosts(): Observable<any> {
    return this.http.get(POST_API + 'all')
  }

  public getAllPostsForUser(): Observable<any> {
    return this.http.get(POST_API + 'user')
  }

  public deletePost(id: number): Observable<any> {
    return this.http.delete(POST_API + id);
  }

  public likePost(id: number, usernane: string): Observable<any> {
    return this.http.post(POST_API + id + '/' + usernane + 'like', null)
  }

}
