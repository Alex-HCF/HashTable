package com.company;


import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

class MyPair
{
    public int first;
    public double second;
}

public class Main {

    public static void main(String[] args) throws Exception {

//        HashTable <String, String> test = new HashTable<>();
//
//        test.put("key1", "val1");
//
//        test.put("key2", "val2");
//        test.put("key2", "val22");
//
//        test.put("key3", "val3");
//        test.put("key4", "val4");
//
//        test.remove("key3");
//        System.out.println(test.toString());


        HashTable <String, String> dataTable = new HashTable<>();

        HashMap<String, String> other = new HashMap<>();

        int countInterv = 10;
        MyPair [] analists = new MyPair[countInterv];


        FileReader fr = new FileReader("data.txt");
        Scanner sc = new Scanner(fr);

        while (sc.hasNext())
        {
            String temp = sc.nextLine();

            Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            Matcher  m = p.matcher(temp);

            int start = 0;
            int end = 0;
            while (m.find()) {
                start = m.start();
                end = m.end();
                dataTable.put(temp.substring(start, end), temp.substring(end+1));
                break;
            }

            double coeffFill = dataTable.coeffFill();
            double countCollisions = dataTable.countCollisions();

            int inerval = 0;

            for (int i = 0; i < countInterv; i++)
            {
                if (coeffFill <= (double)(i+1)/countInterv && coeffFill > (double)i/countInterv){
                    inerval = i;
                    break;
                }
            }

            if (analists[inerval] == null)
                analists[inerval] = new MyPair();
            analists[inerval].first += countCollisions;
            analists[inerval].second++;
        }

        int i = 0;
        for (; i < countInterv && (double)(i+1)/countInterv < 0.75; i++)
        {
            if (analists[i] == null)
                break;
            System.out.print("Coefficient Fill: " + (double)i*100/countInterv + "% - " +  (double)(i+1)*100/countInterv + "%  " );
            System.out.println("Count Collisions: " + (analists[i].first/analists[i].second));
        }
        if (analists[i] != null){
            System.out.print("Coefficient Fill: " + (double) i * 100 / countInterv + "% - " + "75%  ");
            System.out.println("Count Collisions: " + (analists[i].first / analists[i].second));
        }


//        System.out.println(dataTable.countCollisions());
//        System.out.println(dataTable.toString());



    }
}
