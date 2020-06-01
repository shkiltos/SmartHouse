import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-camera',
  templateUrl: './camera.component.html',
  styleUrls: ['./camera.component.scss']
})
export class CameraComponent implements OnInit {
  htmlSnippet: SafeHtml;
  constructor(public userService: UserService, public sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.userService.fetchUserObservable().subscribe( () => {
      let html = '';
      this.userService.user.cams.forEach(cam => {
        html += '<iframe class="cam" src="' + cam.value + '" width="535px" height="290px" style="border:0px; overflow:hidden; padding-bottom: 10px; padding-right: 15px" scrolling="no"></iframe>';
      });
      this.htmlSnippet = this.sanitizer.bypassSecurityTrustHtml(html);
    });

  }

}
