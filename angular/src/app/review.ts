export interface Review {
    reviewId: number;
    product_id: number;
    author_id: number;
    author_name: string;
    author_surname: string;
    content: string;
    rating:number;
    likes:number;
    dislikes:number;


}