/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pietnastka;

import java.util.ArrayList;

/**
 *
 * @author maciek
 */
public class Uklad {
    private byte[][] tab;
    double ocena;
    static int liczbaStanowOdwieczonych = 0;
    static int liczbaStanowPrzetworzonych = 0;
    byte yZera, xZera;
    private static String poprawny;
    
    public Uklad(byte w, byte k)
    {
        this.tab = new byte[w][k];
        this.ocena = 1.0;
    }
    
    Uklad(Uklad u, char kier) throws PoprawnyUkladException
    {
        this.tab = u.tab.clone();
        this.ocena = u.ocena;
        this.yZera = u.yZera;
        this.xZera = u.xZera;
        this.przesun(kier);
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
                    main.c(yZera + " "+ xZera);
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
        main.c(this.jakieMozliwosci("RDUL"));
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
        main.c(this);
        if(czyPoprawna()) throw new PoprawnyUkladException();
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
    
    private boolean czyPoprawna()
    {
//        main.c("test poprawnosci:");
//        main.c(this.toString());
//        main.c(poprawny);
        return this.toString().equals(poprawny);
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
    
    void setPoprawny(String popr)
    {
        Uklad.poprawny = popr;
    }

    static class PoprawnyUkladException extends Exception {

        public PoprawnyUkladException() {
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
