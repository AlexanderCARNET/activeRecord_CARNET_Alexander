package activeRecord;

public class DBConnection {

    private final String userName = "root";
    private final String password = "";
    private final String serverName = "localhost";
    private final String portNumber = "3306";
    private String dbName;
    private static DBConnection dbConnection;

    private DBConnection() {
        this.dbName = "testpersonne";
        DBConnection.dbConnection = this;
    }

    public static synchronized DBConnection getConnection(){
        if(DBConnection.dbConnection == null){
            return new DBConnection();
        }
        return DBConnection.dbConnection;
    }
}
