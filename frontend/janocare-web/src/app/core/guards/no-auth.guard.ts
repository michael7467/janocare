import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { AuthService } from '../services';
import { map } from 'rxjs';

// @Injectable({ providedIn: 'root' })
// export class NoAuthGuard implements CanActivate {
//   constructor(private authService: AuthService, private router: Router) {}
//   canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
//     const currentUser = this.authService.userProfile;
//     if (!currentUser || !currentUser?.id) {
//       return true;
//     }
//     this.router.navigate(['/']);
//     return false;
//   }
// }

export const NoAuthGuard = (): CanActivateFn => {
  const guard: CanActivateFn = () => {
    return inject(AuthService).isLoggedIn$.pipe(map((isLoggedIn) => !isLoggedIn));
    // const router = inject(Router);
    // const route = inject(ActivatedRouteSnapshot);
    // return authService.isLoggedIn$.pipe(
    //   map((isLoggedIn) => {
    //     if (isLoggedIn) return route.queryParamMap.has('returnUrl') ? router.createUrlTree([route.queryParamMap.get('returnUrl')]) : false;
    //     //  router.navigate(['/'], { queryParamsHandling: 'merge', queryParams: { returnUrl: state.url } });
    //     return true;
    //   }),
    // );
  };

  return guard;
};
