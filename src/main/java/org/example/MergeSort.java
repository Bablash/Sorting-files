package org.example;
import java.io.*;
import java.util.*;

public class MergeSort {
    private boolean mode;
    private ArrayList<Reader> readers;

    private FileWriter writer;

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
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sort(){

        List<String> array = new ArrayList<>(readers.size());
        for (Reader reader : readers) array.add(reader.getItem());

        while (readers.size() > 0) {
            String ex = extreme(array);

            int indexOfEx = array.indexOf(ex);
            try {
                writer.write(ex + "\n");
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            checkNextItem(indexOfEx, ex, array);
        }
    }

    public void checkNextItem(int indexOfEx, String ex, List<String> array){ //проверка следующего на null/содержание пробела/верную отсортированность
        String value = readers.get(indexOfEx).getNextItem(type);

        if (value != null){
            if (!check_sort(ex, value))
                checkNextItem(indexOfEx, ex, array);
            else array.set(indexOfEx, (value)); //если все хорошо, то на индекс элемента, который являлся мин/макс, ставим следующий из этого же файла
        }
        else {
            array.remove(indexOfEx);
            readers.remove(indexOfEx);
        }
    }

    public boolean check_sort(String item, String nextItem){ //проверяем верно ли отсортирован файл
        if (mode) {
            if (type.equals("int")) {
                if (Integer.parseInt(nextItem) >= Integer.parseInt(item))
                    return true;
                else return false;
            } else {
                if (nextItem.compareTo(item) >= 0)
                    return true;
                else return false;
            }
        } else {
            if (type.equals("int")) {
                if (Integer.parseInt(nextItem) <= Integer.parseInt(item))
                    return true;
                else return false;
            } else {
                if (nextItem.compareTo(item) <= 0)
                    return true;
                else return false;
            }
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
        int min_int;
        if (type.equals("int")){
            min_int = Integer.parseInt(arr.get(0));
            for (int i = 1; i < arr.size(); i++) {
                if (Integer.parseInt(arr.get(i)) < min_int) {
                    min_int = Integer.parseInt(arr.get(i));
                }
            }
            min = Integer.toString(min_int);
        }
        else{
            min = Collections.min(arr);
        }
        return min;
    }

    public String max(List<String> arr){
        String max;
        int max_int;
        if (type.equals("int")){
            max_int = Integer.parseInt(arr.get(0));
            for (int i = 1; i < arr.size(); i++) {
                if (Integer.parseInt(arr.get(i)) > max_int) {
                    max_int = Integer.parseInt(arr.get(i));
                }
            }
            max = Integer.toString(max_int);
        }
        else{
            max = Collections.max(arr);
        }
        return max;
    }
}
