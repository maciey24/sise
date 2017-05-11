/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pietnastka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
//dfs LUDR "..\dodatki\\uklady\4x4_05_00054.txt" 4x4_01_0001_dfs_ludr_sol.txt 4x4_01_0001_dfs_ludr_stats.txt
//dfs LURD "..\dodatki\\uklady\3x3_05_00004.txt" ..\dodatki\wyniki\3x3_05_00004_dfs_durl_sol.txt ..\dodatki\wyniki\3x3_05_00004_dfs_durl_stats.txt

/**
 *
 * @author maciek
 */
public class main {

    static boolean debug = true;
    static int maksymalnaDozwolonaGlebokoscRekursji = 30;
//        static String ciagRuchow;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        c("rozwiązywanie piętnastki");
//        ciagRuchow = "";
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
        if (debug) {
            testArgumentow(args);
        }

        byte w, k; //liczba wierszy, kolumn
        Scanner odczyt = new Scanner(new File(plikWejsciowy));
        w = odczyt.nextByte();
        k = odczyt.nextByte();
        c("liczba wierszy: " + w);
        c("liczba kolumn: " + k);
        Uklad u = new Uklad(w, k);
        for (byte i = 0; i < w; i++) {
            for (byte j = 0; j < k; j++) {
                u.setLiczba(i, j, odczyt.nextByte());
            }
        }
        u.znajdzZero();

        Uklad poprawny = new Uklad(w, k);
        poprawny.wypelnijPoprawnie();
        c("poprawny układ :" + System.lineSeparator() + poprawny);
        String poprawnyString = poprawny.toString();
        u.setPoprawny(poprawnyString);
        poprawny = null;

        Algorytm algorytm = null;
        long czasStart = System.nanoTime(), czasStop = System.nanoTime();
        try {
            try (PrintWriter out = new PrintWriter(plikRozwiazenie)) {
                out.println("-1");
            }
            try (PrintWriter out = new PrintWriter(plikStat)) {
                out.println("-1");
            } catch (FileNotFoundException exc) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, exc);
            }
            czasStart = System.nanoTime();
            switch (rodzajAlgorytmu) {
                case "dfs":
                    strategia = new StringBuilder(strategia).reverse().toString();
                    algorytm = new DFS(u, strategia);
                    break;
                case "bfs":
                    algorytm = new BFS(u, strategia);
                    break;
            }
            czasStop = System.nanoTime();
//            testPrzesuwania(u);
        } catch (Uklad.PoprawnyUkladException ex) {
            czasStop = System.nanoTime();
            c(ex);
            c("długość znalezionego rozwiązania: " + ex.toString().length());
            c("liczba stanów odwiedzonych: " + Uklad.liczbaStanowOdwiedzonych);
            c("liczba stanów przetworzonych: " + (Uklad.liczbaStanowPrzetworzonych - 1));//nasz program "przetwarza" jeszcze układ poprawny
            c("maksymalna głębokość przetwarzana w drzewie: " + Uklad.maxGlebokosc);
            c(((czasStop - czasStart) / 1000) / 1000.0); //nie "/1000000", bo wtedy nie byłoby części ułamkowej, a nie "/1000000.0" bo wtedy byłaby zbyt duza dokladnosc (wymagana jest dokladnosc 3 miejsc po przecinku)

            try (PrintWriter out = new PrintWriter(plikRozwiazenie)) {
                out.println(ex.toString().length());
                out.println(ex);
            }
            try (PrintWriter out = new PrintWriter(plikStat)) {
                out.println(ex.toString().length());
            } catch (FileNotFoundException exc) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, exc);
            }
        }
        try (PrintWriter out = new PrintWriter(new FileOutputStream(new File(plikStat), true))) {
            out.append(String.valueOf((Uklad.liczbaStanowOdwiedzonych)));
            out.append(System.lineSeparator() + String.valueOf((Uklad.liczbaStanowPrzetworzonych)));
            out.append(System.lineSeparator() + String.valueOf((Uklad.maxGlebokosc)));
            out.append(System.lineSeparator() + String.valueOf((((czasStop - czasStart) / 1000) / 1000.0)));
        } catch (FileNotFoundException exc) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, exc);
        }
//        Jest to plik tekstowy standardowo składający się z 2 linii. 
//Pierwsza z nich zawiera liczbę całkowitą n, określającą długość 
//znalezionego rozwiązania (czyli długość ciągu ruchów odpowiadających 
//przesunięciom wolnego pola, które przeprowadzą układankę z zadanego 
//układu początkowego do układu wzorcowego). 

//Natomiast w drugiej 
//linii znajduje się ciąg n liter odpowiadających poszczególnym 
//ruchom wolnego pola w ramach znalezionego rozwiązania, 
//zgodnie z reprezentacją przedstawioną w tabeli zamieszczonej wyżej
//. Jeżeli dla zadanego układu początkowego program nie znalazł 
//rozwiązania, wówczas plik składa się tylko z 1 linii, 
//która zawiera liczbę -1.
//Plik z dodatkowymi informacjami
//Jest to plik tekstowy składający się z 5 linii, z których każda
//zawiera jedną liczbę oznaczającą odpowiednio:
//1 linia (liczba całkowita): długość znalezionego 
//rozwiązania - o takiej samej wartości jak w pliku z rozwiązaniem 
//(przy czym gdy program nie znalazł rozwiązania, wartość ta to -1);
//2 linia (liczba całkowita): liczbę stanów odwiedzonych;
//3 linia (liczba całkowita): liczbę stanów przetworzonych;
//4 linia (liczba całkowita): maksymalną osiągniętą D rekursji;
//5 linia (liczba rzeczywista z dokładnością do 3 miejsc po przecinku): 
//czas trwania procesu obliczeniowego w milisekundach.
    }

    public static void c(Object o) {
        if (debug) {
            System.out.println(o);
        }
    }

    private static void testArgumentow(String[] args) {
        String temp = "";
        for (String arg : args) {
            temp += arg + " ";
        }
        c(temp);
        try (PrintWriter out = new PrintWriter("..\\dodatki\\wyniki\\test.txt")) {
            out.println(temp);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void maskowanieUkosnikow(String s) {

    }

    private static void testPrzesuwania(Uklad u) throws Uklad.PoprawnyUkladException {
        c(u);
        u.przesun('L');
        u.przesun('L');
        u.przesun('D');
        u.przesun('D');
        u.przesun('U');
        u.przesun('U');
        u.przesun('U');
        u.przesun('U');
        u.przesun('R');
        u.przesun('R');
        u.przesun('R');
        u.przesun('R');
        u.przesun('L');
        u.przesun('L');
        u.przesun('L');
        u.przesun('L');
        u.przesun('D');
        u.przesun('D');
        u.przesun('D');
        u.przesun('D');
        u.przesun('R');
        u.przesun('R');
        u.przesun('R');
        u.przesun('R');
        u.przesun('U');
        u.przesun('U');
        u.przesun('U');
        u.przesun('R');
        u.przesun('R');
        u.przesun('R');
        u.przesun('R');
        u.przesun('L');
        u.przesun('L');
    }
}
