package com.brunycharotte.mastermindfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Random;

public class MasterMindAlgo {

    //____________________________________________________________

    /**
     * pré-requis : cod1.length = cod2.length
     * résultat : le nombre d'éléments communs de cod1 et cod2 se trouvant au même indice
     * Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne 1 (le "0" à l'indice 3)
     */
    public static int nbBienPlaces(int[] cod1, int[] cod2) {

        int longueur = cod1.length;
        int nbBienPlaces = 0;

        for (int i = 0; i < longueur; i++) {
            if (cod1[i] == cod2[i]) nbBienPlaces++;
        }

        return nbBienPlaces;
    }
    public static int[] codeAleat() {
        int[] tab = new int[4];

        for (int i = 0; i < 4; i++) {
            Random r = new Random();
            int random = r.nextInt(0, 6); // Renvoi bien un nombre entre 0 et nbCouleurs-1
            tab[i] = random;
        }

        return tab;
    }

    //____________________________________________________________

    /**
     * pré-requis : les éléments de cod sont des entiers de 0 à nbCouleurs-1
     * résultat : un tableau de longueur nbCouleurs contenant à chaque indice i le nombre d'occurrences de i dans cod
     * Par exemple, si cod = (1,0,2,0) et nbCouleurs = 6 la fonction retourne (2,1,1,0,0,0)
     */
    public static int[] tabFrequence(int[] cod) {
        int[] tab = new int[6];

        for (int i = 0; i < 6; i++) {
            int occurence = 0;
            for (int elt : cod) { // Parcourt les éléments du tableau cod
                if (elt == i) {
                    occurence++;
                }
            }
            tab[i] = occurence;
        }

        return tab;
    }

    //____________________________________________________________

    /**
     * pré-requis : les éléments de cod1 et cod2 sont des entiers de 0 à nbCouleurs-1
     * résultat : le nombre d'éléments communs de cod1 et cod2, indépendamment de leur position
     * Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne 3 (2 "0" et 1 "1")
     */
    public static int nbCommuns(int[] cod1, int[] cod2) {
        int nbCommuns = 0;

        int[] tabFrequenceCod1 = tabFrequence(cod1);
        int[] tabFrequenceCod2 = tabFrequence(cod2);
        for (int i = 0; i < 6; i++) {
            if (tabFrequenceCod1[i] > 0 && tabFrequenceCod2[i] > 0) { // Les deux codes ont au moins une occurence de la couleur qui se situe à l'emplacement i
                nbCommuns += Math.min(tabFrequenceCod1[i], tabFrequenceCod2[i]); // Prend la plus petite des deux occurences pour n'avoir que ce qui est en commun pour les deux codes
            }
        }

        return nbCommuns;
    }

    //____________________________________________________________

    /**
     * pré-requis : cod1.length = cod2.length et les éléments de cod1 et cod2 sont des entiers de 0 à nbCouleurs-1
     * résultat : un tableau de 2 entiers contenant à l'indice 0 (resp. 1) le nombre d'éléments communs de cod1 et cod2
     * se trouvant  (resp. ne se trouvant pas) au même indice
     * Par exemple, si cod1 = (1,0,2,0) et cod2 = (0,1,0,0) la fonction retourne (1,2) : 1 bien placé (le "0" à l'indice 3)
     * et 2 mal placés (1 "0" et 1 "1")
     */
    public static int[] nbBienMalPlaces(int[] cod1, int[] cod2) {
        int[] tab = new int[2];

        tab[0] = nbBienPlaces(cod1, cod2); // Attribue le nombre d'elt bien placés au premier indice
        tab[1] = Math.abs(tab[0] - nbCommuns(cod1, cod2)); // Attribue le nombre d'elt mal placés au second indice

        return tab;
    }



}
