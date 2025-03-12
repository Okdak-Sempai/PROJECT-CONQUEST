package Conquest;

import Conquest.Menus.Menu;
import Conquest.Menus.MenuLoadTXT;
import Conquest.Struct.Colon;
import Conquest.Struct.Colonie;
import Conquest.Struct.Ressource;
import Conquest.Exception.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe Expedition
 * <p>
 * Une expedition sert a gerer plusieurs Colonies et à faire des actions sur elles. Comme la creation ou la sauvegarde.
 *
 * @see Expedition#Expedition()
 * @see Expedition#Expedition(List)
 * @see Expedition#getColonie(int)
 * @see Expedition#createColonie()
 * @see Expedition#algoFavoriteFirst(int, Ressource)
 * @see Expedition#algoBestPerfSUR(int, int, Ressource)
 * @see Expedition#affectation(int)
 * @see Expedition#Importation(String)
 * @see Expedition#save(String, int)
 * @see Menu
 * @see Menu#Menu(Expedition)
 * @see Menu#getExpedition()
 * @see MenuLoadTXT#afficherMenuConfigurationLoadTXT()
 * @see Main#main(String[])
 *
 * @author Devasenaradjounayagar Damien
 *
 * @version 1.0
 *
 */
public class Expedition
{
    /**
     * Liste des Colonies
     *
     * @see Expedition#Expedition()
     * @see Expedition#Expedition(List)
     * @see Expedition#getColonie(int)
     * @see Expedition#createColonie()
     * @see Expedition#algoFavoriteFirst(int, Ressource)
     * @see Expedition#algoBestPerfSUR(int, int, Ressource)
     * @see Expedition#affectation(int)
     * @see Expedition#Importation(String)
     * @see Expedition#save(String, int)
     *
     */
    private final List<Colonie> colonies;

    /**
     * Liste des Ressources
     *
     * @see Expedition#Expedition()
     * @see Expedition#Expedition(List)
     * @see Expedition#createRessource()
     * @see Expedition#algoFavoriteFirst(int, Ressource)
     * @see Expedition#algoBestPerfSUR(int, int, Ressource)
     * @see Expedition#affectation(int)
     * @see Expedition#Importation(String)
     *
     */
    private final List<Ressource> ressources;

    /**
     * Constructeur d'Expedition.
     *
     * @see Main#main(String[])
     *
     */
    Expedition()
    {
        colonies = new ArrayList<Colonie>();
        ressources = new ArrayList<>();
    }

    /**
     * Constructeur d'Expedition via une liste de Colonie.
     *
     * @param colonies
     * Liste de Colonie.
     *
     */
    Expedition(List<Colonie> colonies)
    {
        this.colonies=colonies;
        ressources = new ArrayList<>();
    }

    /**
     * Retourne une colonie a l'index donné.
     *
     * @param index
     * Indexe de la colonie.
     *
     * @return
     * Retourne une colonie a l'index donné.
     *
     * @see Menu#afficherMenuCreationColonie()
     * @see Menu#afficherMenuConfiguration(int)
     * @see Menu#afficherMenuAffectation(int, int)
     * @see Main#main(String[])
     *
     */
    public Colonie getColonie(int index)
    {
        return colonies.get(index);
    }
    /**
     * Cree et ajoute une nouvelle Colonie a l'expedition.
     *
     * @return
     * L'indice la Colonie.
     *
     * @see Menu#afficherMenuCreationColonie()
     *
     */
    public int createColonie()
    {
        Colonie colonie = new Colonie();
        colonies.add(colonie);
        return colonies.size()-1;
    }

    /**
     * Cree et ajoute une nouvelle Ressource a l'expedition.
     *
     * @return
     * L'indice la Ressource.
     *
     * @see Menu#afficherMenuCreationRessources()
     *
     */
    public int createRessource()
    {
        Ressource ressource = new Ressource();
        ressources.add(ressource);
        return ressources.size()-1;
    }

    /**
     * Retourne les ressources associées a l'index donné.
     *
     * @param index
     * Index des Ressources.
     *
     * @return
     * Retourne les Ressources.
     *
     * @see Main#main(String[])
     * @see Menu#afficherMenuCreationRessources()
     * @see Menu#afficherMenuAffectation(int, int)
     *
     */
    public Ressource getRessource(int index)
    {
        return ressources.get(index);
    }

    /**
     * Algorithme d'affectation qui donne dans un ordre strict les ressources favorites de chaque colon.
     * Algorithme lineaire.
     *
     * @param colonieN
     * Index de la colonie.
     *
     * @param r
     * Ressources a affecter.
     *
     * @return
     * Le coute de l'affectation.
     *
     * @throws Exception
     * Nombre de colonies different du nombrede ressources.
     *
     * @see Expedition#algoBestPerfSUPER(int, Ressource) 
     *
     */
    public int algoFavoriteFirst(int colonieN, Ressource r) throws Exception //TODO UNIT
    {
        Colonie colonie = colonies.get(colonieN);
        if (colonie.getColons().size() != r.size())
        {
            throw new NbColonDIfferentNbRessourcesException("Not enough colonies for the ressources");        }
        for(Colon colon : colonie.getColons())
        {
            for(int i=0;i<colon.getPreferences().size();i++)
            {

                //Verifie si la ressource i du colon est dans disponible
                if(r.contains(colon.getPreferenceAT(i)))
                {
                    colon.affectationRessource(colon.getPreferenceAT(i));
                    r.removeRessource(colon.getPreferenceAT(i));
                    break;
                }
            }
        }

        while(r.Depile()!=null);
        while(r.Repile()!=null);
        return colonie.cout();
    }

    /**
     * Algorithme d'affectation naif, il utilise le resultat d'une affectation par preference et fait des substitutions d'objet pour optimiser le cout.
     * Cet algorithme ne garanti pas un meilleur cout, mais il essaie avec un nombre de tentatives donné.
     *
     * @param maxTentatives
     * Nombre de tentatives.
     *
     * @param colonieN
     * Index de la colonie.
     *
     * @param r
     * Ressrouces a affecter.
     *
     * @return
     * retourne le cout de l'affectation.
     *
     * @throws Exception
     * Erreur lors de l'echange ou le calcul du cout.
     *
     * @see Expedition#affectation(int)
     * @see Menu#afficherMenuAffectation(int, int)
     *
     */
    public int algoBestPerfSUR(int maxTentatives,int colonieN, Ressource r) throws Exception

    {
        Colonie colonie = colonies.get(colonieN);
        //int cout1=Integer.MAX_VALUE;
        int cout1;
        for(Colon colon: colonie.getColons())
        {
            if(colon.getRessource().isEmpty())
            {
                algoFavoriteFirst(colonieN, r);
                break;
            }
        }
        cout1 = colonie.cout();
        int cout2;
        int i=0;

        while(i<maxTentatives && cout1!=0)
        {
            Random generator = new Random();
            //Genere un nombre aleatoire entre 0 et le nombre de colons
            int randomIndex1 = generator.nextInt(colonie.getColons().size());
            int randomIndex2;
            do {
                randomIndex2 = generator.nextInt(colonie.getColons().size());
            } while (randomIndex2 == randomIndex1);

            Colon colon1 = colonie.getColons().get(randomIndex1);
            Colon colon2 = colonie.getColons().get(randomIndex2);

            colonie.echangerRessources(colon1, colon2);
            cout2=colonie.cout();

            if(cout1<cout2) //Cancel trade
            {
                colonie.echangerRessources(colon2,colon1);
            }
            else
                cout1=cout2;
            i++;
        }


        return colonie.cout();
    }

    /**
     * Algorithme d'affectation qui prend le fait que les relations soient binaires pour satisfaire les colons par ordre croissant de relations. Tres peu concluent et sous performe algoBestPerfSUR()
     *
     * @param colonieN
     * Index de la colonie.
     *
     *
     * @param r
     * Ressrouces a affecter.
     *
     * @return
     * retourne le cout de l'affectation.
     *
     * @throws Exception
     * Erreur lors de l'echange ou le calcul du cout.
     *
     * @see Expedition#affectation(int)
     * @see Menu#afficherMenuAffectation(int, int)
     *
     */
    public int algoBestPerfSUPER(int colonieN,Ressource r) throws Exception
    {
        //Trier les Colons du plus petit au plus grand nombre de relations
        Colonie colonie = colonies.get(colonieN);
        List<Colon> sortedcolonie = new ArrayList<>(colonie.getColons());
        sortedcolonie.sort(Comparator.comparingInt(c -> c.getRelationsDetestables().size()));

        //Donner les ressources en essayant de faire le moins de jaloux

        //Donner les favoris
        int coutreturn = algoFavoriteFirst(colonieN, r);
        //Arranger les jalousies
        for(Colon colon : sortedcolonie)
        {
            for(Colon colon2 : colon.getRelationsDetestables())
            {
                colonie.echangerRessources(colon, colon2);
                if(colonie.cout()<coutreturn)
                {
                    coutreturn=colonie.cout();
                }
                else
                {
                    colonie.echangerRessources(colon, colon2);
                }
            }
        }

        return coutreturn;
    }


    /**
     * Optimise l'affectation des ressources aux colons en minimisant les conflits liés aux relations détestables.
     * L'algorithme trie les colons par contraintes, effectue plusieurs tentatives d'affectation initiale,
     * puis affine les résultats par des échanges locaux pour trouver le meilleur coût.
     * La meilleure configuration est restaurée à la fin.
     *
     * @param colonieN
     * Index de la colonie.
     *
     * @param r
     * Ressrouces a affecter.
     *
     * @return
     * retourne le cout de l'affectation.
     *
     * @throws Exception
     * Erreur lors de l'echange ou le calcul du cout ou tailles de colons non égal au nombre de ressources.
     *
     * @see Expedition#affectation(int)
     * @see Menu#afficherMenuAffectation(int, int)
     *
     */
    public int meuilleurResultat(int colonieN, Ressource r) throws Exception
    {
        Colonie colonie = colonies.get(colonieN);
        
        // Vérification initiale
        if (colonie.getColons().size() != r.size()) {
            throw new NbColonDIfferentNbRessourcesException("Nombre de colons différent du nombre de ressources");
        }
        
        // 1. Créer une copie du graphe des relations et des préférences pour manipulation
        List<Colon> colonsTries = new ArrayList<>(colonie.getColons());
        
        // 2. Trier les colons par ordre décroissant de contraintes (nombre de relations détestables)
        colonsTries.sort((c1, c2) -> c2.getRelationsDetestables().size() - c1.getRelationsDetestables().size());
        
        int meilleurCout = Integer.MAX_VALUE;
        Map<Colon, String> meilleureAffectation = new HashMap<>();
        
        // 3. Phase 1 : Essayer plusieurs affectations initiales
        for (int tentative = 0; tentative < 5; tentative++) {
            // Réinitialiser les ressources
            Ressource ressourcesTemp = new Ressource();
            for (int i = 0; i < r.size(); i++) {
                ressourcesTemp.addRessource(r.getRessource(i));
            }
            
            // Affecter les ressources en priorité aux colons ayant le plus de contraintes
            for (Colon colon : colonsTries) {
                String meilleureRessource = null;
                int minConflits = Integer.MAX_VALUE;
                
                // Pour chaque ressource disponible
                for (int i = 0; i < ressourcesTemp.size(); i++) {
                    String ressourceCandidate = ressourcesTemp.getRessource(i);
                    colon.affectationRessource(ressourceCandidate);
                    
                    // Calculer les conflits potentiels
                    int conflits = 0;
                    for (Colon voisin : colon.getRelationsDetestables()) {
                        if (!voisin.getRessource().isEmpty() && 
                            colon.getPreferenceIndex(voisin.getRessource()) < 
                            colon.getPreferenceIndex(ressourceCandidate)) {
                            conflits++;
                        }
                    }
                    
                    if (conflits < minConflits) {
                        minConflits = conflits;
                        meilleureRessource = ressourceCandidate;
                    }
                }
                
                // Affecter la meilleure ressource trouvée
                colon.affectationRessource(meilleureRessource);
                ressourcesTemp.removeRessource(meilleureRessource);
            }
            
            // 4. Phase 2 : Optimisation locale par échanges
            boolean amelioration;
            do {
                amelioration = false;
                int coutActuel = colonie.cout();
                
                // Essayer tous les échanges possibles entre paires de colons
                for (int i = 0; i < colonsTries.size(); i++) {
                    for (int j = i + 1; j < colonsTries.size(); j++) {
                        Colon colon1 = colonsTries.get(i);
                        Colon colon2 = colonsTries.get(j);
                        
                        // Tester l'échange
                        colonie.echangerRessources(colon1, colon2);
                        int nouveauCout = colonie.cout();
                        
                        if (nouveauCout < coutActuel) {
                            coutActuel = nouveauCout;
                            amelioration = true;
                        } else {
                            // Annuler l'échange s'il n'y a pas d'amélioration
                            colonie.echangerRessources(colon1, colon2);
                        }
                    }
                }
            } while (amelioration);
            
            // Mettre à jour la meilleure solution si nécessaire
            int coutFinal = colonie.cout();
            if (coutFinal < meilleurCout) {
                meilleurCout = coutFinal;
                meilleureAffectation.clear();
                for (Colon c : colonie.getColons()) {
                    meilleureAffectation.put(c, c.getRessource());
                }
            }
        }
        
        // 5. Restaurer la meilleure affectation trouvée
        for (Map.Entry<Colon, String> entry : meilleureAffectation.entrySet()) {
            entry.getKey().affectationRessource(entry.getValue());
        }
        
        return meilleurCout;
    }
        
        
        
        
    

    /**
     * Affecte les ressources aux colons d'une colonie avec le meilleur algorithme disponible.
     *
     * @param colonieIndex
     * Index de la colonie.
     *
     * @return
     * retourne le cout de l'affectation.
     *
     * @throws Exception
     * Erreur de lecture du fichier.
     * Erreur de creation.
     *
     * @see MenuLoadTXT#afficherMenuConfigurationLoadTXT()
     *
     */
    public int affectation(int colonieIndex) throws Exception
    // simuler le partage des ressources entre colons
    {
        Colonie colonie = colonies.get(colonieIndex);
        Ressource ressource = ressources.get(colonieIndex);
        int maxTentatives=100;
        //Algo usage
        //return algoBestPerfSUR(maxTentatives, colonieIndex, ressource);
        //return algoBestPerfSUPER(colonieIndex, ressource);
        return meuilleurResultat(colonieIndex,ressource);
    }

    /**
     * Importe une colonie depuis un fichier et l'ajoute a l'expedition.
     *
     * @param path
     * Chemin du fichier
     *
     * @return
     * 0 si c'est un success.
     *
     * @throws Exception
     * Erreur de lecture du fichier.
     *
     * @see Main#main(String[])
     *
     */
    public int Importation(String path) throws Exception
    {
        List<String> keyClasses = Arrays.asList("colon","ressource","deteste","preferences");
        List<String> linesOfFiles = Files.readAllLines(Paths.get(path));
        Colonie newColonie = new Colonie();
        Ressource newRessource = new Ressource();

        //Checks
        int colonCount = 0;
        int ressourceCount = 0;
        int detesteCount = 0;

        //REGEX
        String colonRegex = "^colon\\(([a-z0-9_]+)\\).$";
        String ressourceRegex = "^ressource\\(([a-z0-9_]+)\\).$";
        String detesteRegex = "^deteste\\(([a-z0-9_]+)(?:,([a-z0-9_]+))+\\).$";
        String preferencesRegex = "^preferences\\(([a-z0-9_]+),([a-z0-9_,]+)\\)\\.$";

        Pattern colonPatern = Pattern.compile(colonRegex);
        Pattern ressourcePatern = Pattern.compile(ressourceRegex);
        Pattern detestePatern = Pattern.compile(detesteRegex);
        Pattern preferencesPatern = Pattern.compile(preferencesRegex);

        for(int i=0;i<linesOfFiles.size();i++)
        {
            // Colon Case
            String line = linesOfFiles.get(i);
            String keytest = keyClasses.get(0);
            Boolean t = linesOfFiles.get(i).startsWith(keyClasses.get(0));

            if(linesOfFiles.get(i).startsWith(keyClasses.get(0)))
            {
                if(linesOfFiles.get(i).contains(","))
                    throw new ToMuchArgumentsException("Trop d'arguments dans la ligne:\t["+linesOfFiles.get(i)+"]");

                Matcher matcher = colonPatern.matcher(linesOfFiles.get(i));
                if(matcher.find())
                    newColonie.ajouterColon(new Colon(matcher.group(1)));
            }

            // Ressource Case
            else if (linesOfFiles.get(i).startsWith(keyClasses.get(1)))
            {
                if(linesOfFiles.get(i).contains(","))
                    throw new ToMuchArgumentsException("Trop d'arguments dans la ligne:\t["+linesOfFiles.get(i)+"]");
                Matcher matcher = ressourcePatern.matcher(linesOfFiles.get(i));
                if(matcher.find())
                    newRessource.addRessource(matcher.group(1));
            }

            // Hate Case
            else if (linesOfFiles.get(i).startsWith(keyClasses.get(2)))
            {
                if(linesOfFiles.get(i).split(",").length > 2)
                    throw new ToMuchArgumentsException("Trop d'arguments dans la ligne:\t["+linesOfFiles.get(i)+"]");

                Matcher matcher = detestePatern.matcher(linesOfFiles.get(i));
                if(matcher.find())
                {
                    Colon colonDeBase = newColonie.getColonObjet(matcher.group(1));
                    Colon colonHated = newColonie.getColonObjet(matcher.group(2));
                    colonDeBase.addRelation(colonHated);
                }
            }

            //Preferences Case
            else if (linesOfFiles.get(i).startsWith(keyClasses.get(3)))
            {
                Matcher matcher = preferencesPatern.matcher(linesOfFiles.get(i));
                if (matcher.find())
                {
                    Colon colon = newColonie.getColonObjet(matcher.group(1));
                    String preferencesList = matcher.group(2);
                    String[] preferences = preferencesList.split(",");
                    for (String preference : preferences)
                    {
                        colon.addPreference(preference.trim());
                    }
                }
            }
            // Unknown case
            else
            {
                throw new NonFormalismeException("La ligne:\t["+linesOfFiles.get(i)+"]\n Ne respecte pas les mots clé de debut.");
            }
            if(!linesOfFiles.get(i).endsWith("."))
            {
                throw new NonFormalismeException("La ligne:\t["+linesOfFiles.get(i)+"]\n Ne fini pas par un '.'");
            }
        }

        //Checks
        if (newColonie.getColons().size()!=newRessource.size())
        {
            throw new NbColonDIfferentNbRessourcesException("Nombre de Colon non egal au nombre de Ressources.");
        }

        colonies.add(newColonie);
        ressources.add(newRessource);
        return 0;
    }

    /**
     * Savuegarde une solution d'une colonie donnée de l'expedition dans le dossier SolutionSave avec le nom donné.
     *
     * @param nomFichier
     * Nom du fichier sauvegarde.
     *
     * @param colonieIndex
     * Index de la colonie a sauvegarder.
     *
     * @return
     * Retourne le chemin du fichier sauvegardé.
     *
     * @see MenuLoadTXT#afficherMenuConfigurationLoadTXT()
     *
     */
    public String save(String nomFichier, int colonieIndex)
    {
        // Folder
        String folderName = "SolutionSaves";
        File folder = new File(folderName);

        // Ensure the folder exists
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                System.out.println("Echec lors de la creation du fichier: " + folderName);
            }
        }

        // Folder + filename
        File saveFile = new File(folderName + File.separator + nomFichier);

        // Create File
        try {
            if (saveFile.createNewFile()) {
                System.out.println("Fichier crée: " + saveFile.getName());
            } else {
                System.out.println("Le fichier existe deja.");
            }
        } catch (IOException e) {
            System.out.println("Il Y a eu une erreur lors de la creation du fichier.");
        }

        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            for (Colon colon : colonies.get(colonieIndex).getColons()) {
                writer.write(colon.getNom() + ":" + colon.getRessource());
                writer.newLine();
            }
            writer.write("\nCout: " + colonies.get(colonieIndex).cout());
            System.out.println("Ecriture réussie.");
        } catch (IOException e) {
            System.err.println("Il y a eu une erreur lors de l'écriture du fichier: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return saveFile.getAbsolutePath();
    }

}
