package iad;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Neuron implements Serializable {
    protected Double[] input;
    protected Double[] weight;
    protected Double[] delta;
    protected Double output;
    protected Double error;
    protected IFunction function;

    /**
     *
     * @param n number of inputs
     * @param f 
     */
    protected Neuron(int n, IFunction f) {
    	input = new Double[n];
    	weight = new Double[n];
    	delta = new Double[n];
    	output = 0.0;
    	error = 0.0;

    	function = f;
    	
    	for (int i = 0; i < n; i++) {
			weight[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
			delta[i] = 0.0;
    	}
    }
    
    abstract public void teach(Double d);
    abstract public Double getLesson(Integer i);
    
    public void learn(Double lSpeed, Double lMomentum) {
    	for(int i=0; i<weight.length; i++) {
    		Double wDelta = (lSpeed * input[i] * error) + (delta[i] * lMomentum);
    		weight[i] += wDelta;
    		delta[i] = wDelta;
    	}
    }
    
    public void setInput(Integer i, Double d) {
    	input[i] = d;
    }
    
    public Double nextOutput() {
    	output = function.f(sum());
    	return output;
    }
    
    public Double lastOutput() {
    	return output;
    }
    
    protected Double sum() {
    	Double sum = 0.0;
    	for(int i = 0; i < weight.length; i++) {
    		sum += weight[i] * input[i];
    	}
    	return sum;
    }
    
    public Double distanceTo(Point p) {
    	Double d = 0.0;
    	Double [] vals = p.getVals();
    	for(int i = 0; i<vals.length; i++) {
    		d += (vals[i]-weight[i])*(vals[i]-weight[i]);
    	}
    	return Math.sqrt(d);
    }
}
