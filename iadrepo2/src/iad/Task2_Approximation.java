package iad;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Task2_Approximation {
	static ArrayList<Double[]> trainX = new ArrayList<Double[]>();
	static ArrayList<Double[]> trainY = new ArrayList<Double[]>();
	
	static ArrayList<Double> testX = new ArrayList<Double>();
	static ArrayList<Double> testY = new ArrayList<Double>();
        
        static ArrayList<Point> results = new ArrayList<>();
        static ArrayList<Point> trainPoints = new ArrayList<>();
        static ArrayList<Point> aproksymowanaFunkcja = new ArrayList<>();
        static ArrayList<Point> wszystkiePunkty = new ArrayList<>();
        static ArrayList<Double> blad = new ArrayList<>();

	public static void main(String[] args) 
        {
            double krok = 0.01;
            for(double i = 1; i<=100; i+=krok) //
            {
                aproksymowanaFunkcja.add(new Point(0.0, i, Math.sqrt(i)));
                testX.add(i);
            }
            Main.PlotPoints(aproksymowanaFunkcja);
            
            int liczbaPunktowTreningowych = 50;//liczba punktow treningowych
            int liczbaNeuronow = 10;
            int liczbaWarstwUkrytych = 3;
            double predkoscUczenia = 0.0005;
            double momentum = 0.4;
            int liczbaEpok = 3000;
            boolean bias = false;
            for(int i = 0; i<liczbaPunktowTreningowych; i++)
            {
                double x = ThreadLocalRandom.current().nextDouble(1, 100); //zakres dziedzina punktow treningowych
                Double[] tab = new Double[1];
                tab[0]=x;
                trainX.add(tab);
                Double[] tab2 = new Double[1];
                tab2[0] = Math.sqrt(x);
                trainY.add(tab2);
                
                trainPoints.add(new Point(1.0, x, Math.sqrt(x))); //redundantne do wykresu
            }
            Main.PlotPoints(trainPoints);

            String biasString = ", bez biasu.";
            if(bias) biasString=", z biasem";
                wszystkiePunkty.addAll(trainPoints);
		for (int i=liczbaNeuronow; i<=liczbaNeuronow; i++) //liczba neuronow w kazdej warstwie ukrytej
                {
                    NeuralNetwork net = new NeuralNetwork(1, liczbaWarstwUkrytych+1, i, 1, Boolean.FALSE);//druga liczba - liczba warstw, warstwy
                    blad = net.train(trainX, trainY, predkoscUczenia, momentum, 0.01, liczbaEpok);//predkosc uczenia, ped momentum, max blad, liczba epok epoki

                    for(int j=0; j<aproksymowanaFunkcja.size(); j++) 
                    {
                        Double res =  net.fwdPropagate(new Double [] { aproksymowanaFunkcja.get(j).getVals()[0] })[0];
                        c(aproksymowanaFunkcja.get(j).getVals()[0]+ " :" + res);
                        results.add(new Point(2.0, aproksymowanaFunkcja.get(j).getVals()[0], res));
                    }
                    wszystkiePunkty.addAll(results);
                    aproksymowanaFunkcja.addAll(results);
                    Main.PlotPoints(results, "wykres odpowiedzi sieci na zbiór testowy dla "+i+" neuronow w warstwie ukrytej",
                        "x", "y", "zbior treningowy","funkcja aproksymowana");
                    Main.PlotPoints(aproksymowanaFunkcja, "porównanie wykresów funkcji aproksymującej i aproksymowanej",
                        "x", "y", "zbior treningowy","funkcja aproksymowana");
                    results.clear();
		}
                Main.PlotPoints(wszystkiePunkty, "wynik dzialania sieci dla :"+System.lineSeparator()+liczbaPunktowTreningowych+" punktów treningowych, "+
                        liczbaNeuronow+" neuronów w każdej warstwie ukrytej, "+ liczbaWarstwUkrytych +" warstw ukrytych, "+
                        "prędkość uczenia: "+predkoscUczenia+", pęd uczenia: "+momentum+", liczba epok: "+liczbaEpok+biasString, "x", "y", "seria1", "seria2");
                Main.PlotList(blad, "błąd", null, "numer epoki", "wartość błędu");
	}
	
        public static void c(Object o)
        {
            System.out.println(o);
        }
}
