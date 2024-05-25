import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Coupon } from '../coupon';

@Injectable({
    providedIn: 'root'
  })
  export class CouponService {

    private apiUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) { }


    private authSecretKey = 'authToken';


    private getHeaders(): HttpHeaders {
        const authToken = localStorage.getItem(this.authSecretKey);
        console.log(authToken);

        if (authToken && authToken.trim() !== '') {
          return new HttpHeaders({
            'Content-Type': 'application/json',
            Authorization: `Bearer ${authToken.trim()}`
          });
        } else {
          throw new Error('Auth token is not available');
        }
      }

    addCoupon(coupon: Coupon,id: number): Observable<Coupon> {
        const headers = this.getHeaders();

        return this.http.post<Coupon>(`${this.apiUrl}/service/addCoupon/${id}`,  coupon , {headers});
      }

    getCoupons(productId: number): Observable<Coupon[]> {
        const headers = this.getHeaders();
        return this.http.get<Coupon[]>(`${this.apiUrl}/product/${productId}/coupon`, {headers});
      }

      getRedeemedCoupons(productId: number): Observable<Coupon[]> {
        const headers = this.getHeaders();
        return this.http.get<Coupon[]>(`${this.apiUrl}/product/${productId}/redeemedCoupon`, {headers});
      }

    redeemCoupon(coupon:Coupon): Observable<Coupon>{
        const headers = this.getHeaders();

        return this.http.put<Coupon>(`${this.apiUrl}/product/redeem`,coupon, {headers});

    }

    editCoupon(coupon:Coupon): Observable<Coupon>{
      const headers = this.getHeaders();
      console.log(headers)
      return this.http.put<Coupon>(`${this.apiUrl}/service/updateCoupon`,coupon, {headers});



  }
  deleteCoupon(coupon_id:number): Observable<number>{
    const headers = this.getHeaders();
    console.log(headers)
    return this.http.delete<number>(`${this.apiUrl}/service/deleteCoupon/${coupon_id}`, {headers});


}


}
