package hello.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public TokenResult refresh(String refTokenValue) {
        RefreshToken refToken = refreshTokenRepository.findOne(refTokenValue).orElseThrow(RuntimeException::new);
        if (refToken.isExpired()) refreshTokenRepository.delete(refTokenValue);

        Long userId = refToken.getUserId();
        String role = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new).getRole();

        String newToken = jwtProvider.createAccessToken(userId, role);
        RefreshToken newRefToken = refreshTokenProvider.createToken(userId);
        refreshTokenRepository.save(newRefToken);
        refreshTokenRepository.delete(refTokenValue);
        return TokenResult.of(newToken, newRefToken);
    }
}
