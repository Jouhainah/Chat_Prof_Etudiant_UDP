package model;

public class Message {

    private Etudiant etudiantSender;
    private String contenu;
    private Etudiant etudiantReciver;

    public Message() {
    }

    public Message(Etudiant etudiantSender, String contenu, Etudiant etudiantReciver) {
        this.etudiantSender = etudiantSender;
        this.contenu = contenu;
        this.etudiantReciver = etudiantReciver;
    }

    public Etudiant getEtudiantSender() {
        return etudiantSender;
    }

    public void setEtudiantSender(Etudiant etudiantSender) {
        this.etudiantSender = etudiantSender;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Etudiant getEtudiantReciver() {
        return etudiantReciver;
    }

    public void setEtudiantReciver(Etudiant etudiantReciver) {
        this.etudiantReciver = etudiantReciver;
    }
}
