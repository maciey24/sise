/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iad;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maciek
 */
public class dao {
    static void saveNN(NeuralNetwork net, String name) {
        try (FileOutputStream fo = new FileOutputStream(name)) {
        	try (ObjectOutputStream o = new ObjectOutputStream(fo)) {
        		o.writeObject(net);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NeuralNetwork.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        	Logger.getLogger(NeuralNetwork.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    //TODO: choose filename freely
    // maybe constructor from file?
    static NeuralNetwork restore(String name) {
        try (FileInputStream fi = new FileInputStream(name)) { //"../netSav.txt"
            try (ObjectInputStream o = new ObjectInputStream(fi)) {
                return (NeuralNetwork) o.readObject();
            } catch (ClassNotFoundException ex) {
              	Logger.getLogger(NeuralNetwork.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NeuralNetwork.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NeuralNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
