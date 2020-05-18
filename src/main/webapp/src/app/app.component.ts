import { Component } from '@angular/core';
import { ThemeService } from './shared/services/theme.service';
import { OverlayContainer } from '@angular/cdk/overlay';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  overlay: any;
  constructor(public overlayContainer: OverlayContainer, private themeService: ThemeService) {
    overlayContainer.getContainerElement().classList.add('alternative');
    this.overlay = overlayContainer.getContainerElement();
  }

  getTheme(): boolean {
    this.refreshTheme();
    return this.themeService.alternativeTheme;
  }

  refreshTheme(): void {
    if (this.themeService.alternativeTheme) {
      if (!this.overlay.classList.contains("alternative")) {
        this.overlay.classList.add("alternative");
      }
    } else {
      if (this.overlay.classList.contains("alternative")) {
        this.overlay.classList.remove("alternative");
      }
    }
  }
}
