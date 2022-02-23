package com.wildezhawer.hashcode.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReaderService {

    private final static String DATA_FOLDER = System.getProperty("user.dir") + "/data/";


    public List<String> read(String filename) throws Exception {
        List<String> lines = new ArrayList<>();

        try {
            File myObj = new File(DATA_FOLDER + filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception();
        }

        return lines;
    }
}
