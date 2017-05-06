/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pietnastka;

/**
 *
 * @author maciek
 */
public class DFS {
    Uklad u;
    String strategia;
    
    DFS(Uklad u, String strategia)
    {
        this.u = u;
        this.strategia = strategia;
        przesuwanie(u.toString());
    }
    
    private void przesuwanie(String uklad)
    {
        for(byte i = 0; i<strategia.length(); i++)
        {
            if(u.przesun(strategia.charAt(i))) przesuwanie(u.toString());
            if(u.toString().equals(uklad)) return;
        }
    }
}
