package iad;

public class GaussianFunction implements IFunction {
	private Double beta;
	
	public GaussianFunction(Double b) {
		beta = b;
	}

	@Override
	public double f(Double d) {
		return Math.exp(beta*d);
	}

	@Override
	public double df(Double d) {
		return beta*Math.exp(beta*d);
	}

}
