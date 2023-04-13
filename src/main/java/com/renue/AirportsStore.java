package com.renue;

import java.util.ArrayList;
import java.util.List;

public class AirportsStore {
    private List<Pair> pairs;

    public AirportsStore(){
        pairs = new ArrayList<>();
    }

    public void Add(String name, String csv){
        var item = new Pair(name, csv);
        pairs.add(item);
    }

    public void Sort(){
        pairs.sort((o1, o2) -> CompareSort(o1.name, o2.name));
    }

    public List<Pair> FindStartWith(String name){
        List<Pair> p = new ArrayList<>();
        var index= binarySearch(name, 0, pairs.size() - 1);
        var i = index;
        if (index == -1)
            return p;
        while (i > -1 && pairs.get(i).name.startsWith(name)){
            i--;
        }
        i++;
        while (i < pairs.size() && pairs.get(i).name.startsWith(name)){
            p.add(pairs.get(i));
            i++;
        }
        return p;
    }

    private int binarySearch(String valueToFind, int low, int high) {
        int index = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            var pair = pairs.get(mid);
            if (CompareTo(pair.name, valueToFind) < 0) {
                low = mid + 1;
            } else if (CompareTo(pair.name, valueToFind) > 0) {
                high = mid - 1;
            } else if (CompareTo(pair.name, valueToFind) == 0) {
                index = mid;
                break;
            }
        }
        return index;
    }

    private int CompareSort(String a, String b){
        var i = CompareTo(a, b);
        if (i == 0)
            return a.length() - b.length();
        return i;
    }
    private int CompareTo(String name, String substring){
        int len = Math.min(substring.length(), name.length());
        for(var i = 0; i < len; i++)
            if (name.charAt(i) != substring.charAt(i))
                return name.charAt(i) - substring.charAt(i);
        return 0;
    }

    public class Pair {
        String name;
        String csv;

        public Pair(String name, String csv){
            this.name = name;
            this.csv = csv;
        }
    }
}
