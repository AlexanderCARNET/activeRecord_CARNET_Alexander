package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

class TestFilm {
    @BeforeEach
    void setUp() throws SQLException {
        Personne.createTable();
        Film.createTable();

        Personne p1 = new Personne("Spielberg","Steven");
        Personne p2 = new Personne("Scott","Ridley");
        Personne p3 = new Personne("Kubrick","Stanley");
        Personne p4 = new Personne("Fincher","David");

        Film[] films = new Film [7];

        films[0] = new Film("Arche perdue", p1);
        films[1] = new Film("Alien", p2);
        films[2] = new Film("Temple Maudit", p1);
        films[3] = new Film("Blade Runner", p2);
        films[4] = new Film("Alien3", p4);
        films[5] = new Film("Fight Club", p4);
        films[6] = new Film("Orange Mecanique", p3);


        p1.save();
        p2.save();
        p3.save();
        p4.save();

        for(Film f : films) {
            f.save();
        }

    }

    @AfterEach
    void tearDown() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }
}