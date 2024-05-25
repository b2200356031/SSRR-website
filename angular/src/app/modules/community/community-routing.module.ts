import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForumComponent } from './pages/forum/forum.component';
import { PostDetailsComponent } from './components/post/post-details/post-details.component';

const routes: Routes = [{ path: '', component: ForumComponent },{ path: 'post/details/:id', component: PostDetailsComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CommunityRoutingModule { }
