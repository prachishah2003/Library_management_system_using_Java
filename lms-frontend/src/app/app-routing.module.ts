import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookDetailsComponent } from './book-details/book-details.component';
import { BooksListComponent } from './books-list/books-list.component';
import { BorrowBookComponent } from './borrow-book/borrow-book.component';
import { CreateBookComponent } from './create-book/create-book.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { ReturnBookComponent } from './return-book/return-book.component';
import { UpdateBookComponent } from './update-book/update-book.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UsersListComponent } from './users-list/users-list.component';
import { AdminRequestedBooksComponent } from './admin-requested-books-component/admin-requested-books-component.component';
import { AuthGuard } from './_auth/auth.guard';
import {AdminFeedbackComponent} from './admin-feedback/admin-feedback.component';
import { AddUserComponent } from './add-user/add-user.component';
import { AdminReturnsComponent } from './admin-returns/admin-returns.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { BorrowListComponent } from './borrow-list/borrow-list.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { AdminOverdueBooksComponent } from './admin-overdue-books/admin-overdue-books.component';
const routes: Routes = [
  {path: 'books', component: BooksListComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'create-book', component: CreateBookComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'borrow-list', component: BorrowListComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: '', component: HomeComponent},
  {path: 'update-book/:bookId', component: UpdateBookComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'book-details/:bookId', component: BookDetailsComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'requested-books', component: AdminRequestedBooksComponent,canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'users', component: UsersListComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'dashboard', component: AdminDashboardComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'view-feedback', component: AdminFeedbackComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'register-user', component: RegistrationComponent},
  {path: 'user-details/:userId', component: UserDetailsComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'update-user/:userId', component: UpdateUserComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'add-user', component: AddUserComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'return-req', component: AdminReturnsComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'login', component: LoginComponent},
  {path: 'overdue', component: AdminOverdueBooksComponent, canActivate:[AuthGuard], data:{roles:['Admin']}},
  {path: 'forbidden', component: ForbiddenComponent},
  {path: 'borrow-book', component: BorrowBookComponent, canActivate:[AuthGuard], data:{roles:['User']}},
  {path: 'profile', component: UserProfileComponent, canActivate:[AuthGuard], data:{roles:['User']}},
  {path: 'return-book', component: ReturnBookComponent, canActivate:[AuthGuard], data:{roles:['User']}}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
