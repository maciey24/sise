/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pietnastka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author maciek
 */
public class Astr extends Algorytm{
    
    Stack<Uklad> stos;
    
    Astr(Uklad u, String strategia) throws Uklad.PoprawnyUkladException
    {
        super(u, strategia);
        stos = new Stack();
        przesuwanie();
    }
    
    @Override
    void przesuwanie() throws Uklad.PoprawnyUkladException
    {
        stos.push(this.u);
        while(!stos.empty())
        {
            Uklad wierzcholek = stos.pop();
            while(wierzcholek.sciezkaDoWezla.length()>=main.maksymalnaDozwolonaGlebokoscRekursji)
            {
                wierzcholek = stos.pop();
            }
            Uklad.liczbaStanowOdwiedzonych++;
//            main.c(wierzcholek.odleglosc);
//            main.c(wierzcholek);
            if(!listaOdwiedzonych.contains(wierzcholek.toString()))
            {
                if(wierzcholek.czyPoprawna())
                {
                    throw new Uklad.PoprawnyUkladException(wierzcholek.sciezkaDoWezla);
                }
//                main.c(wierzcholek.sciezkaDoWezla);
                String dozwoloneRuchy = wierzcholek.jakieMozliwosci("LURD");
//                main.c(dozwoloneRuchy);
                listaOdwiedzonych.add(wierzcholek.toString());
                
                ArrayList<Uklad> doDodania = new ArrayList<>();
                for(int i = 0; i<dozwoloneRuchy.length(); i++)
                {
                    doDodania.add(new Uklad(wierzcholek, wierzcholek.sciezkaDoWezla, dozwoloneRuchy.charAt(i), strategia));
                }
                Collections.sort(doDodania);
                Collections.reverse(doDodania);
                for(Uklad u : doDodania)
                {
                    stos.push(u);
                }
            }
            else 
            {       
//                main.c("wierzcholek juz odwiedzony");
            }
        }
    }
}
