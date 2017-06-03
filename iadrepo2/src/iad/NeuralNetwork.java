package iad;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NeuralNetwork implements Serializable {
	private ArrayList<HiddenNeuron[]> neuHidden;
	private OutputNeuron[] neuOutput;
	
	private Double lSpeed = 0.3;
	private Double lMomentum = 0.0;
	
	private SigmoidFunction sigmoid;
	private LinearFunction linear;
	

    /**
           user needs to provide size of input vector,
	 number of layers in network (including output layer)
         and number of neurons per layer 
     * @param iSize input vector size 
     * @param nLayers number of hidden layers with input layer
     * @param nNeuPerLay number of neurons per layer
     * @param nNeuOut number of output neurons
     * @param isBiased is network biased
     */
	public NeuralNetwork(Integer iSize, Integer nLayers, Integer nNeuPerLay, Integer nNeuOut, Boolean isBiased) {
		sigmoid = new SigmoidFunction();
                linear = new LinearFunction();
		neuHidden = new ArrayList<HiddenNeuron[]>();
		
		HiddenNeuron[] neuInput = new HiddenNeuron[nNeuPerLay];
		if(isBiased)
			neuInput[0] = new BiasedNeuron(iSize, sigmoid);
		else
			neuInput[0] = new HiddenNeuron(iSize, sigmoid);
			
		for(int i = 1; i < nNeuPerLay; i++) {
			neuInput[i] = new HiddenNeuron(iSize, sigmoid);
		}
		neuHidden.add(neuInput);
		
		// Create other hidden layers of network
		for(int j = 0; j<nLayers-1; j++) {
			HiddenNeuron[] hidden = new HiddenNeuron[nNeuPerLay];
			for(int i = 0; i < nNeuPerLay; i++) {
				hidden[i] = new HiddenNeuron(nNeuPerLay, sigmoid);
			}
			neuHidden.add(hidden);
		}
		
		// Create output layer
            this.neuOutput = new OutputNeuron[nNeuOut];
            for(int i = 0; i<nNeuOut; i++) {
            	neuOutput[i]= new OutputNeuron(nNeuPerLay, linear);
            }
	}

	// constructor only used for approximation
	public NeuralNetwork(Integer nNeurons, Boolean isBiased) {
		linear = new LinearFunction();
		sigmoid = new SigmoidFunction();
		neuHidden = new ArrayList<HiddenNeuron[]>();
		
//		HiddenNeuron[] neuInput = (isBiased) ? new HiddenNeuron[2] : new HiddenNeuron[1];
//		neuInput[0] = new HiddenNeuron(1, sigmoid);
//		if(isBiased)
//			neuInput[neuInput.length-1] = new BiasedNeuron(1, sigmoid);
//		neuHidden.add(neuInput);
		
		HiddenNeuron[] hidden = new HiddenNeuron[nNeurons];
		for(int i = 0; i < hidden.length; i++) {
			hidden[i] = (isBiased) ? new HiddenNeuron(2, sigmoid) : new HiddenNeuron(1, sigmoid);
		}
		
		neuHidden.add(hidden);
		
        this.neuOutput = new OutputNeuron[1];
        neuOutput[0]= new OutputNeuron(nNeurons, linear);
	}
	
	public Double[] getOutput() {
        Double[] res = new Double[neuOutput.length];
        for(int i=0; i<neuOutput.length; i++) {
            res[i] =  neuOutput[i].lastOutput();
        }
        return res;
	}
	
	private void setInput(Double[] d) {
		HiddenNeuron[] neuInput = neuHidden.get(0);
		for(int i = 0; i<neuInput.length; i++) {
			neuInput[i].setInput(d);
		}
	}
	
	private Double calcMSE(Double[] x, Double[] xi) {
		Double res = 0.0;
		for(int i=0; i<x.length; i++) {
			res += (x[i]-xi[i]) * (x[i]-xi[i]);
		}
		return res;
	}
	
	public Double[] fwdPropagate(Double [] d) {
		setInput(d);
	    Double temp;
	    
	    // Propagate forward through network
	    for (int i=0; i<neuHidden.size()-1; i++) {
		    for(int j=0; j<neuHidden.get(i).length; j++) {
				temp = neuHidden.get(i)[j].nextOutput();
				for(int c=0; c<neuHidden.get(i+1).length; c++) {
					neuHidden.get(i+1)[c].setInput(j, temp);
				}
		    }
		}
	    
	    // Pass signals to output layer
	    for(int i = neuHidden.size()-1, j = 0; j<neuHidden.get(i).length; j++) {
			temp = neuHidden.get(i)[j].nextOutput();
            for (int a= 0; a<neuOutput.length; a++) {
                neuOutput[a].setInput(j, temp);
            }
	    }
	    
	    // Get and return results from output layer
        Double[] res = new Double[neuOutput.length];
        for(int a=0; a<neuOutput.length; a++) {
            res[a]= neuOutput[a].nextOutput();
        }
        return res;
	}
	
	/**
    	training routine, returns ordered list of errors
	 * @param tInput ArrayList of arrays of doubles, each is single input for network
	 * @param tOutput ArrayList of arrays of doubles, each is single EXPECTED output for network
	 * @param iSpeed constant learning speed for network
	 * @param iMomentum constant momentum of learning
	 * @param maxError upon reaching MSE lower than maxError, learning stops
	 * @param maxEpochs upon working longer than maxEpochs, learning stops
     * @return 
	 */
	public ArrayList<Double> train(ArrayList<Double[]> tInput, ArrayList<Double[]> tOutput, 
			Double iSpeed, Double iMomentum, Double maxError, Integer maxEpochs) {
		lSpeed = iSpeed;
		lMomentum = iMomentum;
		
		ArrayList<Double> MSE = new ArrayList<Double>();
                int epoch;
		
		for(epoch=0; epoch<maxEpochs; epoch++) {
			Double epochMSE = 0.0;
			for(int i=0; i<tInput.size(); i++) {
				final Double[] currentInput = tInput.get(i);
				final Double[] expectedOutput = tOutput.get(i);
				final Double[] currentOutput = fwdPropagate(currentInput);
				bckPropagate(expectedOutput);
				epochMSE += calcMSE(currentOutput, expectedOutput);
			}
			epochMSE /= tInput.size();
			//if(epoch%10 == 0)
				System.out.println("Epoch " + epoch + " MSE: " + epochMSE);
			if(Double.isNaN(epochMSE))
				return null;
			
			// it is possible to modulate momentum
			/*if(lMomentum != 0 && MSE.size() > 5) {
				Double dMSE = MSE.peek() - epochMSE;
				System.out.println("Epoch " + epoch + " MSE: " + epochMSE + " MSE diff " + dMSE);
				if(dMSE > 0.0)
					lMomentum *= 1.02;
				else
					lMomentum = iMomentum;
			} */
			
			MSE.add(epochMSE);
			if(epochMSE<maxError)
				break;
		}
                //cout("training finished, last epochMSE: "+ MSE.get(MSE.size()-1).toString()+" number of epochs: " + epoch);
		return MSE;
	}
	
	public void trainCompetitive() {
		
	}
	
	// assumed there is some data inside already
	// teach > getLesson > learn
	private void bckPropagate(Double [] expected) {
        // Start with output layer, only get its errors and lessons
        for(int a=0; a<neuOutput.length; a++) {
            final OutputNeuron oNeu = neuOutput[a];
            oNeu.teach(expected[a]);

            // pass signal to hidden layers
            for (int j=0, i=neuHidden.size()-1; j<neuHidden.get(i).length; j++) {
                neuHidden.get(i)[j].teach(oNeu.getLesson(j));
            }
        	oNeu.learn(lSpeed, lMomentum);
        }
		
        // Proceed to hidden layers
        for (int i=neuHidden.size()-1; i >= 0; i--) {
            for (int j=0; j<neuHidden.get(i).length; j++) {
                final HiddenNeuron hNeu = neuHidden.get(i)[j];

                // only non-input hidden neurons can back propagate signal
                if(i>0) {
                    for (int k=0; k<neuHidden.get(i-1).length; k++)
                        neuHidden.get(i-1)[k].teach(hNeu.getLesson(k));
                }
                hNeu.learn(lSpeed, lMomentum);
            }
        }
    }

    private void cout(Object s) {
        System.out.println(s);
    }
}
