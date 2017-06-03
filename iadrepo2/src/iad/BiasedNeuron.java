package iad;

public class BiasedNeuron extends HiddenNeuron {

	public BiasedNeuron(int n, IFunction f) {
		super(n, f);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double nextOutput() {
		return 1.0;
	}
	
	@Override
	public Double lastOutput() {
		return 1.0;
	}
	
}
