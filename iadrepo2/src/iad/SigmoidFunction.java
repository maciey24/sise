package iad;

import java.io.Serializable;
//http://pl.numberempire.com/graphingcalculator.php?functions=(1)%2F(1%20%2B%20exp(-x))%2C%20(1)%2F(1%20%2B%20exp(-x))*(1-(1)%2F(1%20%2B%20exp(-x)))%2C&xmin=-24.759233&xmax=29.810462&ymin=-1.969878&ymax=1.936373&var=x
public class SigmoidFunction implements IFunction, Serializable{

	@Override
	public double f(Double d) {
		return 1.0f / (1.0f + (double) Math.exp(-d));
	}

	@Override
	public double df(Double d) {
		//return d*(1-d);
		return f(d)*(1-f(d));
	}

}
