export class User {
  userId: number;
  username: string;
  name: string;
}

export class Feedback {
  id: number;
  feedbackText: string;
  rating: number;
  user: User; // This ensures 'user.username' is valid in the template
}
