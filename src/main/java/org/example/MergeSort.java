package org.example;
import java.io.*;
import java.util.*;

public class MergeSort {
    private boolean mode;
    private ArrayList<Reader> readers;

    private Writer writer;

    private Comparator comparator;

    private String type;

    MergeSort(){
        mode = true;
        readers = new ArrayList<>();
        type = "int";
        comparator = new Comparator();
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

        for (int i = 0; i < readers.size(); i++) { //считываем первые элементы из каждого файла
            String item = readers.get(i).getItem();
            if (item != null && readers.get(i) != null)
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
                writer.write(ex + "\n"); //записываем в файл макс/мин элемент из массива
                writer.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            getNextItem(indexOfEx, ex, array); //метод заменяет в массиве макс/мин элемент на следующее значение из файла, где был найден макс/мин
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
            else array.set(indexOfEx, (value)); //если все хорошо, то на место элемента, который являлся мин/макс, ставим следующий из этого же файла
        }
        else {
            array.remove(indexOfEx);
            readers.get(indexOfEx).close();
            readers.remove(indexOfEx);
        }
    }

    public boolean check_sort(String item, String nextItem){ //проверяем верно ли отсортирован файл
        if (mode)
            return comparator.a_sort(type, item, nextItem);
        else
            return comparator.d_sort(type, item, nextItem);
    }
    public String extreme(List<String> array){ //в зависимости от режима сортировки ищем макс/мин
        if(mode)
            return comparator.min(array, type);
        else
            return comparator.max(array, type);
    }

}
