package com.krotos;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

class ToFile {
    private static final String UTF_8 = "UTF-8";
    private static final String FILE_NAME = "calcHistory.txt";
    private static final String MY_GITHUB_URL = "https://github.com/mkrotos/Calculator";

    //zapis historii do pliku txt

    //dodac zapis w nowych plikach

    void write(List<Memory> list) {
        try {
            PrintWriter writer = new PrintWriter(FILE_NAME, UTF_8);

            writer.println(MY_GITHUB_URL);
            writer.println();
            writer.println("Saved equations: ");
            for (Memory mem : list) {
                writer.println(mem.toString());
            }
            writer.close();
            System.out.println("Saved");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
