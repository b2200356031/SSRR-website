import { HttpErrorResponse } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Product } from 'src/app/product';
import { Coupon } from 'src/app/coupon';
import { ProductService } from 'src/app/services/product.service';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { CouponService } from 'src/app/services/coupon.service';
import { UserProfile } from 'src/app/userprofile';
import { Chart, LinearScale, BarElement, BarController, CategoryScale, Tooltip, Legend } from 'chart.js';
import * as bootstrap from 'bootstrap';

@Component({
  selector: 'app-product-service-component',
  templateUrl: './product-service-component.component.html',
  styleUrls: ['./product-service-component.component.css']
})
export class ProductServiceComponent implements OnInit {

  
  public updateProduct: Product;
  public selectedProduct: Product;
  public products: Product[];
  public deleteProduct: Product;
  public user: UserProfile;
  chart: any;
  categories = [
    { id: 1, name: 'Electronics' },
    { id: 2, name: 'Books' },
    { id: 3, name: 'Clothing' },
    { id: 4, name: 'Home & Kitchen' },
    { id: 5, name: 'Sports' },
    { id: 6, name: 'Toys' },
    { id: 7, name: 'Automotive' },
    { id: 8, name: 'Health' },
    { id: 9, name: 'Beauty' }
  ];

  constructor(private userService: UserService ,private productService: ProductService,private http: HttpClient,private couponService : CouponService)
  {Chart.register(LinearScale, BarElement, BarController, CategoryScale, Tooltip, Legend);}

  //productData : any;


  ngOnInit() {
    this.userService.getUserProfile().subscribe((userProfile: UserProfile) => this.user = userProfile );
    this.getProducts();
  }


  public getProducts(): void {
    this.productService.getProductsToService().subscribe(
      (response: Product[]) => {
        this.products = response;
        console.log(this.products);
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }



  checkUrlValidity(url: string): Observable<boolean> {
    if (!url) {
      return of(false); // Handle empty URL case
    }
    return this.http.get(url, { observe: 'response' }).pipe(
      map(response => {
        if (response.status === 200) {
          return true;
        } else if (response.status === 404) {
          return false;
        } else {
          return false; // Any other response or non-HTTPS URL is considered invalid
        }
      }),
      catchError(error => {
        if (error.status === 404) {
          return of(false); // Handle 404 Not Found
        } else {
          return of(true); // Rethrow other errors
        }
      }) // Handle HTTP errors
    );
  }

  public async onAddProduct(addForm: NgForm): Promise<void> {

    console.log();

    if (addForm.value.imageUrl.length > 256) {
      alert("Görsel URL'si 256 karakterden fazla olamaz!");
      return;
    }

    try {
      const isValid = await this.checkUrlValidity(addForm.value.imageUrl).toPromise();

      if (!isValid) {
        console.log('URL is INvalid:', isValid);
        alert("Görsel URL'si geçersiz!");
        return;
      } else {
        console.log('URL is valid:', isValid);
      }



      document.getElementById("add-product-form")?.click();
      this.productService.addProduct(addForm.value).subscribe(
        (response: Product) => {
          console.log(addForm.value);
          this.getProducts();
          addForm.reset();
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
          addForm.reset();
        }
      );
    } catch (error) {
      console.error('Error validating URL:', error);
      alert("URL doğrulanırken bir hata oluştu.");
    }
  }



  public onUpdateProduct(product: Product): void {
    this.productService.updateProduct(product).subscribe(
      (response: Product) => {
        this.getProducts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public onDeleteProduct(productId: number): void {
    document.getElementById("delete-close-button").click();
    this.productService.deleteProduct(productId).subscribe(
      (response: void) => {
        this.getProducts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }


  public onOpenModal(product: Product, mode: String): void {
    const container = document.createElement('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode == 'add') {
      button.setAttribute('data-target', '#addProductModal');
    }
    if (mode == 'update') {
      this.updateProduct = product;
      button.setAttribute('data-target', '#updateProductModal');
    }
    if (mode == 'delete') {
      this.deleteProduct = product;
      button.setAttribute('data-target', '#deleteProductModal');
    }
    if (mode == 'addCoupon') {
      this.selectedProduct = product;
      button.setAttribute('data-target', '#addCouponModal');
    }



    container.appendChild(button);
    button.click();
  }

  public addCoupon(form: NgForm): void {
    const newCoupon: Coupon = {
      coupon_id: 0, 
      productId: this.selectedProduct.id,
      price: form.value.price,
      discount: form.value.discount,
      avaibleCoupons: form.value.availableCoupons,
      couponCode: form.value.couponCode
    };
    
    this.couponService.addCoupon(newCoupon,this.selectedProduct.id).subscribe(
      response => {
        console.log('User interests updated successfully:', response);
        
      },
      error => {
        console.error('Error updating user interests:', error);
        
      }
    );

    console.log('Adding coupon', newCoupon);
    

   

  }

  openAnalyticsModal(): void {
    const productNames = this.products.map(product => product.name);
    const averageRatings = this.products.map(product => product.rating);
    const ratingCounts = this.products.map(product => product.reviewCount);

    setTimeout(() => {
      const ctx = document.getElementById('analyticsChart') as HTMLCanvasElement;
      if (this.chart) {
        this.chart.destroy();
      }
      this.chart = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: productNames,
          datasets: [
            {
              label: 'Average Rating',
              data: averageRatings,
              backgroundColor: 'rgba(75, 192, 192, 0.2)',
              borderColor: 'rgba(75, 192, 192, 1)',
              borderWidth: 1
            },
            {
              label: 'Rating Count',
              data: ratingCounts,
              backgroundColor: 'rgba(153, 102, 255, 0.2)',
              borderColor: 'rgba(153, 102, 255, 1)',
              borderWidth: 1
            }
          ]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });
    }, 500);

    const analyticsModal = new bootstrap.Modal(document.getElementById('analyticsModal'), {
      keyboard: false
    });
    analyticsModal.show();
  }

  closeAnalyticsModal(): void {
    const analyticsModal = bootstrap.Modal.getInstance(document.getElementById('analyticsModal'));
    if (analyticsModal) {
      analyticsModal.hide();
    }
  }



}







