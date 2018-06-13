package ys09.model;

// Class for keeping the items needed for local storage in browser
public class LocalStorage {

    int userId;
    String username;
    String auth_token;

    public LocalStorage(int userId, String username, String auth_token) {
        this.userId = userId;
        this.username = username;
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

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
