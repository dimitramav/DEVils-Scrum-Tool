package devils.scrumtool.security;

import devils.scrumtool.entities.User;
// Java libraries
import java.util.Arrays;
import java.util.Collection;
// Spring libraries
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    // All UserDetails methods declared
    private String username;
    private String password;
    private Boolean isAdmin;

    public CustomUserDetails() {}

    public CustomUserDetails(User user) {
        // In our application we use email for login and not username
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.isAdmin = user.getIsAdmin();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.isAdmin) {
            return Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
        } else {
            return Arrays.asList(new SimpleGrantedAuthority("USER"));
        }
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
