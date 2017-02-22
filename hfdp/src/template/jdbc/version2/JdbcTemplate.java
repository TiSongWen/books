package template.jdbc.version2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 增加回调函数，可避免对抽象模板的再子类化;
 * 把子类应该实现的模板方法放到了回调接口的方法中
 */
public class JdbcTemplate {

    private DataSource dataSource;

    public final Object  execute(StatementCallback callback) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            return callback.doWithStatement(stmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
        }
    }


    private final DataAccessException translateSQLException(SQLException e) {
        DataAccessException dataAccessException = null;

        // transfor SQLException to DataAccessException

        return dataAccessException;
    }

    private final void closeStatement(Statement stmt) {
        // 关闭 Statement
    }

    private final void closeConnection(Connection conn) {
        // 关闭 Connection
    }

    /**
     * 对SQL异常的统一封装
     */
    class DataAccessException extends Exception {

    }


    public static void main(String[] args) {
        String sql = "update something";

        StatementCallback callback = new StatementCallback() {
            @Override
            public Object doWithStatement(Statement stmt) throws SQLException {
                return new Integer(stmt.executeUpdate(sql));
            }
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.execute(callback);
    }
}

