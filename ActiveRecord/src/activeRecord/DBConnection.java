package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private String password;
    private String serverName;
    private String portNumber;
    private String dbName;
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() {
        //initialisation des attributs pour la connexion a la bd
        this.dbName = "testpersonne";
        this.password = "";
        this.serverName = "localhost";
        this.portNumber = "3306";

        this.creerConnexion();

        DBConnection.dbConnection = this;
    }

    public static synchronized DBConnection getInstance(){
        if(DBConnection.dbConnection == null){
            return new DBConnection();
        }
        return DBConnection.dbConnection;
    }

    public Connection getConnexion(){
        return this.connection;
    }

    public void setNomDB(String nomDB){
        this.dbName = nomDB;
        this.creerConnexion();

    }

    private void creerConnexion(){
        try{
            //creation de la connection
            // chargement du driver jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");

            // creation de la connection
            Properties connectionProps = new Properties();
            String userName = "root";
            connectionProps.put("user", userName);
            connectionProps.put("password", this.password);
            String urlDB = "jdbc:mysql://" + this.serverName + ":";
            urlDB += portNumber + "/" + this.dbName;
            this.connection = DriverManager.getConnection(urlDB, connectionProps);
        } catch (SQLException e) {
            System.out.println("*** ERREUR SQL ***");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("*** ERREUR lors du chargement du driver ***");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("*** ERREUR inconnue... ***");
            e.printStackTrace();
        }
    }

}
