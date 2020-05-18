import { Injectable } from '@angular/core';

const CACHED_THEME_KEY = 'theme';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  public alternativeTheme = false;

  constructor() {
    if (this.get(CACHED_THEME_KEY)) {
      this.alternativeTheme = this.get(CACHED_THEME_KEY);
    }
   }

  switchTheme() {
    this.alternativeTheme = !this.alternativeTheme;
    this.set(CACHED_THEME_KEY, this.alternativeTheme);
  }

  set(key: string, data: any): void {
    try {
      localStorage.setItem(key, JSON.stringify(data));
    } catch (e) {
      console.error('Error saving to localStorage', e);
    }
  }

  get(key: string) {
    try {
      return JSON.parse(localStorage.getItem(key));
    } catch (e) {
      console.error('Error getting data from localStorage', e);
      return null;
    }
  }
}
