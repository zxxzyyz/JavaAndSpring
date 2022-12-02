package hello.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginResult login(User user) {
        System.out.println("LoginService.login");
        User saveUser = userRepository.save(user);
        Long id = saveUser.getId();

        String accessToken = jwtProvider.createAccessToken(id, saveUser.getRole());
        RefreshToken refreshToken = refreshTokenProvider.createToken(id);
        refreshTokenRepository.save(refreshToken);
        return new LoginResult(refreshToken.getRefreshToken(), accessToken, saveUser);
    }
}
