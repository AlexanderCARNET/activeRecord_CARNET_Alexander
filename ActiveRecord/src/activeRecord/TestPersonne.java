package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestPersonne {

    @BeforeEach
    void setUp() throws SQLException {
        Personne.createTable();
        Personne p1 = new Personne("Spielberg","Steven");
        Personne p2 = new Personne("Scott","Ridley");
        Personne p3 = new Personne("Kubrick","Stanley");
        Personne p4 = new Personne("Fincher","David");
        p1.save();
        p2.save();
        p3.save();
        p4.save();
    }

    @AfterEach
    void tearDown() throws SQLException {
        Personne.deleteTable();
    }

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

        List<Personne> personnes = Personne.findAll();

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

        ArrayList<Personne> personnes = Personne.findByName("Spielberg");
        assertEquals(1,personnes.size(), "Pas le bon nombre de personnes");
        assertTrue(personnes.contains(p1));
    }

    @Test
    public void test_delete_OK() throws SQLException {
        Personne p1 = new Personne("Spielberg","Steven");
        p1.setId(1);

        p1.delete();

        assertEquals(-1, p1.getId(),"id non mis a jour dans l'instance Personne supprimé");
        Personne res = Personne.findById(1);
        assertNull(res,"Ce tuple aurait du etre supprime de la bdd");
    }

    @Test
    public void test_save_non_existant() throws SQLException {
        Personne p1 = new Personne("car","alex");

        p1.save();
        Personne res = Personne.findById(5);

        assertEquals(p1,res, "enregistrement echoué");
    }

    @Test
    public void test_save_deja_existant() throws SQLException {
        Personne p1 = new Personne("car","alex");
        p1.setId(1);

        p1.save();
        Personne res = Personne.findById(1);

        assertEquals(p1,res, "mise a jour de la personne dans la bdd a échoué");
    }

}