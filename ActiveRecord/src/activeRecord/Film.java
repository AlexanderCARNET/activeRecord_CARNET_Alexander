package activeRecord;

import java.sql.SQLException;

public class Film {
    private String titre;
    private int id;
    private int id_real;

    public Film(String titre, Personne p) throws SQLException {
        this.titre = titre;
        this.id = -1;
        if(p.getId() == -1) {
            p.save();
        }
        this.id_real = p.getId();
    }

    private Film(String titre, int id, int id_real){
        this.titre = titre;
        this.id = id;
        this.id_real = id_real;
    }
}
