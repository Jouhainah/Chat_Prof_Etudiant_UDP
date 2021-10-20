package model;

import java.net.InetAddress;

public class Etudiant {

    private String nom;
    private String login;
    private String niveau;
    private String etatConnexion;
    private InetAddress IPAddress;
    private int port;

    public Etudiant() {
    }

    public Etudiant(String nom, String login, String niveau, String etatConnexion, InetAddress IPAddress, int port) {
        this.nom = nom;
        this.login = login;
        this.niveau = niveau;
        this.etatConnexion = etatConnexion;
        this.IPAddress = IPAddress;
        this.port = port;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getEtatConnexion() {
        return etatConnexion;
    }

    public void setEtatConnexion(String etatConnexion) {
        this.etatConnexion = etatConnexion;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(InetAddress IPAddress) {
        this.IPAddress = IPAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
