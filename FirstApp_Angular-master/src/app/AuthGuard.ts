import { inject } from '@angular/core';
import {
  CanActivateFn,
  CanMatchFn,
  Route,
  Router,
  RouterStateSnapshot,
  UrlSegment,
  UrlTree,
} from '@angular/router';
import { AuthService } from './auth.service';

const isUserAuthenticated = (authService: AuthService): boolean => {
  const svc: any = authService;

  if (typeof svc.isAuthenticated === 'function') return !!svc.isAuthenticated();
  if (typeof svc.hasToken === 'function') return !!svc.hasToken();
  if (typeof svc.isLoggedIn === 'function') return !!svc.isLoggedIn();

  if (typeof svc.isAuthenticated === 'boolean') return svc.isAuthenticated;
  if (typeof svc.isLoggedIn === 'boolean') return svc.isLoggedIn;

  return false;
};

const redirectToLogin = (router: Router, returnUrl: string): UrlTree =>
  router.createUrlTree(['/login'], { queryParams: { returnUrl } });

export const authGuard: CanActivateFn = (
  _route,
  state: RouterStateSnapshot
): boolean | UrlTree => {
  const router = inject(Router);
  const authService = inject(AuthService);

  return isUserAuthenticated(authService)
    ? true
    : redirectToLogin(router, state.url || '/');
};

export const authMatchGuard: CanMatchFn = (
  _route: Route,
  segments: UrlSegment[]
): boolean | UrlTree => {
  const router = inject(Router);
  const authService = inject(AuthService);
  const returnUrl = '/' + segments.map((s) => s.path).join('/');

  return isUserAuthenticated(authService)
    ? true
    : redirectToLogin(router, returnUrl || '/');
};