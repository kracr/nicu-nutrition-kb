import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import { PreloadingStrategy, Route } from '@angular/router';

export class CustomPreloading implements PreloadingStrategy {
  preload(route: Route, preload: Function): Observable<any> {
  console.log("Sunny Says 1");
    if (route.data && route.data["preload"]) {
    console.log("Sunny Says 2");
      return preload();
    } else {
    console.log("Sunny Says 3");
      return Observable.of(null);
    }
  }
}