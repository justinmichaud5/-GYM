/**
 * Classe abstraite contenant les informations communes entre les membres et les professionnels
 */
public abstract class Usager {
    private String nom;
    private String courriel;
    private String adresse;
    private String ville;
    private String province;
    private String codePostal;

    /**
     * Constructeur usager
     *
     * @param nom nom de l'usager
     * @param courriel courriel de l'usager
     * @param adresse adresse de l'usager
     * @param ville ville de l'usager
     * @param province province de l'usager
     * @param codePostal code postale de l'usager
     */
    public Usager (String nom, String courriel, String adresse, String ville, String province, String codePostal) {
        this.nom = nom;
        this.courriel = courriel;
        this.adresse = adresse;
        this.ville = ville;
        this.province = province;
        this.codePostal = codePostal;
    }

    // Getters
    public String getCourriel() {
        return courriel;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getVille() {
        return ville;
    }

    public String getProvince() {
        return province;
    }

    public String getCodePostal() {
        return codePostal;
    }

    //Setters
    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
}
