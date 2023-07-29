package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private String item;
    BufferedReader reader;
    public Reader(String file, String type){

        try {
            reader = new BufferedReader(new FileReader(file));
            String value = getNextItem(type);
            if (value != null)
                item = value;
            else
                System.out.println("Empty file: " + file);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getItem(){
        return item;
    }

    public String getNextItem(String type){
        try {
            String value = reader.readLine();
            item = value;
            boolean isInt = false;

            if (type.equals("int") && value!= null) { //если сортируем int и считали не int с файла
                isInt = isInt(value);
                if(!isInt)
                    getNextItem(type);
            }
            else if(value != null && (value.contains(" ")))
                getNextItem(type);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    private static boolean isInt(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void close(){
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
