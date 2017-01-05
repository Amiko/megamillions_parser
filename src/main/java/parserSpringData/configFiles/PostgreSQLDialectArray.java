package parserSpringData.configFiles;

import java.sql.Types;

/**
 * Created by amiko on 30-Dec-16.
 */
public class PostgreSQLDialectArray extends org.hibernate.dialect.PostgreSQL9Dialect {

    public PostgreSQLDialectArray() {

        super();
        registerColumnType(Types.ARRAY, "integer[]");
    }
}
