package template.jdbc.version1;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tisong on 2/22/17.
 */
public abstract class JdbcTemplate {

    private DataSource dataSource;

    public final Object  execute(String sql) {
        Connection conn = null;
        Statement  stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            return executeWithStatement(stmt, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
        }
    }

    protected abstract Object executeWithStatement(Statement stmt, String sql);


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
}
