import java.io.InputStream;


public class Jeu {
    private Niveau map;
    private LecteurNiveaux lecteurNiveaux;

    Jeu(InputStream inputStream){
        lecteurNiveaux = new LecteurNiveaux(inputStream);
    }

    Niveau niveau(){
        return map;
    }

    Boolean prochainNiveau(){
        map = lecteurNiveaux.lisProchainNiveau();
        return map != null;
    }

}
