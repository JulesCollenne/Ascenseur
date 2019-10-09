import ControleCommande.Moniteur;
import GUI.Fenetre;

public class main {
    public static void main(String[] args){

        Moniteur moniteur = new Moniteur();

        Fenetre fenetre = new Fenetre(moniteur);
    }
}
