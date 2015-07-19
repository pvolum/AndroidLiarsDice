package com.example.patrick.liarsdice;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by patrick on 7/16/15.
 */
public class Player {
    String name;
    ArrayList<Integer> hand;
    ArrayList<Integer> showing;

    Random r;

    public Player(int i){
        name = ("Player " + i);
        hand = new ArrayList<Integer>();
        showing = new ArrayList<Integer>();
        r = new Random();

        for (int t = 0; t < 5 ; t++){
            hand.add(r.nextInt(6) + 1);
        }
    }

    public void newHand() {
        int size = hand.size();
        hand.clear();
        for (int t = 0; t < size; t++) {
            hand.add(r.nextInt(6) + 1);
        }
    }

}
