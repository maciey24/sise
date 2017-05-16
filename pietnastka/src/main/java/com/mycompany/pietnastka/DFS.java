package com.mycompany.pietnastka;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author maciek
 */
public final class DFS extends Algorytm {
    
    Stack<Uklad> stos;
    
    DFS(Uklad u, String strategia) throws Uklad.PoprawnyUkladException
    {
        super(u, strategia);
        stos = new Stack();
        przesuwanie();
    }
    
    @Override
    void przesuwanie() throws Uklad.PoprawnyUkladException
    {
        stos.push(this.u);
//        main.c(u);
        while(!stos.empty())
        {
            Uklad wierzcholek = stos.pop();
//            while(wierzcholek.sciezkaDoWezla.length()>=main.maksymalnaDozwolonaGlebokoscRekursji)
//            {
//                wierzcholek = stos.pop();
//            }
            Uklad.liczbaStanowOdwiedzonych++;
//            if(!listaOdwiedzonych.containsKey(wierzcholek.toString()))//&&wierzcholek.sciezkaDoWezla.length()<main.maksymalnaDozwolonaGlebokoscRekursji)
//            {
                if(wierzcholek.czyPoprawna())
                {
                    throw new Uklad.PoprawnyUkladException(wierzcholek.sciezkaDoWezla);
                }
                String dozwoloneRuchy = wierzcholek.jakieMozliwosci(strategia);
//                listaOdwiedzonych.put(wierzcholek.toString(), null);

                    if(wierzcholek.sciezkaDoWezla.length()<=main.maksymalnaDozwolonaGlebokoscRekursji-1)
                    {
                        for(int i = 0; i<dozwoloneRuchy.length(); i++)
                        {
                            stos.push(new Uklad(wierzcholek, wierzcholek.sciezkaDoWezla, dozwoloneRuchy.charAt(i)));
                        }
                    }
                    else
                    {
                        
                    }
//            }
//            else 
//            {       
////                main.c("wierzcholek juz odwiedzony");
//            }
        }
    }
}
