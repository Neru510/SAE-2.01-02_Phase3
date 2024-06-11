package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.mecanique.CouleurJoueur;

import java.util.Map;

public class CouleursJoueurs {
    public static Map<CouleurJoueur, String> couleursBackgroundJoueur = Map.of(
            CouleurJoueur.JAUNE, "#FED440",
            CouleurJoueur.ROUGE, "#e54b48",
            CouleurJoueur.BLEU, "#4093B6",
            CouleurJoueur.VERT, "#6ac95a"
    );

    public Map<CouleurJoueur, String> getCouleursBackgroundJoueur(){
        return couleursBackgroundJoueur;
    }

    public String getCouleur(CouleurJoueur couleursJoueurs){
        return couleursBackgroundJoueur.get(couleursJoueurs);
    }

    public String getCouleurAnglais(CouleurJoueur couleurJoueur){
        if (couleurJoueur == CouleurJoueur.JAUNE){
            return "yellow";
        }
        else if (couleurJoueur == CouleurJoueur.ROUGE){
            return "red";
        }
        else if (couleurJoueur == CouleurJoueur.BLEU){
            return "blue";
        }
        else {
            return "green";
        }
    }

}