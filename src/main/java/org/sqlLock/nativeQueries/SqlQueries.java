package org.sqlLock.nativeQueries;


import java.util.Objects;
import java.util.regex.Pattern;

//Native SQL queries
// Even though the methods are public it is still safe because the parameters for
// execution of these queries are injected using
public final class SqlQueries {


    private final String tableName;

    private static final Pattern VALID_TABLE_NAME_PATTERN = Pattern.compile("[a-zA-Z0-9_]+");

    public SqlQueries(String tableName) {
        this.tableName = expectValidTableName(tableName);
    }

    private String expectValidTableName(String tableName) {
        Objects.requireNonNull(tableName);
        if (!VALID_TABLE_NAME_PATTERN.matcher(tableName).matches()) {
            throw new IllegalArgumentException(
                    "Expected table name consisting of a-z, A-Z, 0-9, _. Got: " + tableName);
        }
        return tableName;
    }

    public String createLocksTable() {

        return "CREATE TABLE " + tableName
                + "("
                + "  ID VARCHAR(512) NOT NULL,"
                + "  ACQUIRED_BY VARCHAR(512) NOT NULL,"
                + "  ACQUIRED_AT TIMESTAMP(3) NOT NULL,"
                + "  EXPIRES_AT TIMESTAMP(3),"
                + "  PRIMARY KEY (ID)"
                + ")";
    }

    public String checkTableExits() {
        return "SELECT 1 FROM " + tableName + " WHERE 1=2";
    }

    public String deleteAll() {
        return "DELETE FROM " + tableName + ";";
    }

    public String deleteAcquiredByIdAndOwnerId() {
        return "DELETE FROM " + tableName
                + " WHERE ID = ? AND ACQUIRED_BY = ? AND EXPIRES_AT > ?;";
    }

    public String deleteAcquiredById() {
        return "DELETE FROM " + tableName
                + " WHERE ID = ? AND EXPIRES_AT > ?;";
    }

    public String updateLockById() {
        return "UPDATE " + tableName
                + " SET ACQUIRED_BY = ?, ACQUIRED_AT = ?, EXPIRES_AT = ?"
                + " WHERE ID = ?";
    }

    public String updateAcquiredOrReleasedLock() {
        return "UPDATE " + tableName
                + " SET ACQUIRED_BY = ?, ACQUIRED_AT = ?, EXPIRES_AT = ?"
                + " WHERE ID = ? AND (ACQUIRED_BY = ? OR EXPIRES_AT <= ?)";
    }

    public String updateReleasedLock() {
        return "UPDATE " + tableName
                + " SET ACQUIRED_BY = ?, ACQUIRED_AT = ?, EXPIRES_AT = ?"
                + " WHERE ID = ? AND EXPIRES_AT <= ?";
    }

    public String insertLock() {
        return "INSERT INTO " + tableName
                + " (ID, ACQUIRED_BY, ACQUIRED_AT, EXPIRES_AT)"
                + " VALUES (?, ?, ?, ?)";
    }
}
