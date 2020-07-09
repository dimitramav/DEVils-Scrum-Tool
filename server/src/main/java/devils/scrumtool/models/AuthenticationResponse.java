package devils.scrumtool.models;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private Integer id;
    private String username;

    public AuthenticationResponse(String jwt, Integer id, String username) {
        this.jwt = jwt;
        this.id = id;
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
