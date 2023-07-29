package org.example;
import java.io.*;
import java.util.*;

public class MergeSort {
    private boolean mode;
    private ArrayList<Reader> readers;

    private Writer writer;

    private String type;

    MergeSort(){
        mode = true;
        readers = new ArrayList<>();
        type = "int";
    }

    public void setMode(boolean f){
        mode = f;
    }
    public void setType(String s){
        type = s;
    }
    public String getType(){
        return type;
    }

    public void setReader(Reader r) {
            readers.add(r);
    }

    public void setWriter(String file) {
        if(file == null) {
            System.out.println("Missing output file name.");
            return;
        }
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void sort(){

        List<String> array = new ArrayList<>();

        for (int i = 0; i < readers.size(); i++) {
            String item = readers.get(i).getItem();
            if (item != null)
                array.add(readers.get(i).getItem());
            else {
                readers.remove(i);
                i--;
            }
        }

        while (readers.size() > 0) {
            String ex = extreme(array);
            int indexOfEx = array.indexOf(ex);

            try {
                writer.write(ex + "\n");
                writer.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            getNextItem(indexOfEx, ex, array);
        }

        try {
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getNextItem(int indexOfEx, String ex, List<String> array){ //проверка следующего на null/верную отсортированность
        String value = readers.get(indexOfEx).getNextItem(type);

        if (value != null){
            if (!check_sort(ex, value))
                getNextItem(indexOfEx, ex, array);
            else array.set(indexOfEx, (value)); //если все хорошо, то на индекс элемента, который являлся мин/макс, ставим следующий из этого же файла
        }
        else {
            array.remove(indexOfEx);
            readers.get(indexOfEx).close();
            readers.remove(indexOfEx);
        }
    }

    public boolean check_sort(String item, String nextItem){ //проверяем верно ли отсортирован файл
        if (mode) {
            if (type.equals("int")) {
                return Integer.parseInt(nextItem) >= Integer.parseInt(item);
            } else {
                return nextItem.compareTo(item) >= 0;
            }
        } else {
            if (type.equals("int"))
                return Integer.parseInt(nextItem) <= Integer.parseInt(item);
            else
                return nextItem.compareTo(item) <= 0;
        }
    }
    public String extreme(List<String> array){ //в зависимости от режима сортировки ищем макс/мин
        if(mode)
            return min(array);
        else
            return max(array);
    }
    public String min(List<String> arr){
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

    public String max(List<String> arr){
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
