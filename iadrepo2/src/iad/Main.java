package iad;

import java.io.*;
import java.util.*;

import org.jfree.chart.plot.Plot;
import org.jfree.ui.RefineryUtilities;

public class Main {
	private static List<Point> trainPoints = new ArrayList<Point>();
	private static List<Point> unknownPoints = new ArrayList<Point>();
	public static void main(String[] args){
            //ReadFile("src/iad/input.txt");
            //trainPoints.add(new Point(0.0, 0.0, 4.0));
            //trainPoints.add(new Point(1.0, 4.0, 0.0));

            //PlotPoints(trainPoints);
            NeuralNetwork net = new NeuralNetwork(2, 3, 3, 1, true);
//            redOrBluePointsLearn(net);
//            dao.saveNN(net, "RedorBluepoints.ser");
//            NeuralNetwork net = dao.restore("RorBpoints.txt");
            
            ArrayList<Double[]> trainInput = new ArrayList<>();
            trainInput.add(new Double[] {0.0, 4.0});
            trainInput.add(new Double[] {4.0, 0.0});
            
            ArrayList<Double[]> trainOutput = new ArrayList<>();
            trainOutput.add(new Double[] {1.0});
            trainOutput.add(new Double[] {0.0});
            
            net.train(trainInput, trainOutput, 0.3, 0.0, 0.005, 50000);
            
            redOrBluePointsClassify(net);
	}
	
	
	static void ReadFile(String fpath)
	{
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fpath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
		  while (true) {
		    String line = reader.readLine();
		    if (line == null) break;
		    String[] fields = line.split(",");
		    Point p = new Point();
		    p.tag = Double.parseDouble(fields[0]);
		    for(int i = 1; i < fields.length; i++) {
		    	p.val.add(Double.parseDouble(fields[i]));
		    }
		    trainPoints.add(p);
		  }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader != null){
				try {
	                reader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}
		}	
	}

	static void PlotPoints(List<Point> lst)
	{
            XYLineChart_AWT chart = new XYLineChart_AWT("T1", "T2", lst);
	    chart.pack( );          
	    RefineryUtilities.centerFrameOnScreen( chart );          
	    chart.setVisible( true );
	}	
        static void PlotPoints(List<Point> lst, String tytul, String osx, String osy, String ser1, String ser2)
	{
            XYLineChart_AWT chart = new XYLineChart_AWT("T1", tytul, osx, osy, ser1, ser2, lst);
	    chart.pack( );          
	    RefineryUtilities.centerFrameOnScreen( chart );          
	    chart.setVisible( true );
	}
	static void PlotList(List<Double> lst, String seria1, String seria2, String osy, String osx)
	{
            XYLineChart_AWT chart = new XYLineChart_AWT("T1", "Błąd średniokwadratowy w zależności od numeru epoki", lst, null, seria1, seria2, osy, osx);
	    chart.pack( );          
	    RefineryUtilities.centerFrameOnScreen( chart );          
	    chart.setVisible( true );
	}
	static void PlotPoints(List<Double> lst1, List<Double> lst2, String s1, String s2, String osy, String osx)
	{
            XYLineChart_AWT chart = new XYLineChart_AWT("T1", "Odpowiedź sieci na wejście [0 0 0]", lst1, lst2, s1, s2, osy, osx);
	    chart.pack( );          
	    RefineryUtilities.centerFrameOnScreen( chart );          
	    chart.setVisible( true );
	}
        
        private static void redOrBluePointsLearn(NeuralNetwork net)
        {		            
            for (int i = 0; i<10000; i++) {
                System.out.println("\nTraining " + i);
                for(int j = 0; j<trainPoints.size(); j++) {
                    Point p = trainPoints.get(j);
                    net.fwdPropagate(p.getVals());
//                    net.bckPropagate(new Double[] {p.tag});
                    System.out.println(net.getOutput());
                }
            }
            System.out.println("After all trainings: " + net.getOutput());
        }
        
        private static void redOrBluePointsClassify(NeuralNetwork net)
        {
		Point p = new Point();
		p.val.add(1.0);
		p.val.add(2.5);
		p.tag = net.fwdPropagate(p.getVals())[0]; 
		System.out.println(p.tag);
		unknownPoints.add(p);
		
		p = new Point();
		p.val.add(1.2);
		p.val.add(0.5);
		p.tag = net.fwdPropagate(p.getVals())[0];
		System.out.println(p.tag);
		unknownPoints.add(p);
		
		p = new Point();
		p.val.add(3.8);
		p.val.add(2.0);
		p.tag = net.fwdPropagate(p.getVals())[0];
		System.out.println(p.tag);
		unknownPoints.add(p);
                
		p = new Point();
		p.val.add(1.8);
		p.val.add(3.1);
		p.tag = net.fwdPropagate(p.getVals())[0];
		System.out.println(p.tag);
		unknownPoints.add(p);
                
		p = new Point();
		p.val.add(2.0);
		p.val.add(1.0);
		p.tag = net.fwdPropagate(p.getVals())[0];
		System.out.println(p.tag);
		unknownPoints.add(p);
		PlotPoints(unknownPoints);
        }


}
