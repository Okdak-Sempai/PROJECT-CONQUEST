package Conquest.Menus;

import Conquest.Exception.ColonInexistantException;
import Conquest.Exception.RessourceDejaExistatneException;
import Conquest.Struct.Colon;
import Conquest.Struct.Colonie;
import Conquest.Expedition;
import Conquest.Struct.Ressource;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe Menu
 * <p>
 * Cette classe sert de Menu pour lancer le code sans argument.
 * <p>
 * Il nous permet de creer des colonies et leur affecter des reesources.
 * <p>
 * On ajoute d'abbord les colons puis les ressources disponibles.
 * <p>
 * 1) Ajouter une relation entre deux colons
 * 2) Ajouter les préférences d’un colon
 * 3) Fin
 * <p>
 * Une fois les relation ajouté et les preferences faites, si tout est conforme un solution est proposé et un nouveau menu est accessible.
 * <p>
 *  1) Échanger les ressources de deux colons
 *  2) Afficher le nombre de colons jaloux(Cout)
 *  3) Fin
 *
 * @see Conquest.Main#main(String[])
 *
 * @author Devasenaradjounayagar Damien
 *
 * @version 1.0
 *
 */
public class Menu
{
    /**
     * L'Expedition que le Menu exploitera.
     *
     * @see Menu#Menu(Expedition)
     * @see Menu#getExpedition()
     * @see Menu#afficherMenuCreationColonie()
     * @see Menu#afficherMenuCreationRessources()
     * @see Menu#afficherMenuConfiguration(int)
     * @see Menu#afficherMenuAffectation(int, int)
     *
     */
    private final Expedition expedition;

    /**
     * Le Scanner qu'utilisera le menu.
     *
     * @see Menu#Menu(Expedition)
     * @see Menu#getScanner()
     * @see Menu#afficherMenuCreationColonie()
     * @see Menu#afficherMenuCreationRessources()
     * @see Menu#afficherMenuConfiguration(int)
     * @see Menu#afficherMenuAffectation(int, int)
     *
     */
    private final Scanner scanner;
    /**
     * Nombre constant de Colons&Ressources.
     *
     * @see Menu#Menu(Expedition)
     * @see Menu#afficherMenuCreationColonie()
     * @see Menu#afficherMenuCreationRessources()
     * @see Menu#afficherMenuConfiguration(int)
     *
     */
    private int n;

    /**
     * Constructeur de Menu.
     *
     * @param expedition
     * L'expedition a suivre.
     *
     * @see MenuLoadTXT
     * @see Conquest.Main#main(String[])
     *
     */
    public Menu(Expedition expedition)
    {
        this.scanner = new Scanner(System.in);
        this.expedition=expedition;
        this.n = 0;
    }

    /**
     * Recupere l'Expedition
     *
     * @return
     * Retourne l'expedition.
     *
     * @see MenuLoadTXT#afficherMenuAffectation(int, int)
     */
    public Expedition getExpedition()
    {
        return expedition;
    }

    /**
     * Recupere le Scanner.
     *
     * @return
     * Retourne le Scanner.
     *
     * @see MenuLoadTXT#afficherMenuAffectation(int, int)
     *
     */
    public Scanner getScanner()
    {
        return scanner;
    }

    /**
     * Menu de creation d'une Colonie.
     * <p>
     * On recupere le nombre de colons, qui par la meme occasion sera le nombre de ressources a allouer pour plus tard, puis on les crée.
     *
     *
     * @return
     * L'index de la colonie crée dans l'Expedition.
     *
     * @see Conquest.Main#main(String[])
     *
     */
    public int afficherMenuCreationColonie()
    {
        int colonieIndex = expedition.createColonie();
        Colonie colonie = expedition.getColonie(colonieIndex);

        System.out.println("Configuration de la colonie :");

        //Creation de Colonie
        while (true)
        {
            try
            {
                System.out.print("Combien de colons voulez-vous ajouter ? ");
                n = Integer.parseInt(scanner.nextLine());
                if (n <= 0)
                {
                    System.out.println("Veuillez entrer un nombre positif.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
            }
        }
        for (int i = 0; i < n; i++)
        {
            System.out.print("Entrez le nom du colon " + (i + 1) + " : ");
            String nomColon = scanner.nextLine();
            Colon colon = new Colon(nomColon);
            try
            {
                colonie.ajouterColon(colon);
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
                i--;
            }
        }

        return colonieIndex;
    }

    /**
     * Menu de creation d'une Colonie.
     * <p>
     * On recupere le nom des ressources.
     *
     * @return
     * L'index des Ressources crée dans l'Expedition.
     *
     * @see Conquest.Main#main(String[])
     *
     */
    public int afficherMenuCreationRessources()
    {
        int ressourceIndex = expedition.createRessource();
        Ressource ressources = expedition.getRessource(ressourceIndex);

        System.out.println("Configuration des ressources :");
            for(int i=0; i<n; i++)
            {
                System.out.print("Entrez le nom de la ressource " + (i + 1) + " : ");
                String ressource = scanner.nextLine();

                if(ressources.contains(ressource)){
                    System.out.println("Cette ressource "+ ressource +"a deja ete ajoutée !");
                    i--;
                    continue;
                }

                ressources.addRessource(ressource);

            }
            return ressourceIndex;
    }

    /**
     * Menu de Configuration de la colonie.
     * <p>
     *     1/) Ajouter une relation entre deux colons (ex : A B)
     *     2/) Ajouter les préférences d’un colon (ex : A 1 2 3)
     *     3/) Fin
     * L'index des Ressources crée dans l'Expedition.
     *
     * @param colonieIndex
     * Index de la colonie de l'Expedition à utiliser.
     *
     * @see Conquest.Main#main(String[])
     *
     */


     public void afficherMenuConfiguration(int colonieIndex) {
        Colonie colonie = expedition.getColonie(colonieIndex);
        Ressource ressources = expedition.getRessource(0); // Pour vérifier les ressources valides
    
        // Manipulation de Colonie
        boolean enCours = true;
        while (enCours) {
            // Afficher l'état actuel des préférences
            System.out.println("\nÉtat actuel des préférences :");
            for (Colon c : colonie.getColons()) {
                System.out.println(c.getNom() + " : " + 
                    (c.getPreferences().isEmpty() ? "aucune préférence" : 
                    c.getPreferences()) + " (" + c.getPreferences().size() + "/" + n + ")");
            }
    
            System.out.println("\n1) Ajouter une relation entre deux colons");
            System.out.println("2) Ajouter les préférences d'un colon");
            System.out.println("3) Fin");
            System.out.println();
    
            int choix;
            while (true) {
                System.out.print("Veuillez entrer votre choix : ");
                try {
                    choix = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                }
            }
    
            switch (choix) {
                case 1:
                    System.out.print("Entrer les noms des deux colons (ex : A B) : ");
                    String colon1 = scanner.next();
                    String colon2 = scanner.next();
                    scanner.nextLine();
    
                    try {
                        Colon c1 = colonie.getColonObjet(colon1);
                        Colon c2 = colonie.getColonObjet(colon2);
                        ArrayList<Colon> colons = new ArrayList<>();
                        colons.add(c2);
                        c1.ajouterRelations(colons);
                        System.out.println("Relation ajoutée avec succès entre " + colon1 + " et " + colon2);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
    
                case 2:
                    System.out.print("Entrer le nom du colon et ses préférences (ex : A 1 2 3 4) : ");
                    String input = scanner.nextLine();
                    String[] parts = input.split(" ");
                
                    // Vérifier que nous avons le bon nombre de préférences 
                    if (parts.length != n + 1) { // +1 car la première partie est le nom du colon
                        System.out.println("Erreur : il faut exactement " + n + " préférences");
                        break;
                    }
                
                    try {
                        // Récupérer le colon
                        Colon colon = colonie.getColonObjet(parts[0]);
                
                        // Ajouter directement les préférences
                        for (int i = 1; i < parts.length; i++) {
                            colon.addPreference(parts[i]);
                        }
                
                        System.out.println("Préférences ajoutées pour " + parts[0] + " : " + colon.getPreferences());
                
                    } catch (ColonInexistantException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    boolean toutesPreferencesCompletes = true;
                    for (Colon c : colonie.getColons()) {
                        if (c.getPreferences().size() != n) {
                            System.out.println("Attention ! " + c.getNom() + " n'a que " + c.getPreferences().size() + "/" + n + " préférences");
                            toutesPreferencesCompletes = false;
                        }
                    }
                    enCours = !toutesPreferencesCompletes;
                    if (enCours) {
                        System.out.println("\nVous devez compléter toutes les préférences avant de continuer.");
                    }
                    break;
    
                default:
                    System.out.println("Option invalide. Réessayez.");
            }
        }
    }



    /*

    CETTE METHODE EST LA METHODE DE BASE, ELLE EST REMPLACEE PAR LA METHODE CI-DESSUS
    le probleme de cette methode est qu'elle ne verifie pas si le nombre de preference est correcte


    public void afficherMenuConfiguration(int colonieIndex)
    {
        Colonie colonie = expedition.getColonie(colonieIndex);

        //Manipulation de Colonie
        boolean enCours = true;
        while (enCours)
        {
            System.out.println();
            System.out.println("1) Ajouter une relation entre deux colons");
            System.out.println("2) Ajouter les préférences d’un colon");
            System.out.println("3) Fin");
            System.out.println();

            int choix;
            while (true) {
                System.out.print("Veuillez entrer votre choix : ");
                try {
                    choix = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e)
                {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                }
            }

            switch (choix)
            {
                case 1:
                    System.out.print("Entrer les noms des deux colons (ex : A B) : ");
                    String colon1 = scanner.next();
                    String colon2 = scanner.next();
                    scanner.nextLine();

                    Colon c1 = null;
                    Colon c2 = null;
                    ArrayList<Colon> colons = null;
                    try
                    {
                        c1 = colonie.getColonObjet(colon1);
                        c2 = colonie.getColonObjet(colon2);
                        colons = new ArrayList<>();
                        colons.add(c2);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                    try
                    {
                        c1.ajouterRelations(colons);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                    break;

                    
                case 2:
                    System.out.print("Entrer le nom du colon et ses préférences (ex : A 1 2 3) : ");
                    String input = scanner.nextLine();
                    String[] parts = input.split(" ");
                    //On verifie si le colon existe
                    
                    String nom = parts[0];
                    Colon colon;
                    try
                    {
                        colon = colonie.getColonObjet(nom);
                    } catch (ColonInexistantException e)
                    {
                        System.out.println(e.getMessage());
                        break;
                    }

                    for (int i = 1; i < parts.length; i++)
                    {
                        try
                        {
                            colonie.getColonObjet(parts[i]);
                        }
                        catch (ColonInexistantException e)
                        {
                            System.out.println(e.getMessage());
                            break;
                        }
                        colon.addPreference(parts[i]);
                    }
                    break;

                case 3:
                    enCours = false;
                    boolean willbreak = false;
                    for(Colon c : colonie.getColons())
                    {
                        if(c.getPreferences().size()!=n)
                        {
                            System.out.println("Attention ! "+c.getNom()+" n'a que"+c.getPreferences().size()+"/"+n);
                            enCours = true;
                            willbreak = true;
                        }
                    }
                    if(willbreak)
                    {
                        break;
                    }
                    break;
                default:
                    System.out.println("Option invalide. Réessayez.");
            }
        }

    }

    /**
     * Menu de Resultat et de changement d'affectations des Resources.
     *
     * 1/) Échanger les ressources de deux colons
     * 2/) Afficher le nombre de colons jaloux
     * 3/) Fin
     *
     * @param colonieIndex
     * Index de la Colonie a utiliser dans l'Expedition.
     *
     * @param ressourceIndex
     * Index des Ressources a utiliser dans l'Expedition.
     *
     * @throws Exception
     * Si il y a un probleme avec le calcul du cout ou l'algorithme de try,
     *
     * @see Conquest.Main#main(String[])
     *
     */
    public void afficherMenuAffectation(int colonieIndex, int ressourceIndex) throws Exception
    {
        Colonie colonie = expedition.getColonie(colonieIndex);
        Ressource ressources = expedition.getRessource(ressourceIndex);

        // expedition.algoBestPerfSUR(100,colonieIndex,ressources);
        expedition.meuilleurResultat(colonieIndex, ressources);

        System.out.println("Gestion des affectations de ressources :");
        System.out.println(colonie.toString());
        System.out.println("Le cout est "+colonie.cout());

        boolean enCours = true;
        while (enCours) {
            System.out.println();
            System.out.println("1/) Échanger les ressources de deux colons");
            System.out.println("2/) Afficher le nombre de colons jaloux");
            System.out.println("3/) Fin");
            System.out.println();

            int choix;
            while (true)
            {
                System.out.print("Veuillez entrer votre choix : ");
                try
                {
                    choix = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e)
                {
                    System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                }
            }

            try {
                switch (choix)
                {
                    case 1:
                        System.out.print("Entrer les noms des deux colons à échanger (ex : A B): ");
                        String nom1 = scanner.next();
                        String nom2 = scanner.next();
                        scanner.nextLine();

                        Colon c1 = null;
                        Colon c2 = null;
                        ArrayList<Colon> colons = null;
                        try
                        {
                            c1 = colonie.getColonObjet(nom1);
                            c2 = colonie.getColonObjet(nom2);
                            colonie.echangerRessources(c1, c2);
                        }catch (Exception e)
                        {
                            System.out.println(e.getMessage());
                        }
                        colonie.afficherAffectations();
                        break;
                    case 2:
                        int cout = colonie.cout();
                        System.out.println("\nNombre de colons jaloux : " + cout+"[cout]");
                        colonie.afficherAffectations();
                        break;
                    case 3:
                        enCours = false;
                        break;
                    default:
                        System.out.println("Option invalide. Réessayez.");
                }
             } catch (Exception e) {
                System.out.println("Erreur inattendue : " + e.getMessage());
            }
        }
    }
}