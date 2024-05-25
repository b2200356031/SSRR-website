import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { AuthGuard } from './guards/auth.guard';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { ProductRatingComponent } from './components/product-rating/product-rating.component';
import { ProductOffersComponent } from './components/product-offers/product-offers.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'products', component: ProductListComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent},
  {
    path: 'product/:id', component: ProductDetailsComponent, canActivateChild: [AuthGuard],
    children: [
      { path: 'rating', component: ProductRatingComponent, canActivate: [AuthGuard] },
      { path: 'offers', component: ProductOffersComponent, canActivate: [AuthGuard]}
    ]
  },
  {
    path: 'service',
    loadChildren: () => import('./modules/product-service/product-service.module').then(m => m.ProductServiceModule),
    canLoad: [AuthGuard], canActivate: [AuthGuard]
  },
  {
    path: 'forum',
    loadChildren: () => import('./modules/community/community.module').then(m => m.CommunityModule),
    canLoad: [AuthGuard], canActivate: [AuthGuard]
  },
  { path: 'user', component: UserProfileComponent, canActivate: [AuthGuard]},
  //{ path: '**', component: ProductListComponent, pathMatch: 'full', canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
