package com.fakenews.service;

import org.springframework.stereotype.Service;

import java.io.*;
@Service
public class NewsApiService {

    public void writeApiResponseInFileAfterClose(String id) throws IOException {
        File file = new File("NewsApiResponse.txt");

        file.createNewFile();

        FileWriter writer = new FileWriter(file, true);
        writer.append('\n');
        writer.write(id);
        writer.flush();
        writer.close();

        try {
            writer.write("writeSomethingAfterClose");
        }catch (Exception e){
        // nu fac nimic ca sa se vada ca eroarea o arunca din MOP
      }
    }


    public void writeApiResponseInFile(String id) throws IOException {
        File file = new File("NewsApiResponse.txt");

        file.createNewFile();

        FileWriter writer = new FileWriter(file, true);
        writer.append('\n');
        writer.write(id);
        writer.flush();
        writer.close();
    }

    public void readFromFile() throws IOException {
        File file = new File("NewsApiResponse.txt");

        FileReader fr = new FileReader(file);
        char [] a = new char[50];
        fr.read(a);

        for(char c : a)
            System.out.print(c);
        fr.close();
    }

}
