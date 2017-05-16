package com.mycompany.pietnastka;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author alicj
 */
public class BFS extends Algorytm {

    Queue<Uklad> kolejka;

    public BFS(Uklad u, String strategia) throws Uklad.PoprawnyUkladException {
        super(u, strategia);
        kolejka = new LinkedList<>();
        przesuwanie();
    }

    @Override
    void przesuwanie() throws Uklad.PoprawnyUkladException 
    {
        kolejka.add(this.u);
        while (!kolejka.isEmpty()) 
        {
            Uklad wierzcholek = kolejka.poll();
            Uklad.liczbaStanowOdwiedzonych++;
//            main.c(wierzcholek);
//            if(!listaOdwiedzonych.containsKey(wierzcholek.toString()))
//            {
                if (wierzcholek.czyPoprawna()) {
                    throw new Uklad.PoprawnyUkladException(wierzcholek.sciezkaDoWezla);
                }
//                main.c(wierzcholek.sciezkaDoWezla);
                String dozwoloneRuchy = wierzcholek.jakieMozliwosci(strategia);
//                main.c(dozwoloneRuchy);
//                listaOdwiedzonych.put(wierzcholek.toString(), null);

                for (int i = 0; i < dozwoloneRuchy.length(); i++) {
                    kolejka.add(new Uklad(wierzcholek, wierzcholek.sciezkaDoWezla, dozwoloneRuchy.charAt(i)));
                }
//            }
//            else 
//            {
//    //            main.c("wierzcholek juz odwiedzony");
//            }
        }
    
    }
}
