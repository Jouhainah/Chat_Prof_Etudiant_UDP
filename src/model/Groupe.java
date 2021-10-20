package model;

import java.util.ArrayList;
import java.util.List;

public class Groupe {

    String titre ;
    List<Etudiant> etudiantList = new ArrayList<Etudiant>();
    List<Message> messageList = new ArrayList<Message>();

    public Groupe() {
    }

    public Groupe(String titre) {
        this.titre = titre;
    }

    public Groupe(String titre, List<Etudiant> etudiantList, List<Message> messageList) {
        this.titre = titre;
        this.etudiantList = etudiantList;
        this.messageList = messageList;
    }
}
