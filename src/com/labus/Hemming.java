/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labus;

import java.util.BitSet;

/**
 * @author Slavik
 */
public class Hemming {
    private static Hemming instance;


    public static Hemming getInstance() {
        if (instance == null) {
            instance = new Hemming();
        }
        return instance;
    }

    public void run() {
        String hex1 = "FFFFFF0";
        String hex2 = "FFFFFFF";
        hammingDistance(hex1, hex2);
    }

    public void print(Object text) {
        System.out.println(text);
    }

    public void hammingDistance(String hex1, String hex2) {
        BitSet first = initBitSet(hex1);
        BitSet second = initBitSet(hex2);
        print(first);
        print(second);
        first.xor(second);
        print(first);
        print("similarity measure: " + (float) first.cardinality() / (float) second.length() * 100 + "%");
        print("Hamming distance: " + first.cardinality());
    }

    public BitSet initBitSet(String hex) {
        String binary_rep = hexToBinary(hex);
        BitSet result = new BitSet(binary_rep.length());
        int i = 0;
        for (char symbol : binary_rep.toCharArray()) {
            result.set(i, (symbol == '1' ? true : false));
            i++;
        }
        return result;
    }

    public String hexToBinary(String hex) {
        int i = Integer.parseInt(hex, 16);
        String bin = Integer.toBinaryString(i);
        return bin;
    }
}
