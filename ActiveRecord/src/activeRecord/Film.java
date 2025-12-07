package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static Film findById(int id) throws SQLException {
        Film res = null;

        //recuperation de la connexion a la bdd
        Connection connect = DBConnection.getInstance().getConnexion();

        String SQLPrep = "SELECT * FROM film WHERE id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String titre = rs.getString("titre");
            int id_rea = Integer.parseInt(rs.getString("id_rea"));

            res = new Film(titre,id,id_rea);
        }
        return res;
    }


    public Personne getRealisateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    public static void createTable() throws SQLException {

        DBConnection.getInstance().setNomDB("testpersonne");
        Connection connect = DBConnection.getInstance().getConnexion();


        String createString = "CREATE TABLE film ( "
                + "id INTEGER  AUTO_INCREMENT NOT NULL, " + "titre varchar(40) NOT NULL, "
                + "id_rea int(11) DEFAULT NULL, " + "PRIMARY KEY (id), FOREIGN KEY (id_rea) REFERENCES Personne(id))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        DBConnection.getInstance().setNomDB("testpersonne");
        Connection connect = DBConnection.getInstance().getConnexion();


        String drop = "DROP TABLE film";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    public void delete() throws SQLException {
        if(this.id != -1){
            Connection connect = DBConnection.getInstance().getConnexion();

            PreparedStatement prep = connect.prepareStatement("DELETE FROM film WHERE id=?");
            prep.setInt(1, this.id);
            prep.execute();
            this.id = -1;
        }
    }

    public void save() throws SQLException {
        if(this.id != -1){
            if(this.id == -1){
                this.saveNew();
            }else{
                this.update();
            }
        }else{
            throw new RealisateurAbsentException("pas de réalisateur");
        }

    }

    private void update() throws SQLException {
        if(this.id_real != -1)throw new RealisateurAbsentException("pas de realisateur");

        Connection connect = DBConnection.getInstance().getConnexion();

        // met a jour un film
        String SQLprep = "update film set titre=?, id_rea=? where id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLprep);
        prep1.setString(1, this.titre);
        prep1.setInt(2, this.id_real);
        prep1.setInt(3, this.id);
        prep1.execute();
    }

    private void saveNew() throws SQLException {
        if(this.id_real != -1)throw new RealisateurAbsentException("pas de realisateur");

        Connection connect = DBConnection.getInstance().getConnexion();

        // ajout de personne avec requete preparee
        String SQLPrep = "INSERT INTO film (titre, id_rea) VALUES (?,?);";
        PreparedStatement prep;
        //l'option RETURN_GENERATED_KEYS permet de recuperer l'id
        prep = connect.prepareStatement(SQLPrep,
                Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.executeUpdate();

        // recuperation de la derniere ligne ajoutée (auto increment)
        // recupere le nouvel id
        int autoInc = -1;
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            autoInc = rs.getInt(1);
        }
        this.id = autoInc;
    }

    public static List<Film> findByRealisateur(Personne p) throws SQLException {
        ArrayList<Film> films = new ArrayList<Film>();

        Connection connect = DBConnection.getInstance().getConnexion();
        PreparedStatement ps = connect.prepareStatement("select id, titre from film where id_rea = ?");
        ps.setInt(1, p.getId());
        ps.execute();

        ResultSet rs = ps.getResultSet();

        while(rs.next()){
            int film_id = rs.getInt("id");
            String titre = rs.getString("titre");
            Film f = new Film(titre,film_id,p.getId());
            films.add(f);
        }
        return films;
    }


    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public int getId_real() {
        return id_real;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Film){
            Film f = (Film) obj;
            return this.id == f.id;
        }
        return false;
    }
}
