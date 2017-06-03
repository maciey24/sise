package iad;

public class HiddenNeuron extends Neuron {
    private Double lessons;
    
    public HiddenNeuron(int n, IFunction f) 
    {
    	super(n, f);
    	lessons = 0.0;
    }
    
    public void setInput(Double[] d) {
    	Double sum = 0.0;
    	for(int i = 0; i < input.length; i++) {
    		if(i < d.length)
    			input[i] = d[i];
    		else
    			input[i] = 1.0;
    		sum += input[i] * weight[i];
    	}
    	//System.out.println(input[0] +" "+ input[1]);
    	output = function.f(sum);
    }
    
    @Override
    public void learn(Double lSpeed, Double lMomentum){
    	error = lessons * function.df(sum());
    	super.learn(lSpeed, lMomentum);
    	
    	lessons = 0.0;
    }
    
    @Override
    public void teach(Double lesson) {
    	lessons += lesson;
    }
//*input[i]*function.df(sum())
	@Override
	public Double getLesson(Integer i) {
		return weight[i]* lessons * function.df(sum());
	}
}
