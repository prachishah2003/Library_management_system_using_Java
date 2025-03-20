export class User {
  id: number;
  username: string;
}

export class RequestedBook {
  id?: number;
  bookName: string;
  requestedBy?: User;  // Now it's an object, not just an ID
  requestedAt?: Date;
}
