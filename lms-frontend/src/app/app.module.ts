import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; 
import { NgChartsModule } from 'ng2-charts';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';

import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BooksListComponent } from './books-list/books-list.component';
import { CreateBookComponent } from './create-book/create-book.component';
import { FormsModule} from '@angular/forms';
import { UpdateBookComponent } from './update-book/update-book.component';
import { BookDetailsComponent } from './book-details/book-details.component';
import { RegistrationComponent } from './registration/registration.component';
import { UsersListComponent } from './users-list/users-list.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { BooksService } from './_service/books.service';
import { UsersService } from './_service/users.service';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faEdit, faEye, faTrash } from '@fortawesome/free-solid-svg-icons';
import { AuthGuard } from './_auth/auth.guard';
import { AuthInterceptor } from './_auth/auth.interceptor';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { BorrowBookComponent } from './borrow-book/borrow-book.component';
import { ReturnBookComponent } from './return-book/return-book.component';
import { AdminRequestedBooksComponent } from './admin-requested-books-component/admin-requested-books-component.component';
import { AdminFeedbackComponent } from './admin-feedback/admin-feedback.component';
import { RatingPopupComponent } from './rating-popup/rating-popup.component';
import { SidebarComponent }    from './sidebar/sidebar.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { AddUserComponent } from './add-user/add-user.component';
import { AdminReturnsComponent } from './admin-returns/admin-returns.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // Required for animations
import { ToastrModule } from 'ngx-toastr';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { BorrowListComponent } from './borrow-list/borrow-list.component';

@NgModule({ declarations: [
        AppComponent,
        BooksListComponent,
        BorrowListComponent,
        AdminDashboardComponent,
        AddUserComponent,
        AdminReturnsComponent,
        SidebarComponent,
        ContactUsComponent,
        CreateBookComponent,
        UpdateBookComponent,
        BookDetailsComponent,
        RegistrationComponent,
        UsersListComponent,
        UserDetailsComponent,
        UpdateUserComponent,
        LoginComponent,
        LogoutComponent,
        AdminFeedbackComponent,
        HeaderComponent,
        HomeComponent,
        ForbiddenComponent,
        BorrowBookComponent,
        ReturnBookComponent,
        AdminRequestedBooksComponent
    ],
    bootstrap: [AppComponent], imports: [BrowserModule,
        AppRoutingModule,
        FormsModule,
        MatDialogModule,
        RatingPopupComponent,
        NgChartsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    MatButtonModule,
    BrowserAnimationsModule, // Required animations module
    ToastrModule.forRoot(),
        CommonModule,
        RouterModule,
        FontAwesomeModule,
        ReactiveFormsModule], providers: [
        AuthGuard,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        },
        UsersService,
        BooksService,
        provideHttpClient(withInterceptorsFromDi())
    ] })
export class AppModule {constructor() {
    library.add(faEdit, faEye, faTrash);
  } }
