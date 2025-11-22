package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static HashSet<Personne> findAll() throws SQLException {
        HashSet<Personne> personnes = new  HashSet<Personne>();

        //recuperation de la connexion a la bdd
        Connection connect = DBConnection.getInstance().getConnexion();

        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            Personne p = new Personne(nom,prenom);
            p.setId(id);
            personnes.add(p);
        }
        return personnes;
    }

    public static Personne findById(int id) throws SQLException {
        Personne res = null;

        //recuperation de la connexion a la bdd
        Connection connect = DBConnection.getInstance().getConnexion();

        String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");

            res = new Personne(nom,prenom);
            res.setId(id);
        }
        return res;
    }

    private void setId(int id) {
        this.id = id;
    }

}
