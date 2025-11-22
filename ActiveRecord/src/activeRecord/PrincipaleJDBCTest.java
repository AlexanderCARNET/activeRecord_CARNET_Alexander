package activeRecord;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
class PrincipaleJDBCTest {

    @BeforeEach
    void setUp(){

    }

    @AfterEach
    void tearDown(){

    }

    @Test
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
}