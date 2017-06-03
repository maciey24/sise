package iad;

public class OutputNeuron extends Neuron{
	
    /**
     *
     * @param n - number of inputs
     * @param f - Interface
     */
    public OutputNeuron(int n, IFunction f) {
		super(n, f);
	}

	@Override
	public void teach(Double d) {
		error = function.df(sum())* (d-output);
	}

	@Override
	public Double getLesson(Integer i) {
		return weight[i]*error;
	}
    
    public Double getMSE() {
    	Double e = error/function.df(sum());
    	return e*e;
    }
}
