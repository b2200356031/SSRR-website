import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable, of, tap } from 'rxjs';
import { LoginComponent } from '../components/login/login.component';
import { Product } from '../product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiUrl = 'http://localhost:8080';
  
  // Bearer Token ---> authToken yapın
  private authSecretKey = 'authToken';

  constructor(private http: HttpClient) { }

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

  getProducts(): Observable<Product[]> {
    const headers = this.getHeaders();

    return this.http.get<Product[]>(`${this.apiUrl}/products`, { headers });
  }

  addProduct(product: Product): Observable<Product> {
    const headers = this.getHeaders();

    return this.http.post<Product>(`${this.apiUrl}/service/add`,  product , {headers});
    //normalde yukarıdaki gibi headers var ama bende bozuyor diye sildim bakılır demodan sonra
    //bakıldı
  }

  updateProduct(product: Product): Observable<Product> {
    const headers = this.getHeaders();

    return this.http.put<Product>(`${this.apiUrl}/service/update`, product, { headers });
  }

  deleteProduct(productId: number): Observable<void> {
    const headers = this.getHeaders();

    return this.http.delete<void>(`${this.apiUrl}/service/delete/${productId}`, { headers });
  }

  getProductsToService(): Observable<Product[]> {
    const headers = this.getHeaders();

    return this.http.get<Product[]>(`${this.apiUrl}/service`, { headers });
  }

  getProductDetailById(id: number) {

    const headers = this.getHeaders();

    return this.http.get(`${this.apiUrl}/products/` + id, { headers })
  }

  getRecommendedProducts(): Observable<Product[]> {
    const headers = this.getHeaders();

    return this.http.get<Product[]>(`${this.apiUrl}/products/recommended`, { headers });
  }

  getRecommendedProductsWithoutToken(): Observable<Product[]> {
    
    const products = JSON.parse(localStorage.getItem('products'));
    if (products) {
      return of(products);
    } else {
      
      // Fetch the products from the backend
      return this.http.get<Product[]>(`${this.apiUrl}/products/recommendedtokenless`).pipe(
        tap(products => {
          // Store the products in local storage
          localStorage.setItem('products', JSON.stringify(products));

        })
      );
    }
  }
}
