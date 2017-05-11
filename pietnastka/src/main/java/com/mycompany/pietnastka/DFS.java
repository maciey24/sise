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
            while(wierzcholek.sciezkaDoWezla.length()>=main.maksymalnaDozwolonaGlebokoscRekursji)
            {
                wierzcholek = stos.pop();
            }
            Uklad.liczbaStanowOdwiedzonych++;
            main.c(wierzcholek);
//            if(!wierzcholek.czyOdwiedzony)
            if(!listaOdwiedzonych.contains(wierzcholek.toString()))
            {
                if(wierzcholek.czyPoprawna())
                {
            //            main.c("poprawny");
                    throw new Uklad.PoprawnyUkladException(wierzcholek.sciezkaDoWezla);
                }
                main.c(wierzcholek.sciezkaDoWezla);
//                main.c("litera uzyta :"+ wierzcholek.literaUzytaDoStworzenia);
                String dozwoloneRuchy = wierzcholek.jakieMozliwosci(strategia);
                main.c(dozwoloneRuchy);
                listaOdwiedzonych.add(wierzcholek.toString());

                for(int i = 0; i<dozwoloneRuchy.length(); i++)
                {
//                    main.c("uklad wyjściowy: "+System.lineSeparator()+wierzcholek);
//                    Uklad nowy = new Uklad(wierzcholek, dozwoloneRuchy.charAt(i));
                    stos.push(new Uklad(wierzcholek, wierzcholek.sciezkaDoWezla, dozwoloneRuchy.charAt(i)));
//                    main.c("nowy uklad: "+System.lineSeparator()+ nowy);
//                    main.c(nowy.jakieMozliwosci(strategia));
                }
            }
            else 
            {       
//                ciagRuchow = ciagRuchow.substring(0, ciagRuchow.length());
                main.c("wierzcholek juz odwiedzony");
            }
        }
//        String dozwoloneRuchy = u.jakieMozliwosci(strategia);
//        if(listaOdwiedzonych.contains(this.toString())) return;
//        for(int i = 0; i<dozwoloneRuchy.length(); i++)
//        {
//            przesuwanie();
//        }
//        stos.push(dozwoloneRuchy.substring(0, 1));
//        while(!stos.empty())
//        {
//            //czy to jest wynik
//            //czy już jest ten węzeł odwiedzony
//            stack.push(curentElement.ruchWGore);
//            stack.push(currentElement.ruchWDol);
//            ...
//        }

//        let S be a stack
//      S.push(v)
//      while S is not empty
//          v = S.pop()
//          if v is not labeled as discovered:
//              label v as discovered
//              for all edges from v to w in G.adjacentEdges(v) do 
//                  S.push(w)


    }
}
