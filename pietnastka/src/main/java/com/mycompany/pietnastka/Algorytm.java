/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pietnastka;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author alicj
 */
public abstract class Algorytm {

    Uklad u;
    String strategia;
    ArrayList<String> listaOdwiedzonych;  

    Algorytm(Uklad u, String strategia) throws Uklad.PoprawnyUkladException {
        this.u = u;
        this.strategia = strategia;
        listaOdwiedzonych = new ArrayList<>();
    }

    abstract void przesuwanie() throws Uklad.PoprawnyUkladException;
}
