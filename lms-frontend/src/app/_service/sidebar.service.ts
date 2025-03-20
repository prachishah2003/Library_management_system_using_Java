import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {
  private _isCollapsed = false;
  isCollapsed$ = new BehaviorSubject<boolean>(this._isCollapsed);

  get isCollapsed(): boolean {
    return this._isCollapsed;
  }

  toggleSidebar() {
    this._isCollapsed = !this._isCollapsed;
    this.isCollapsed$.next(this._isCollapsed);
  }
}
