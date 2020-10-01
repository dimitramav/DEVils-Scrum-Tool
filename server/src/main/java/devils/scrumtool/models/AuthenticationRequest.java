package devils.scrumtool.models;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    // Authentication model for login
    private String username;
    private String password;

    public AuthenticationRequest() {}

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
