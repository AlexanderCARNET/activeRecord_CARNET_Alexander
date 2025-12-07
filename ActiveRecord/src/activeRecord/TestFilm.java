package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestFilm {

    private Film[] films = new Film [7];
    Personne p1;

    @BeforeEach
    void setUp() throws SQLException {
        Personne.createTable();
        Film.createTable();

        p1 = new Personne("Spielberg","Steven");
        Personne p2 = new Personne("Scott","Ridley");
        Personne p3 = new Personne("Kubrick","Stanley");
        Personne p4 = new Personne("Fincher","David");

        p1.save();
        p2.save();
        p3.save();
        p4.save();


        films[0] = new Film("Arche perdue", p1);
        films[1] = new Film("Alien", p2);
        films[2] = new Film("Temple Maudit", p1);
        films[3] = new Film("Blade Runner", p2);
        films[4] = new Film("Alien3", p4);
        films[5] = new Film("Fight Club", p4);
        films[6] = new Film("Orange Mecanique", p3);


        for(Film f : films) {
            f.save();
        }

    }

    @AfterEach
    void tearDown() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }

    @Test
    public void test_save_newSave() throws SQLException {
        Film f = new Film("nouveau",Personne.findById(1));

        f.save();

        assertEquals(8, f.getId(),"enregistrement echoué");
    }

    @Test
    public void test_save_update() throws SQLException {
        Film f = new Film("nouveau",Personne.findById(1));
        f.setId(1);

        f.save();

        Film filmSave = Film.findById(1);

        assertEquals(filmSave.getId(),f.getId(),"enregistrement echou");
        assertEquals("nouveau",filmSave.getTitre());
    }
    @Test
    public void test_save_personne_nonSave() throws SQLException {
        Personne p = new Personne("car","alex");
        Film f = new Film("testTitre", p);

        f.save();

        assertEquals(8,f.getId(),"enregistrement echou du film");
        assertEquals(5,p.getId(),"enregistrement echou de personne");
        assertEquals(5,f.getId_real(), "mauvais id_rea enregistré");
    }

    @Test
    public void test_delete() throws SQLException {
        Film f = new Film("Arche perdue", new Personne("Spielberg","Steven"));
        f.setId(1);

        f.delete();

        assertEquals(-1, f.getId(),"id non mis a jour dans l'instance Film supprimé");
        Film res = Film.findById(1);
        assertNull(res,"Ce tuple aurait du etre supprime de la bdd");
    }

    @Test
    public void test_findById_present_bdd() throws SQLException {

        Film f = Film.findById(2);

        assertEquals(2,f.getId());
    }

    @Test
    public void test_findById_innexistant_bdd() throws SQLException {

        Film f = Film.findById(19);

        assertNull(f);
    }

    @Test
    public void test_getRealisateur_OK() throws SQLException {
        Personne p = films[0].getRealisateur();

        assertEquals(1, p.getId());
    }


    @Test
    public void test_findByRealisateur_OK() throws SQLException {
        List<Film> res = Film.findByRealisateur(p1);

        assertEquals(2, res.size());
    }

    @Test
    public void test_findByRealisateur_realisateur_sans_id() throws SQLException {
        Personne p = new Personne("Scott","Ridley");


        assertThrows(RealisateurAbsentException.class, () -> Film.findByRealisateur(p));
    }

    @Test
    public void test_findByRealisateur_realistateur_null() throws SQLException {
        Personne p = null;
        List<Film> res = Film.findByRealisateur(p);
        assertEquals(0,res.size(), "Pas le bon nombre de films");

    }

}