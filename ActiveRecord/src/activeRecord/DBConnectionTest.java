package activeRecord;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
class DBConnectionTest {

    @Test
    /**
     * test que le Singleton retourne toujours le meme objet Connection
     */
    public void test_creation_unique_connexion(){
        //recuperation de différentes instances de Connection
        DBConnection instance = DBConnection.getInstance();
        Connection connection1 = instance.getConnexion();
        Connection connection2 = instance.getConnexion();

        DBConnection instance2 = DBConnection.getInstance();
        Connection connection3 = instance2.getConnexion();

        //test que les connexions sont bien les memes
        assertEquals(connection1,connection2);
        assertEquals(connection1,connection3);

    }

    @Test
    /**
     * test que l'objet connection donné par DBConnection est du type java.sql.Connection
     */
    public void test_bon_type_Connection(){
        DBConnection instance = DBConnection.getInstance();
        Connection connection = instance.getConnexion();
        assertInstanceOf(java.sql.Connection.class,connection);
    }

    @Test
    /**
     * Test que le changement de nom de base données fonctionne correctement
     */
    public void test_changement_BD() throws SQLException {
        DBConnection instance = DBConnection.getInstance();
        instance.setNomDB("dbtestqdev");
        Connection connection = instance.getConnexion();

        String nomBDConnexion = connection.getCatalog();

        assertEquals("dbtestqdev", nomBDConnexion,"connexion a la mauvaise base de données");

    }
}