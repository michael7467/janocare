export class LoginRequest {
  identifier: string;
  password: string;
}
export class LoginResponse {
  // {
  //   "access_token": "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImZlYzZjNzA2LWJkMDYtNDE3Mi1hNDA1LWExZmVmMjFmMDcwYSJ9.eyJzdWIiOjcsIm5hbWUiOiJqZW5ueSIsInBob25lIjoiOTIzNDMyMzciLCJlbWFpbCI6bnVsbCwidWlkIjo3LCJyb2xlIjoiQURNSU4iLCJleHAiOjE3MjkwOTM2ODQsImlhdCI6MTcwNTA5MzY4NCwidHlwIjoiQmVhcmVyIiwiaXNzIjoiaHR0cDovL2FwaS5lYW5kLmV0In0.yaI5RHKGwaOdE9X-gAhag8vmmzFB-iN0ayGcMTQ33JMLJbtzMKY5R05ByVmoHYo7RoJRA0_NVKT2HqhIUBF7lg",
  //   "success": true,
  //   "statusCode": 200,
  //   "message": "Login Successful"
  // }
  access_token: string;
  success: boolean;
  statusCode: number;
  message: string;
}
