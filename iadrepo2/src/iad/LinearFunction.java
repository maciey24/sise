package iad;

public class LinearFunction implements IFunction {

	public LinearFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double f(Double d) {
		return d;
	}

	@Override
	public double df(Double d) {
		return 1.0;
	}

}
