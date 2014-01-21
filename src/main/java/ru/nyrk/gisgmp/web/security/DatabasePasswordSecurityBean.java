package ru.nyrk.gisgmp.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: oanyrkov
 * Date: 10.03.13
 * Time: 22:56
 */
public class DatabasePasswordSecurityBean extends JdbcDaoSupport {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SaltSource saltSource;
    @Qualifier("jdbcUserService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Qualifier("dataSource")
    @Autowired
    private DataSource myDataSource;

//    public void secureDatabase() {
//        getJdbcTemplate().query("select u.user_upper_nm, u.password from wp_users u where length(u.password) < 20",
//                new RowCallbackHandler() {
//
//                    @Override
//                    public void processRow(ResultSet rs) throws SQLException {
//                        String username = rs.getString(1);
//                        String password = rs.getString(2);
//                        UserDetails user =
//                                userDetailsService.loadUserByUsername(username);
//                        String encodedPassword =
//                                passwordEncoder.encodePassword(password,
//                                        saltSource.getSalt(user));
//                        getJdbcTemplate().update("update wp_users set password = ? where user_upper_nm = ?",
//                                encodedPassword,
//                                username);
//                        logger.info("Updating password for username: " + username + " to:" + encodedPassword);
//                    }
//                });
//    }

    public void changePassword(Integer userId, String password) {
        String username = getJdbcTemplate().queryForObject("select u.user_upper_nm from wp_users u where u.user_id = ?",
                new Object[]{userId},
                String.class);
        UserDetails user =
                userDetailsService.loadUserByUsername(username);
        String encodedPassword =
                passwordEncoder.encodePassword(password,
                        saltSource.getSalt(user));
        getJdbcTemplate().update("update wp_users set password = ? where user_upper_nm = ?",
                encodedPassword,
                username);
    }


}
