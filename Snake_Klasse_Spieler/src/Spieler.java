public class Spieler {

    private int spielernr;
    private String name;
    private int id;
    private String passwort;



    Spieler(int spielernr, String passwort, String name, int id){
        this.spielernr=spielernr;
        this.passwort=passwort;
        this.name=name;
        this.id=id;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getSpielernr() {
        return spielernr;
    }

    public void setSpielernr(int spielernr) {
        this.spielernr = spielernr;
    }
}
