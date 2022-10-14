package com.jhs.crud.auth.service;

import com.jhs.crud.auth.domain.RefreshToken;
import com.jhs.crud.auth.domain.User;
import com.jhs.crud.auth.dto.JwtResponseDto;
import com.jhs.crud.auth.dto.SigninReq;
import com.jhs.crud.auth.dto.SignupReq;
import com.jhs.crud.auth.repository.TokenRepository;
import com.jhs.crud.auth.repository.UserRepository;
import com.jhs.crud.auth.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     * */
    public ResponseEntity<?> login(SigninReq signinReq) throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername(signinReq.getEmail());

        authenticate(signinReq.getEmail(), signinReq.getPw());

        String uuid = UUID.randomUUID().toString();
        final String token = jwtTokenUtil.generateToken(userDetails);
        final String refreshtoken = jwtTokenUtil.RefreshToken(uuid);
        insertUuid(uuid, signinReq.getEmail());

        return ResponseEntity.ok(new JwtResponseDto(token, refreshtoken));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public void insertUuid(String uuid, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () ->  new IllegalArgumentException("존재하지 않는 email 입니다.")
        );
        LocalDateTime expiredDt = LocalDateTime.now().plusMinutes(2);
        RefreshToken refreshToken = new RefreshToken(uuid, user, expiredDt);
        tokenRepository.save(refreshToken);
    }

    /**
     * 회원가입
     * */
    public ResponseEntity<?> signup(SignupReq signupReq) {
        String email = signupReq.getEmail();
        String pw = signupReq.getPw();
        String name = signupReq.getName();

        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()){
            throw new IllegalArgumentException("이미 가입된 Email 입니다.");
        }

        String password = passwordEncoder.encode(pw);

        User user = new User(email, password, name);
        userRepository.save(user);

        return ResponseEntity.ok("회원가입 완료");
    }
}
