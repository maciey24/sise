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
            
//                for(int i =0; i<trainX.size(); i++)
//                {
//                	for(int j = 0; j<trainX.get(i).length; j++)
//                	{
//                		wszystkiePunkty.add(new Point(0.0, trainX.get(i)[j], trainY.get(i)[j]));
//                		trainPoints.add(new Point(0.0, trainX.get(i)[j], trainY.get(i)[j]));
//                	}
//                }
//                
//                for(int i =0; i<testX.size(); i++)
//                {
//                	wszystkiePunkty.add(new Point(1.0, testX.get(i), testY.get(i)));
//                	aproksymowanaFunkcja.add(new Point(1.0, testX.get(i), testY.get(i)));
//                }
//                
//                Main.PlotPoints(wszystkiePunkty, "Wykres danych treningowych i wzorcowych dla sieci", "x", "y", "Dane treningowe", "Dane wzorcowe");
		// 1 - 20
//                wszystkiePunkty.addAll(aproksymowanaFunkcja);
                wszystkiePunkty.addAll(trainPoints);
		for (int i=10; i<=10; i++) //liczba neuronow w kazdej warstwie ukrytej
                {
                    NeuralNetwork net = new NeuralNetwork(1, 4, i, 1, Boolean.FALSE);//druga liczba - liczba warstw, warstwy
                    net.train(trainX, trainY, 0.0005, 0.6, 0.01, 15000);//predkosc uczenia, ped momentum, max blad, liczba epok epoki

                    Double netError = 0.0;
                    for(int j=0; j<aproksymowanaFunkcja.size(); j++) 
                    {
                        Double res =  net.fwdPropagate(new Double [] { aproksymowanaFunkcja.get(j).getVals()[0] })[0];
                        c(aproksymowanaFunkcja.get(j).getVals()[0]+ " :" + res);
                        results.add(new Point(2.0, aproksymowanaFunkcja.get(j).getVals()[0], res));
                        netError += res*res;
                    }
                    Main.PlotPoints(results, "wykres odpowiedzi sieci na zbiór testowy dla "+i+" neuronow w warstwie ukrytej",
                                    "x", "y", "zbior treningowy","funkcja aproksymowana");
                    wszystkiePunkty.addAll(results);
                    results.clear();
                    netError /= trainPoints.size();
//                    aby odkomentowac - ctrl + /

//                    final NeuralNetwork netbiased = new NeuralNetwork(1, 1, i, 1, true);
////                    netbiased.linearOutput(i);
//                    net.train(trainX, trainY, 0.3, 0.5, 0.001, 100000);
//
//                    Double biasednetError = 0.0;
//                    for(int j=0; j<testX.size(); j++) {
//                            Double res =  net.fwdPropagate(new Double [] {testX.get(j)})[0] - testY.get(j);
//                            results.add(new Point(1.0, testX.get(j), res));
//                            biasednetError += res*res;
//                    }
//                    results.addAll(trainPoints);
//                    Main.PlotPoints(results, "wykres funkcji treningowej i aproksymowanej testowej dla " + i + " neuronów w warstwie ukrytej", "x", "y", "zbiór treningowy","funkcja aproksymowana");
//                    results.clear();
//                    biasednetError /= testX.size();
//                    System.out.println("Results of network "+i+": (biased)"+biasednetError+", (nonbiased)"+netError);
		}
                Main.PlotPoints(wszystkiePunkty, "wynik dzialania sieci", "x", "y", "seria1", "seria2");
	}
	
        public static void c(Object o)
        {
            System.out.println(o);
        }
}
