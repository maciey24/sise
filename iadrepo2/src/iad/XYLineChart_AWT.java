package iad;

import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection;

@SuppressWarnings("serial")
public class XYLineChart_AWT extends ApplicationFrame 
{
	public XYLineChart_AWT( String applicationTitle, String chartTitle, List<Point> pnts)
	{
	   super(applicationTitle);
	   JFreeChart xylineChart = ChartFactory.createScatterPlot(
         chartTitle ,
         "X1" ,
         "X2" ,
         createDataset(pnts) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
	   ChartPanel chartPanel = new ChartPanel( xylineChart );
	   chartPanel.setPreferredSize( new java.awt.Dimension( 600 , 600 ) );
	   setContentPane( chartPanel ); 
   }
   public XYLineChart_AWT( String applicationTitle, String chartTitle, String osx, String osy, String ser1, String ser2, List<Point> pnts)
	{
	   super(applicationTitle);
	   JFreeChart xylineChart = ChartFactory.createScatterPlot(
         chartTitle ,
         osx ,
         osy ,
         createDataset(pnts) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
	   ChartPanel chartPanel = new ChartPanel( xylineChart );
	   chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	   setContentPane( chartPanel ); 
   }
	public XYLineChart_AWT( String applicationTitle, String chartTitle, List<Double> lst1, List<Double> lst2, String s1, String s2, String osy, String osx)
	{
	   super(applicationTitle);
	   JFreeChart xylineChart = ChartFactory.createScatterPlot(
         chartTitle ,
         osy ,
         osx ,
         createDataset(lst1, lst2, s1, s2) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
	   ChartPanel chartPanel = new ChartPanel( xylineChart );
	   chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	   setContentPane( chartPanel ); 
   }        
   
//   private XYDataset createDataset(List<Point> points)
//   {
//	  final XYSeries g1 = new XYSeries( "G1" );
//	  final XYSeries g2 = new XYSeries( "G2" );
//	  for (Point p : points) {
//		  if(p.tag <= 0.5)
//			  g1.add(p.val.get(0), p.val.get(1));
//		  else if (p.tag > 0.5)
//			  g2.add(p.val.get(0), p.val.get(1));
//	  }
//	  final XYSeriesCollection dataset = new XYSeriesCollection( );          
//      dataset.addSeries( g1 );
//      dataset.addSeries( g2 );
//      return dataset;
//   }   
   private XYDataset createDataset(List<Point> points)
   {
       ArrayList<Double> seriesTags = new ArrayList<>();
       for(Point p : points)
       {
           if(!seriesTags.contains(p.tag)) seriesTags.add(p.tag);
       }
       ArrayList<XYSeries> seriesList = new ArrayList<>();
       for(int i = 0; i<seriesTags.size(); i++)
       {
           String s = "seria "+i;
           seriesList.add(new XYSeries(s));
       }
	  for (Point p : points) 
          {
              seriesList.get(seriesTags.indexOf(p.tag)).add(p.val.get(0), p.val.get(1));
//              
//		  if(p.tag <= 0.5)
//			  g1.add(p.val.get(0), p.val.get(1));
//		  else if (p.tag > 0.5)
//			  g2.add(p.val.get(0), p.val.get(1));
	  }
	  final XYSeriesCollection dataset = new XYSeriesCollection( );
          for (XYSeries x : seriesList)
          {
              dataset.addSeries(x);
          }
      return dataset;
   }
   
   private XYDataset createDataset(List<Double> lst1, List<Double> lst2, String s1, String s2)
   {
	  final XYSeries g1 = new XYSeries( s1);
          if(s2==null) s2=" ";
	  final XYSeries g2 = new XYSeries( s2 );
	  for (int i =0; i<lst1.size(); i++) {
		g1.add(i, lst1.get(i));
	  }
          if(lst2!=null){
	  for (int i =0; i<lst2.size(); i++) {
		g2.add(i, lst2.get(i));
	  }}
          
	  final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( g1 );
      dataset.addSeries( g2 );
      return dataset;
   }
}