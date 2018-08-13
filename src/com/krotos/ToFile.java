package com.krotos;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

class ToFile {

    //zapis historii do pliku txt

    private PrintWriter writer;

    //dodac zapis w nowych plikach

    void write(List<Memory> list) {
        try {
            writer = new PrintWriter("calcHistory.txt", "UTF-8");

            writer.println("https://github.com/mkrotos/Calculator");
            writer.println();
            writer.println("Saved equations: ");
            for (Memory mem : list) {
                writer.println(mem.toString());
            }
            writer.close();
            System.out.println("Saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
