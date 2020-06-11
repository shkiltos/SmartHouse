import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DefaultModule } from './layouts/default/default.module';
import { ThemeService } from './shared/services/theme.service';
import { OverlayModule } from '@angular/cdk/overlay';
import { WelcomeComponent } from './layouts/welcome/welcome.component';
import { SortablejsModule } from 'ngx-sortablejs';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    DefaultModule,
    OverlayModule,
    SortablejsModule.forRoot({
      animation: 200,
    }),
  ],
  providers: [ThemeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
