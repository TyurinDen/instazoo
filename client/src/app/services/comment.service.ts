import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const COMMENT_API = 'http://localhost:8080/api/comments/'

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {
  }

  public createCommentForPost(postId: number, comment: string): Observable<any> {
    return this.http.post(COMMENT_API + postId + '/create', {
      content: comment
    })
  }

  public getAllCommentsForPost(postId: number): Observable<any> {
    return this.http.get(COMMENT_API + postId + 'all')
  }

  public deleteComment(commentId: number): Observable<any> {
    return this.http.delete(COMMENT_API + 'delete/' + commentId);
  }

}
