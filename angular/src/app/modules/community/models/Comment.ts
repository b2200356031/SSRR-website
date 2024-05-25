import { UserProfile } from "src/app/userprofile"

export interface Comment{
    id?:number,
    text?:string,    
    postId?:number,
    creationDate?:Date,
    likesCount?:number
    usersDto?:UserProfile
}