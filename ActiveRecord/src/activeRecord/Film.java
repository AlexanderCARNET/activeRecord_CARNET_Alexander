package activeRecord;

import java.sql.*;

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


}
