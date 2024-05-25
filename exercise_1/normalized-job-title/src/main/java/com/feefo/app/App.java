package com.feefo.app;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        Normaliser normaliser = new Normaliser();

        System.out.println(normaliser.normalise(input));
    }
}
