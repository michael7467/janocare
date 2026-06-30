import { inject } from '@angular/core';
import { CanMatchFn, Route, Router } from '@angular/router';
import { map } from 'rxjs';
import { Role } from '../models';
import { AuthService } from '../services';

export const AuthRoleGuard = (roles?: Role[]): CanMatchFn => {
  const guard = () => {
    const auth = inject(AuthService);
    console.log('Ath user',auth);
    const router = inject(Router);
    const initUrl = router.getCurrentNavigation().initialUrl;
    const queryParams = { returnUrl: initUrl.toString() };
    const isRoleCheckEnabled = false;
   
     console.log('AuthRoleGuard: Checking access for roles', roles);
     console.log('AuthRoleGuard: User roles', auth);
     console.log('AuthRoleGuard: User is logged in', auth.isLoggedIn$);
    const hasAccess$ = isRoleCheckEnabled ? auth.hasAnyOftheRoles$(roles) : auth.isLoggedIn$;
 
    const url = router.createUrlTree(['/auth/login'], { queryParams });
  
    return hasAccess$.pipe(map((isAuthed) => isAuthed || url));
  };
  return guard;
};

export const topupGuard = (): CanMatchFn => {
  const guard = (route: Route) => {
    const authService = inject(AuthService);
    const router = inject(Router);
    const initUrl = router.getCurrentNavigation().initialUrl;
    const productId = initUrl.queryParamMap.has('productId') ? +initUrl.queryParamMap.get('productId') : null;
    const isTopupRoute = route.path?.startsWith('airtime-topups');
    const isProductSelected = productId && !isNaN(productId);
    const isProductSelectedForTopup = isProductSelected && isTopupRoute;

    if (!isProductSelectedForTopup) return true;

    const queryParams = { returnUrl: initUrl.toString() };
    const url = router.createUrlTree(['/auth/login'], { queryParams });
    return authService.isLoggedIn$.pipe(map((isAuthed) => isAuthed || url));
  };
  return guard;
};
