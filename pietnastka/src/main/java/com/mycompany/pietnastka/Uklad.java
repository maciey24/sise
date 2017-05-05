/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pietnastka;

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
    
    public Uklad(byte w, byte k)
    {
        this.tab = new byte[w][k];
        this.ocena = 1.0;
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
    
    public void przesun(char c)
    {
        switch(c){
            case 'U':
                if(yZera!=0) swap(yZera,xZera, (byte) (yZera-1),xZera);
                main.c("przesuniecie do gory");
                break;
            case 'D':
                if(yZera!=this.tab.length-1) swap(yZera,xZera, (byte) (yZera+1),xZera);
                main.c("przesuniecie do dolu");
                break;
            case 'L':
                if(xZera!=0) swap(yZera,xZera, yZera, (byte) (xZera-1));
                main.c("przesuniecie do lewej");
                break;
            case 'R':
                if(xZera!=this.tab[yZera].length-1) swap(yZera,xZera, yZera, (byte) (xZera+1));
                main.c("przesuniecie do prawej");
                break;
        }
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
}
