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
//dfs LUDR "..\dodatki\\uklady\4x4_05_00054.txt" 4x4_01_0001_dfs_ludr_sol.txt 4x4_01_0001_dfs_ludr_stats.txt
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
        String rodzajAlgorytmu, strategia, plikWejsciowy, plikRozwiazenie, plikStat;
        rodzajAlgorytmu = args[0];
        strategia = args[1];
        plikWejsciowy = args[2];
        plikRozwiazenie = args[3];
        plikStat = args[4];
        c("rodzaj algorytmu: " + rodzajAlgorytmu);
        c("porządek przeszukiwania sąsiedztwa: " + strategia);
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
        
        long czasStart, czasStop;
        c("");
        c(u);
        czasStart= System.nanoTime();
        DFS dfs;
            dfs = new DFS(u, strategia);
            
        czasStop = System.nanoTime();
        c(((czasStop - czasStart)/1000)/1000.0); //nie "/1000000", bo wtedy nie byłoby części ułamkowej, a nie "/1000000.0" bo wtedy byłaby zbyt duza dokladnosc (wymagana jest dokladnosc 3 miejsc po przecinku)
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
        u.przesun('U');
        u.przesun('U');
        u.przesun('U');
        u.przesun('L');
        u.przesun('L');
        u.przesun('L');
        u.przesun('R');
        u.przesun('R');
        u.przesun('R');
        u.przesun('R');
        u.przesun('D');
        u.przesun('D');
        u.przesun('D');
        u.przesun('D');
    }
}
