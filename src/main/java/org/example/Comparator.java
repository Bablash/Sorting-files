package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comparator {

    public boolean a_sort(String type, String item, String nextItem){
        if (type.equals("int"))
            return Integer.parseInt(nextItem) >= Integer.parseInt(item);
        else
            return nextItem.compareTo(item) >= 0;
    }

    public boolean d_sort(String type, String item, String nextItem){
        if (type.equals("int"))
            return Integer.parseInt(nextItem) <= Integer.parseInt(item);
        else
            return nextItem.compareTo(item) <= 0;
    }

    public String min(List<String> arr, String type){
        String min;
        List<Integer> array_int = new ArrayList<>();
        if (type.equals("int")){
            for (String s : arr)
                array_int.add(Integer.parseInt(s));
            min = Integer.toString(Collections.min(array_int));
        }
        else
            min = Collections.min(arr);
        return min;
    }

    public String max(List<String> arr, String type){
        String max;
        List<Integer> array_int = new ArrayList<>();
        if (type.equals("int")){
            for (String s : arr)
                array_int.add(Integer.parseInt(s));
            max = Integer.toString(Collections.max(array_int));
        }
        else
            max = Collections.max(arr);
        return max;
    }
}
