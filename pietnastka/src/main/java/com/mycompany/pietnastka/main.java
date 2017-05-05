/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pietnastka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maciek
 */
public class main {

        static boolean debug = true;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        c("rozwiązywanie piętnastki");
        String rodzajAlgorytmu, porzadek, plikWejsciowy, plikRozwiazenie, plikStat;
        rodzajAlgorytmu = args[0];
        porzadek = args[1];
        plikWejsciowy = args[2];
        plikRozwiazenie = args[3];
        plikStat = args[4];
        c("rodzaj algorytmu: " + rodzajAlgorytmu);
        c("porządek przeszukiwania sąsiedztwa: " + porzadek);
        c("plik z układem początkowym: " + plikWejsciowy);
        c("plik z rozwiązeniem będzie: " + plikRozwiazenie);
        c("plik ze statystykami: " + plikStat);
        if(debug) testArgumentow(args);
        
        byte w, k; //liczba wierszy, kolumn
        Scanner odczyt = new Scanner(new File(plikWejsciowy));
        w = odczyt.nextByte();
        k = odczyt.nextByte();
        c("liczba wierszy: " + w);
        c("liczba kolumn: " + k);
        Uklad u = new Uklad(w, k);
        
        for(byte i=0; i<w; i++)
        {
            for(byte j=0; j<k; j++)
            {
                u.setLiczba(i, j, odczyt.nextByte());
            }
        }
        u.znajdzZero();
        
        testPrzesuwania(u);

    }
    
    public static void c(Object o)
    {
        if(debug)
        System.out.println(o);
    }
    
    private static void testArgumentow(String[] args)
    {
        String temp= "";
        for (String arg : args) {
            temp += arg + " ";
        }
        c(temp);
        try(PrintWriter out = new PrintWriter("..\\dodatki\\wyniki\\test.txt"))
        {
            out.println(temp);
        }   catch (FileNotFoundException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private static void maskowanieUkosnikow(String s)
    {
        
    }
    
    private static void testPrzesuwania(Uklad u)
    {
        c(u);
        u.przesun('U');
        c(u);
        u.przesun('U');
        c(u);
        u.przesun('U');
        c(u);
        u.przesun('U');
        c(u);
        u.przesun('L');
        c(u);
        u.przesun('L');
        c(u);
        u.przesun('L');
        c(u);
        u.przesun('R');
        c(u);
        u.przesun('R');
        c(u);
        u.przesun('R');
        c(u);
        u.przesun('R');
        c(u);
        u.przesun('D');
        c(u);
        u.przesun('D');
        c(u);
        u.przesun('D');
        c(u);
        u.przesun('D');
        c(u);
    }
}
