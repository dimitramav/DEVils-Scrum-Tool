package devils.scrumtool.controllers;

import devils.scrumtool.entities.User;
import devils.scrumtool.exceptions.CustomException;
import devils.scrumtool.models.AuthenticationRequest;
import devils.scrumtool.models.AuthenticationResponse;
import devils.scrumtool.repositories.UserRepository;
import devils.scrumtool.security.CustomUserDetailsService;
import devils.scrumtool.security.JwtUtilService;
// Java libraries
import java.util.Optional;
// Spring libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// Rest api for login (checks credentials and returns a json web token)
@RestController
public class AuthenticationController {

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private JwtUtilService jwtTokenUtil;

    @Autowired private CustomUserDetailsService userDetailsService;

    @Autowired private UserRepository userRepo;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        // Along with jwt send to client user's id and username for localStorage
        Optional<User> user = userRepo.findByEmail(userDetails.getUsername());
        if (user.isPresent()) {
            User existingUser = user.get();
            return ResponseEntity.ok(
                    new AuthenticationResponse(
                            jwt, existingUser.getId(), existingUser.getUsername()));
        } else {
            throw new CustomException("User with this email not found!");
        }
    }
}
