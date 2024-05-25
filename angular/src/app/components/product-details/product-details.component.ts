
import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, NgForm,FormControl  } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Review } from 'src/app/review';
import { Coupon } from 'src/app/coupon';
import { ProductService } from 'src/app/services/product.service';
import { ReviewService } from 'src/app/services/review.service';
import { CouponService } from 'src/app/services/coupon.service';
@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent {
  productDetail? : any;
  activeTab: string = 'description'; // Default active tab
  reviews: any[];
  coupons: any[];
  couponID:number;
  redeemedCoupons : any[];
  public updateReview: Review;
  public deleteReview: Review;
  submit:boolean
  updateForm:FormGroup;
  submitted:boolean = false;
  pid:number;



  constructor(private route : ActivatedRoute,private productService : ProductService,private reviewService : ReviewService,private couponService : CouponService){}

  ngOnInit(): void {
    let productId = this.route.snapshot.params['id'];
    this.pid = productId;
    this.getProductDetailById(productId)
    this.loadReviews(productId);
    this.loadCoupons(productId);
    this.loadRedeemedCoupons(productId);
  }

  getProductDetailById(id: number){
    this.productService.getProductDetailById(id).subscribe(res => {
      this.productDetail = res
      console.log(res)
    })
  }
  changeTab(tab: string) {
    this.activeTab = tab;
  }


  loadReviews(productId:number): void {
    this.reviewService.getReviews(productId).subscribe(
      (response: Review[]) => {
        this.reviews = response;
        this.getProductDetailById(this.pid);
        console.log(this.reviews);
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }


  submitReview(review:NgForm) {
    const productId = this.productDetail.id;

    console.log('Review submitted successfully:', review.value);
    this.reviewService.addReview(productId, review.value).subscribe(
      response => {
        console.log('Review submitted successfully:', response);
        this.loadReviews(productId);
        review.reset();
      },
      error => {
        console.error('Error submitting review:', error);
      }
    );
  }

  public onUpdateReview(review: Review): void {
    document.getElementById("close-button").click();
    this.updateReview.content = review.content;
    this.updateReview.rating = review.rating;
    this.reviewService.updateReview(this.updateReview).subscribe(
      (response: Review) => {
        console.log(review);
        this.loadReviews(this.pid);

      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public onDeleteReview(reviewId: number): void {
    document.getElementById("delete-close-button").click();
    this.reviewService.deleteReview(reviewId).subscribe(
      (response: void) => {
        this.loadReviews(this.pid);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  public onOpenModal(review: Review, mode: String): void {

    const container = document.createElement('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode == 'update') {
      this.updateReview = review;
      button.setAttribute('data-target', '#updateReviewModal');
    }
    if (mode == 'delete') {
      this.deleteReview = review;
      button.setAttribute('data-target', '#deleteReviewModal');
    }

    container.appendChild(button);
    button.click();


  }

  loadCoupons(productId:number): void {
    this.couponService.getCoupons(productId).subscribe(
      (response: Coupon[]) => {
        this.coupons = response;

      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }

  loadRedeemedCoupons(productId:number): void {
    this.couponService.getRedeemedCoupons(productId).subscribe(
      (response: Coupon[]) => {
        this.redeemedCoupons = response;

      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }

  redeemCoupons(coupon:Coupon):void{
    console.log(coupon);
    	this.couponService.redeemCoupon(coupon).subscribe(
        (response: Coupon) => {
          console.log(response)
          alert('The coupon has been successfully added to your account.')

          this.loadCoupons(this.pid);
          this.loadRedeemedCoupons(this.pid);

        },
        (error) => {
          console.error('Error fetching products:', error);
          alert('There are not enough points or available coupons.')
        }
      );
  }

  editCoupons(coupon:Coupon):void{
    console.log(coupon);
    	this.couponService.editCoupon(coupon).subscribe(
        (response: Coupon) => {
          console.log(response)

        },
        (error) => {
          console.error('Error fetching products:', error);
        }
      );

  }

  deleteCoupons(coupon_id:number):void{

    	this.couponService.deleteCoupon(coupon_id).subscribe(
        (response: number) => {
          console.log(response)
          alert('The coupon has been successfully deleted.')
          this.loadCoupons(this.pid);
          this.loadRedeemedCoupons(this.pid); 


        },
        (error) => {
          console.error('Error fetching products:', error);
        }
      );

  }

  shareOnTwitter(): void {
    // Paylaşılacak içeriğin metnini oluşturun
    var tweetText: string ="Check out the new product that I found on ShopSmart!!!!!! It's amazing!!! "+ "http://localhost:4200/product/" + this.pid;
  
    const shareURL: string = `https://twitter.com/intent/tweet?text=${encodeURIComponent(tweetText)}`;
  
    // Yeni bir pencerede Twitter paylaşım sayfasını açın
    window.open(shareURL, '_blank');
  }

  shareOnFacebook(): void {
   
  }

  toggleLike(review: Review): void{
    this.updateReview = JSON.parse(JSON.stringify(review));
    this.updateReview.likes +=1;
    this.reviewService.updateReview(this.updateReview).subscribe(
      (response: Review) => {
        console.log(review);
        this.loadReviews(this.pid);

      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
}

toggleDislike(review: Review): void{
    this.updateReview = JSON.parse(JSON.stringify(review));
    this.updateReview.dislikes +=1;
    this.reviewService.updateReview(this.updateReview).subscribe(
      (response: Review) => {
        console.log(review);
        this.loadReviews(this.pid);

      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
}






}
