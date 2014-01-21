package ru.nyrk.gisgmp.web.security;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oanyrkov
 * Date: 10.03.13
 * Time: 20:15
 */
public class MyJdbcDaoImpl extends JdbcUserDetailsManager {
    public static final String sqlUsersByUserName =

            "select user_id,\n" +              //1
                    "       user_nm,\n" +      //2
                    "       user_first_nm,\n" +  //3
                    "       user_next_nm,\n" +    //4
                    "       user_family_nm,\n" +   //5
                    "       email_nm,\n" +          //6
                    "       password,\n" +          //7
                    "       phone_nm,\n" +          //8
                    "       user_enabled_pr, \n" +  //9
                    "       user_upper_nm,  \n" + //10
                    "       salt   \n" +    //11
                    "  from wp_users u\n" +
                    " where u.user_upper_nm = upper(?)";
    public static final String sqlGroupAuthoritiesByUsernameQuery =
            "select distinct u.user_upper_nm, p.permission_nm\n" +
                    "  from wp_users u\n" +
                    " inner join wp_users_group ug\n" +
                    "    on ug.user_id = u.user_id\n" +
                    " inner join wp_group_permision gp\n" +
                    "    on gp.group_id = ug.group_id\n" +
                    " inner join wp_permission p\n" +
                    "    on p.permission_id = gp.permission_id\n" +
                    " where u.user_upper_nm = upper(?)";

    public MyJdbcDaoImpl() {
        super();
        setUsersByUsernameQuery(sqlUsersByUserName);

    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {

        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[]{username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(10);
                String password = rs.getString(7);
                boolean enabled = rs.getInt(9) == 1;
                Family family = new Family(rs.getString(3), rs.getString(4), rs.getString(5));
                String email = rs.getString(6);
                String phone = rs.getString(8);
                String salt = rs.getString(11);
                return new MyUserDetails(username, password, enabled,
                       true, true, true, AuthorityUtils.NO_AUTHORITIES, family, email, phone, salt);
            }

        });
    }

    @Override
    protected List<GrantedAuthority> loadGroupAuthorities(String username) {
        return getJdbcTemplate().query(sqlGroupAuthoritiesByUsernameQuery, new String[]{username}, new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String roleName = getRolePrefix() + rs.getString(2);
                return new SimpleGrantedAuthority(roleName);
            }
        });
    }

    @Override
    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        if (userFromUserQuery instanceof MyUserDetails) {
            return new MyUserDetails((MyUserDetails) userFromUserQuery, combinedAuthorities);
        } else
            return super.createUserDetails(username, userFromUserQuery, combinedAuthorities);
    }
}
