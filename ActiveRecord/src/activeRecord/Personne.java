package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * recherche toute les personnes dans la bdd
     * @return un HashSet contenant toutes les personnes de la base de donnees
     * @throws SQLException
     */
    public static ArrayList<Personne> findAll() throws SQLException {
        ArrayList<Personne> personnes = new ArrayList<Personne>();

        //recuperation de la connexion a la bdd
        Connection connect = DBConnection.getInstance().getConnexion();

        String SQLPrep = "SELECT * FROM personne;";
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

    /**
     *
     * @param id id de la personne a recuperer
     * @return null si l'id n'existe pas dans la base de donnees, sinon la personne trouvee
     * @throws SQLException
     */
    public static Personne findById(int id) throws SQLException {
        Personne res = null;

        //recuperation de la connexion a la bdd
        Connection connect = DBConnection.getInstance().getConnexion();

        String SQLPrep = "SELECT * FROM personne WHERE id=?;";
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

    /**
     *
     * @param nom nom des personnes recherchees
     * @return un hashSet de personnes qui ont le nom demandee
     * @throws SQLException
     */
    public static ArrayList<Personne> findByName(String nom) throws SQLException {
        ArrayList<Personne> personnes = new  ArrayList<Personne>();
        Connection connect = DBConnection.getInstance().getConnexion();

        String SQLPrep = "SELECT * FROM personne WHERE nom=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setString(1, nom);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            int id = rs.getInt("id");
            String prenom = rs.getString("prenom");

            Personne p = new Personne(nom,prenom);
            p.setId(id);
            personnes.add(p);
        }

        return personnes;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Personne){
            Personne p = (Personne)obj;
            return this.id == p.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public static void createTable() throws SQLException {
        DBConnection.getInstance().setNomDB("testpersonne");
        Connection connect = DBConnection.getInstance().getConnexion();


        String createString = "CREATE TABLE personne ( "
                + "id INTEGER  AUTO_INCREMENT, " + "nom varchar(40) NOT NULL, "
                + "prenom varchar(40) NOT NULL, " + "PRIMARY KEY (id))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {

        DBConnection.getInstance().setNomDB("testpersonne");
        Connection connect = DBConnection.getInstance().getConnexion();


        String drop = "DROP TABLE personne";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    public void delete() throws SQLException {

        if(this.id != -1){
            Connection connect = DBConnection.getInstance().getConnexion();

            PreparedStatement prep = connect.prepareStatement("DELETE FROM personne WHERE id=?");
            prep.setInt(1, this.id);
            prep.execute();
            this.id = -1;
        }
    }

    public void save() throws SQLException {
        if(this.id == -1){
            this.saveNew();
        }else{
            this.update();
        }
    }

    private void saveNew() throws SQLException {
        Connection connect = DBConnection.getInstance().getConnexion();

        // ajout de personne avec requete preparee
        String SQLPrep = "INSERT INTO personne (nom, prenom) VALUES (?,?);";
        PreparedStatement prep;
        //l'option RETURN_GENERATED_KEYS permet de recuperer l'id
        prep = connect.prepareStatement(SQLPrep,
                Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
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

    private void update() throws SQLException {
        Connection connect = DBConnection.getInstance().getConnexion();

        // met a jour personne
        String SQLprep = "update personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLprep);
        prep1.setString(1, this.nom);
        prep1.setString(2, this.prenom);
        prep1.setInt(3, this.id);
        prep1.execute();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
