/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pietnastka;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author maciek
 */
public class DFS {
    Uklad u;
    String strategia;
    ArrayList<Uklad> listaOdwiedzonych;
    Stack<Uklad> stos;
    
    DFS(Uklad u, String strategia) throws Uklad.PoprawnyUkladException
    {
        this.u = u;
        this.strategia = strategia;
        listaOdwiedzonych = new ArrayList<>();
//        listaOdwiedzonych.add(u.toString());
        stos = new Stack();
        przesuwanie();
    }
    
    private void przesuwanie() throws Uklad.PoprawnyUkladException
    {
        stos.push(this.u);
        main.c(u);
        while(!stos.empty())
        {
            Uklad wierzcholek = stos.pop();
            main.c(wierzcholek);
            if(!wierzcholek.czyOdwiedzony)
            {
                String dozwoloneRuchy = wierzcholek.jakieMozliwosci(strategia);
                main.c(dozwoloneRuchy);
                wierzcholek.czyOdwiedzony = true;
                for(int i = 0; i<dozwoloneRuchy.length(); i++)
                {
                    main.c("uklad wyjściowy: "+System.lineSeparator()+wierzcholek);
                    Uklad nowy = new Uklad(wierzcholek, dozwoloneRuchy.charAt(i));
//                    stos.push(new Uklad(wierzcholek, dozwoloneRuchy.charAt(i)));
                    main.c("nowy uklad: "+System.lineSeparator()+ nowy);
                    main.c(dozwoloneRuchy);
                }
            }
            else main.c("wierzcholek juz odwiedzony");
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
