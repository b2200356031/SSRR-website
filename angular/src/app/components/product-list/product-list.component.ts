import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/product';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit{

  public products: Product[];

  constructor(private productService : ProductService,private userService: UserService){}

  searchTerm: string = '';
  filteredProducts: Product[] = [];

  //productData : any;


  ngOnInit() {
    this.getProducts();

    this.productService.getProducts().subscribe(products => {
      this.products = products;
      this.filteredProducts = this.products;
    });
  }


  onProductClick(product: Product) {
    
    this.userService.setInterestedCategory(product.categoryId).subscribe(
      response => {
        console.log('User interests updated successfully:', response);
        
      },
      error => {
        console.error('Error updating user interests:', error);
        
      }
    );
  }


  public getProducts(): void {
    this.productService.getProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }

  ngDoCheck() {
    if (this.searchTerm) {
      this.filteredProducts = this.products.filter(product =>
        product.name.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    } else {
      this.filteredProducts = this.products;
    }
  }


}
