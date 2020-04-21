package com.company;


import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Optional;
import static java.lang.Math.abs;

public class HashTable <K, V>
{
    private int countCells = 64;
    private static final double LIMIT_FILL = 0.75;

    private ArrayList<Pair<K, V>> [] table;
    private int countPairs = 0;
    private int countUseCells = 0;

    public HashTable ()
    {
        table = new ArrayList[countCells];
    }

    public int getCountCells() {
        return countCells;
    }

    public int getCountPairs() {
        return countPairs;
    }

    public int getCountUseCells() {
        return countUseCells;
    }

    public double coeffFill ()
    {
        return (double) getCountPairs()/getCountCells();
    }

    public int countCollisions()
    {
        return countPairs - countUseCells;
    }


    public void put (K key, V value)
    {
        if (coeffFill() >= LIMIT_FILL)
            tableExtension();

        int hash = hashFunc(key);

        if (table[hash] == null || table[hash].isEmpty())
            countUseCells++;

        if (table[hash] == null){
            table[hash] = new ArrayList<>();
        } else {
            table[hash].removeIf(othPair -> othPair.getKey().equals(key));
        }

        table[hash].add(new Pair<>(key, value));
        countPairs++;

    }


    public void remove (K key)
    {
        int hash = hashFunc(key);

        if (table[hash] == null)
            return;

        table[hash].removeIf(pair -> pair.getKey().equals(key));
        countPairs--;

        if (table[hash].isEmpty())
            countUseCells--;

    }

    public V get (K key)
    {
        int hash = hashFunc(key);

        if (table[hash] != null){
            Optional <Pair<K,V>> temp = table[hash].stream().filter(pair -> pair.getKey().equals(key)).findFirst();
            if (temp.isPresent())
                return temp.get().getValue();
        }

        return null;
    }



    private void tableExtension ()
    {
        countPairs = 0;
        countUseCells = 0;

        ArrayList<Pair<K, V>>[] oldTable = table;

        countCells *= 2;
        table = new ArrayList[countCells];

        for (ArrayList<Pair<K, V>> list : oldTable)
        {
            if (list != null)
                list.stream().forEach(pair -> put(pair.getKey(), pair.getValue()) );
        }
    }


    private int hashFunc (K key)
    {
        return abs(key.hashCode() % countCells);
    }

    @Override
    public String toString ()
    {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (ArrayList<Pair<K, V>> list : table)
        {
            result.append(i++ + ":  ");
            if (list != null && !list.isEmpty())
            {
                result.append(list.toString());
                result.append("\n");
            } else {
                result.append("\n");
            }
        }

        return result.toString();
    }
}
