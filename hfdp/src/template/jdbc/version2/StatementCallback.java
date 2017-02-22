package template.jdbc.version2;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tisong on 2/22/17.
 */
public interface StatementCallback {

    Object doWithStatement(Statement stmt) throws SQLException;
}
