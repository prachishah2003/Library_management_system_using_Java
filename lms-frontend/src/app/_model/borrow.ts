export class Borrow {
    borrowId: number;
    bookId: number;
    userId: number;
    issueDate: Date;
    returnDate: Date;
    dueDate: Date;
    returnRequestStatus: 'NONE' |'PENDING' | 'APPROVED' | 'REJECTED';
}
