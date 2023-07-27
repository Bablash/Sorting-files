package org.example;


public class Main {
    public static void main(String[] args) {
        boolean sort_mode = false;
        MergeSort myMergeSort = new MergeSort();

        if (args.length >= 3) {
            for (int i = 0; i < 2; i++) {
                if (args[0].equals("-a") || args[0].equals("-d")) {
                    sort_mode = true;
                    if (args[0].equals("-d"))
                        myMergeSort.setMode(false);
                    i++;
                }
                if (args[i].equals("-i")) {
                    break;
                }
                if (args[i].equals("-s")){
                    myMergeSort.setType("string");
                    break;
                }
            }

            int indexOfReader;
            if(sort_mode) {
                myMergeSort.setWriter(args[2]);
                indexOfReader = 3;
            }
            else {
                myMergeSort.setWriter(args[1]);
                indexOfReader = 2;
            }

            if (args.length > indexOfReader)
                for(int j = indexOfReader; j < args.length; j++)
                    myMergeSort.setReader(new Reader(args[j], myMergeSort.getType()));
            else {
                System.out.println("Input file names are missing.");
                return;
            }
            myMergeSort.sort();
        }
        else
            System.out.println("Invalid arguments of command line arguments.");
    }
}