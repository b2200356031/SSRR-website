import { UserProfile } from "src/app/userprofile";

export interface Post{
    id:number,
    title:string,
    content:string,
    likesCount:number,
    commentsCount:number,
    viewsCount:number
    usersDto:UserProfile,
    creationDate:Date
}