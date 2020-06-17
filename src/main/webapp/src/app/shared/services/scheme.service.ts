import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Scheme } from '../model/scheme';

const baseUrl = '/api/dashboardScheme';

@Injectable({
  providedIn: 'root'
})
export class SchemeService {

  constructor(private http: HttpClient, private snackBar: MatSnackBar) { }

  getSchemes() {
    return this.http.get<Scheme[]>(baseUrl);
  }

  getSchemeById(id: string) {
    return this.http.get<Scheme>(baseUrl + '/' + id);
  }

  createScheme(scheme: Scheme) {
    const formData = new FormData();

    if (typeof scheme.image !== null) {
      formData.append('image', scheme.image);
    }
    formData.append('dashboardItems', scheme.dashboardSchemeItems ? new Blob([JSON.stringify(scheme.dashboardSchemeItems)], {
                    type: 'application/json'
                }) : null);
    formData.append('title', scheme.title ? new Blob([JSON.stringify(scheme.title)], {
                    type: 'text/plain '
                }) : null);
    const HttpUploadOptions = {
      headers: new HttpHeaders({ Accept: 'application/json' })
    };
    const url = baseUrl + (scheme.image === null ? '/noImage' : '');
    this.http.post(url, formData, HttpUploadOptions).subscribe(data => {
      // console.log(data);
      this.snackBar.open('Successfully added scheme', 'OK', {
        duration: 2000,
      });
      },
      (err: HttpErrorResponse) => {
        // this.snackBar.open('Error occurred. Details: ' + err.name + ' ' + err.message, 'OK', {
        //   duration: 5000,
        // });
    });
  }

  updateScheme(scheme: Scheme) {
    const formData = new FormData();

    formData.append('id', new Blob([JSON.stringify(scheme.id)], {
                    type: 'text/plain '
                }));
    if (typeof scheme.image !== 'string') {
      formData.append('image', scheme.image);
    }
    formData.append('dashboardItems', new Blob([JSON.stringify(scheme.dashboardSchemeItems)], {
                    type: 'application/json'
                }));
    formData.append('title', new Blob([JSON.stringify(scheme.title)], {
                    type: 'text/plain '
                }));
    const HttpUploadOptions = {
      headers: new HttpHeaders({ Accept: 'application/json'})
    };
    const url = baseUrl + (typeof scheme.image === 'string' || scheme.image ===  null ? '/noImage' : '');
    this.http.put(url, formData).subscribe(data => {
      // console.log(data);
      this.snackBar.open('Successfully edited scheme', 'OK', {
        duration: 2000,
      });
    },
    (err: HttpErrorResponse) => {
      // this.snackBar.open('Error occurred. Details: ' + err.name + ' ' + err.message, 'OK', {
      //   duration: 5000,
      // });
    }
    );
  }

  deleteScheme(id: string) {
    return this.http.delete(baseUrl + '/' + id).subscribe( () => {
      this.snackBar.open('Successfully deleted', 'OK', {
        duration: 2000,
      });
    },
    (err: HttpErrorResponse) => {
      this.snackBar.open('Error occurred. Details: ' + err.name + ' ' + err.message, 'OK', {
        duration: 5000,
      });
    }
    );
  }
}
