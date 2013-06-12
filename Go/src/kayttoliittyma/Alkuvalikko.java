/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.util.Scanner;

/**
 *
 * @author Prod
 */
public class Alkuvalikko {
    
    public Alkuvalikko() {
    Scanner input = new Scanner(System.in);
    while (true) {
        System.out.println("\"pelaa\" aloittaa uuden pelin, \"lapikay\" siirtyy pelin lapikayntiin ja \"lopeta\" lopettaa");
        String syote = input.nextLine();
        if (syote.equals("pelaa")) {
            Pelikayttoliittyma kayttoliittyma = new Pelikayttoliittyma();
            break;
        }
        if (syote.equals("lapikay")) {
            Lapikayntikayttoliittyma kayttoliittyma = new Lapikayntikayttoliittyma();
            break;
        }
        if (syote.equals("lopeta")) break;
    }
    }
    
}
