package hello.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        String sql = "INSERT INTO refresh_token VALUES (:refreshToken, :expireTime, :userId)";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(refreshToken);
        int updatedRows = jdbcTemplate.update(sql, param);
        if (updatedRows != 1) throw new RuntimeException();
        return refreshToken;
    }
}
