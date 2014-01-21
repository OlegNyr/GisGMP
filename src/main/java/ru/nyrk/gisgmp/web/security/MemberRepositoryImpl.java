package ru.nyrk.gisgmp.web.security;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: oanyrkov
 * Date: 10.03.13
 * Time: 23:16
 */
public class MemberRepositoryImpl extends JdbcDaoSupport implements PersistentTokenRepository {
    public static final String sqlInsert =
            "insert into wp_persistent_logins\n" +
                    "  (series_str, token_str, user_upper_nm, last_used)\n" +
                    "values\n" +
                    "  (?, ?, ?, ?)";
    public static final String sqlUpdate =
            "update wp_persistent_logins\n" +
                    "   set\n" +
                    "       token_str = ?,\n" +
                    "       last_used = ?\n" +
                    " where series_str = ?";
    public static final String sqlDelete =
            "delete wp_persistent_logins u\n" +
                    " where u.user_upper_nm = ?";
    public static final String sqlSelect =
            "select series_str, token_str, user_upper_nm, last_used from wp_persistent_logins p\n" +
                    "where p.series_str = ?";

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        getJdbcTemplate().update(sqlInsert, token.getSeries(),
                token.getTokenValue(), token.getUsername(), token.getDate());
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        getJdbcTemplate().update(sqlUpdate, tokenValue, new Date(), series);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            return getJdbcTemplate().queryForObject(sqlSelect, new RowMapper<PersistentRememberMeToken>() {
                public PersistentRememberMeToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                    //String username, String series, String tokenValue, Date date
                    return new PersistentRememberMeToken(rs.getString(3),
                            rs.getString(1),
                            rs.getString(2),
                            rs.getTimestamp(4));
                }
            }, seriesId);
        } catch (EmptyResultDataAccessException zeroResults) {
            if (logger.isInfoEnabled()) {
                logger.info("Querying token for series '" + seriesId + "' returned no results.", zeroResults);
            }
        } catch (IncorrectResultSizeDataAccessException moreThanOne) {
            logger.error("Querying token for series '" + seriesId + "' returned more than one value. Series" +
                    " should be unique");
        } catch (DataAccessException e) {
            logger.error("Failed to load token for series " + seriesId, e);
        }

        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        getJdbcTemplate().update(sqlDelete, username);
    }
}
