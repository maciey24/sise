package iad;

import java.util.ArrayList;

public class Point {
		public ArrayList<Double> val = new ArrayList<Double>();
		public Double tag;
                public boolean isCentroid = false;
		
		public Point(Double t, Double d1, Double d2) {
			tag = t;
			val.add(d1);
			val.add(d2);
		}
                
		public Point(Double t, ArrayList<Double> coords) {
			tag = t;
                        val.addAll(coords);
		}
                
		public Point(Double t, Double [] d) {
			tag = t;
			for(int i = 0; i<d.length; i++) {
				val.add(d[i]);
			}
		}
		
		public Point() {
			
		}
		
		public Double [] getVals() {
			Double [] vals = new Double [val.size()];
			for(int i=0; i<val.size(); i++) {
				vals[i] = val.get(i);
			}
			return vals;
		}
		
		public Double distanceTo(Point other) {
			Double dist = 0.0;
			for(int i = 0; i < this.val.size() && i < other.val.size(); i++) {
				final Double d = other.val.get(i) - this.val.get(i);
				dist += d*d;
			}
			return Math.sqrt(dist);
		}
		
		public Double rateMatch(Neuron n) {
			Double dist = 0.0;
			for(int i = 0; i < this.val.size() && i < n.weight.length; i++) {
				final Double d = n.weight[i] - this.val.get(i);
				dist += d*d;
			}
			return Math.sqrt(dist);
		}
                
                @Override
                public String toString()
                {
                    String s = "";
                    for (Double d : val)
                    {
                        s +=d;
                        s+=" ";
                    }
                    s+=tag;
//                    System.out.println(s);
                    return s;
                }
}
