import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductServiceComponent } from './product-service-component/product-service-component.component';
import { ProductServiceRoutingModule } from './product-service-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';



@NgModule({
  declarations: [
    ProductServiceComponent
  ],
  imports: [
    ProductServiceRoutingModule,
    FormsModule,
    CommonModule,
    HttpClientModule
  ]
})
export class ProductServiceModule { }
