package devils.scrumtool.security;

import devils.scrumtool.entities.User;
import devils.scrumtool.repositories.UserRepository;
// Java libraries
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // In our application, login with email, not username
        Optional<User> user = userRepo.findByEmail(username);
        // if (user == null) throw new UsernameNotFoundException(email);
        // return new CustomUserDetails(user);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return user.map(CustomUserDetails::new).get();
    }
}
