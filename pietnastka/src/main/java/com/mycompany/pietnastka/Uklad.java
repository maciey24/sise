/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pietnastka;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author maciek
 */
public class Uklad {
    private byte[][] tab;
    double ocena;
    private byte yZera, xZera;
    private static String poprawny;
    static long liczbaStanowPrzetworzonych = 0;
    static long liczbaStanowOdwiedzonych = 0;
    static long maxGlebokosc = 0;
//    static String ciagRuchow = "";
    String sciezkaDoWezla;
    
    public Uklad(byte w, byte k)
    {
        this.tab = new byte[w][k];
        this.ocena = 1.0;
        this.sciezkaDoWezla = "";
        liczbaStanowPrzetworzonych++;
    }
    
    Uklad(Uklad u, String sciezkaDoNadrzednegoWezla, char kier) throws PoprawnyUkladException
    {
//        main.c("kopiowany przed skopiowaniem:");
//        main.c(u);
        this.tab = new byte[u.tab.length][u.tab[0].length];
        for(int i =0; i<this.tab.length; i++)
        {
            this.tab[i] = Arrays.copyOf(u.tab[i], this.tab[i].length);
        }
//        this.tab = Arrays.copyOf(tab, u.tab.length);
        this.ocena = u.ocena;
        this.yZera = u.yZera;
        this.xZera = u.xZera;
        this.przesun(kier);
//        Uklad.ciagRuchow += kier;
        sciezkaDoWezla = sciezkaDoNadrzednegoWezla + kier;
        if(this.sciezkaDoWezla.length()>maxGlebokosc) maxGlebokosc = this.sciezkaDoWezla.length();
        liczbaStanowPrzetworzonych++;
//        this.tab[2][2] = 99;
//        
//        main.c("nowy uklad:");
//        System.out.println(this);
//        main.c("kopiowany uklad:");
//        main.c(u);
    }
    
    public void setLiczba(byte w, byte k, byte liczba)
    {
        this.tab[w][k]=liczba;
    }
    
    public byte getLiczba(byte w, byte k)
    {
        return this.tab[w][k];
    }
    
//    public byte getLiczba(byte liczba)
//    {
//        for (byte i = 0; i<this.tab.length; i++)
//        {
//            for(byte j = 0; j<this.tab[i].length; j++)
//            {
//                if(this.tab[i][j] == 0) return this.tab[i][j];
//            }
//        }
//        return 0;
//    }
    
    public void znajdzZero()
    {
        for (byte i = 0; i<this.tab.length; i++)
        {
            for(byte j = 0; j<this.tab[i].length; j++)
            {
                if(this.tab[i][j] == 0) 
                {
//                    main.c(i + " "+ j);
                    this.yZera=i;
                    this.xZera=j;
                    main.c("współrzędne zera: " + yZera + " "+ xZera);
                }
            }
        }
    }

    @Override
    public String toString() {
        String res = "";
        for (byte i = 0; i<this.tab.length; i++)
        {
            for(byte j = 0; j<this.tab[i].length; j++)
            {
                res+=this.tab[i][j] + " ";
            }
            res +=System.lineSeparator();
        }
        return res;
    }
    
    public boolean przesun(char c) throws PoprawnyUkladException
    {
//        main.c(this.jakieMozliwosci("RDUL"));
        boolean czyUdaloSiePrzesunac = false;
        switch(c){
            case 'U':
                if(yZera!=0) 
                {
                    swap(yZera,xZera, (byte) (yZera-1),xZera);
                    czyUdaloSiePrzesunac = true;
                }
                main.c("przesuniecie do gory");
                break;
            case 'D':
                if(yZera!=this.tab.length-1) 
                {
                    swap(yZera,xZera, (byte) (yZera+1),xZera);
                    czyUdaloSiePrzesunac = true;
                }
                main.c("przesuniecie do dolu");
                break;
            case 'L':
                if(xZera!=0) 
                {
                    swap(yZera,xZera, yZera, (byte) (xZera-1));
                    czyUdaloSiePrzesunac = true;
                }
                main.c("przesuniecie do lewej");
                break;
            case 'R':
                if(xZera!=this.tab[yZera].length-1) 
                {
                    swap(yZera,xZera, yZera, (byte) (xZera+1));
                    czyUdaloSiePrzesunac = true;
                }
                main.c("przesuniecie do prawej");
                break;
        }
//        main.c(this);
        return czyUdaloSiePrzesunac;
    }
    
    private void swap(byte y1, byte x1, byte y2, byte x2)
    {
        byte temp = 0;
        temp = getLiczba(y1, x1);
//        main.c(temp);
        setLiczba(y1, x1, getLiczba(y2, x2));
//        main.c(getLiczba(y1, x1));
        setLiczba(y2, x2, temp);
//        main.c(getLiczba(y2, x2));
        yZera = y2;
        xZera= x2;
        
    }
    
    private boolean czyIdentyczne(Uklad ukl)
    {
        return this.toString().equals(ukl.toString());
    }
    
    public boolean czyPoprawna()
    {
//        main.c("test poprawnosci:");
//        main.c(this.toString());
//        main.c(poprawny);
//        main.c("ten układ: " + System.lineSeparator() + this);
//        main.c("poprawny: " + System.lineSeparator() + poprawny);
        return this.toString().equals(poprawny);
    }
    
    void setPoprawny(String popr)
    {
        Uklad.poprawny = popr;
    }
    
    void wypelnijPoprawnie()
    {
        for (byte i = 0; i<this.tab.length; i++)
        {
            for(byte j = 0; j<this.tab[i].length; j++)
            {
                this.setLiczba(i, j, (byte) ((i*this.tab[i].length)+j+1));
            }
        }
        this.setLiczba((byte)(this.tab.length-1), (byte)(this.tab[0].length-1), (byte) 0);
    }

    static class PoprawnyUkladException extends Exception {

        private final String sciezka;

        public PoprawnyUkladException(String sciezka) {
            this.sciezka = sciezka;
        }
        
        @Override
        public String toString()
        {
            return sciezka;
        }
    }
    
    public String jakieMozliwosci(String str)
    {   
        ArrayList<String> omin = new ArrayList<>();
        if(yZera==0) omin.add("U");
        if(yZera==this.tab.length-1) omin.add("D");
        if(xZera==0) omin.add("L");
        if(xZera==this.tab[0].length-1) omin.add("R");
        String res = "";
        for(int i =0; i<str.length(); i++)
        {
             if(!omin.contains(str.substring(i, i+1)))
             res +=str.charAt(i);
        }
        return res;
    }
    
}
