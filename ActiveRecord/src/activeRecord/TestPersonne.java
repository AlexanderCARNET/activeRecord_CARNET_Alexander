package activeRecord;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class TestPersonne {

    @Test
    public void test_findAll_OK() throws SQLException {
        Personne p1 = new Personne("Spielberg","Steven");
        p1.setId(1);
        Personne p2 = new Personne("Scott","Ridley");
        p2.setId(2);
        Personne p3 = new Personne("Kubrick","Stanley");
        p3.setId(3);
        Personne p4 = new Personne("Fincher","David");
        p4.setId(4);

        HashSet<Personne> personnes = Personne.findAll();

        assertEquals(4,personnes.size(), "Pas le bon nombre de personnes");

        assertTrue(personnes.contains(p1));
        assertTrue(personnes.contains(p2));
        assertTrue(personnes.contains(p3));
        assertTrue(personnes.contains(p4));
    }

    @Test
    public void test_findById_OK() throws SQLException {
        Personne p1 = new Personne("Spielberg","Steven");
        p1.setId(1);

        Personne p = Personne.findById(1);

        assertEquals(p1,p);
    }

    @Test
    public void test_findById_ID_non_existant() throws SQLException {
        Personne p = Personne.findById(8);
        assertNull(p);
    }

    @Test
    public void test_findByName() throws SQLException {
        Personne p1 = new Personne("Spielberg","Steven");
        p1.setId(1);

        HashSet<Personne> personnes = Personne.findByName("Spielberg");
        assertEquals(1,personnes.size(), "Pas le bon nombre de personnes");
        assertTrue(personnes.contains(p1));
    }

}