package hello.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        System.out.println("RefreshTokenRepositoryImpl.save");
        var sql = "INSERT INTO refresh_token VALUES (:refreshToken, :expireTime, :userId)";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(refreshToken);
        int updatedRows = jdbcTemplate.update(sql, param);
        if (updatedRows != 1) throw new RuntimeException();
        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findOne(String tokenValue) {
        var sql = "SELECT * FROM refresh_token where token_value = :token_value";
        MapSqlParameterSource param = new MapSqlParameterSource("token_value", tokenValue);
        List<RefreshToken> query = jdbcTemplate.query(sql, param, rowMapper);
        if (query.size() > 1) throw new RuntimeException();
        return query.stream().findFirst();
    }

    private final RowMapper<RefreshToken> rowMapper = (rs, rowNum) -> RefreshToken.builder()
            .refreshToken(rs.getString("token_value"))
            .expireTime(rs.getTimestamp("expire_time").toLocalDateTime())
            .userId(rs.getLong("user_id"))
            .build();

    @Override
    public void delete(String tokenValue) {
        var sql = "DELETE FROM refresh_token where token_value = :token_value";
        SqlParameterSource param = new MapSqlParameterSource("token_value", tokenValue);
        int update = jdbcTemplate.update(sql, param);
        if (update > 1) throw new RuntimeException();
    }
}
