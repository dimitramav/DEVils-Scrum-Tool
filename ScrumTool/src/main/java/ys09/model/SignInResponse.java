package ys09.model;

public class SignInResponse {
    int userId;
    String auth_token;

    public SignInResponse(int userId, String auth_token) {
        this.userId = userId;
        this.auth_token = auth_token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
