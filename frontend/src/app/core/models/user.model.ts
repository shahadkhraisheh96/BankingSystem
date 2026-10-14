export interface User {
  id?: string;
  userName: string;
  email: string;
  password?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  userName: string;
  email: string;
  password: string;
}

export interface AuthState {
  token: string | null;
  username: string | null;
}
