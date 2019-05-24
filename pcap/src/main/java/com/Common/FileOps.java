package com.Common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOps {

    private String _filePath;
    private BufferedWriter _writer;

    public FileOps(String filePath) {
        this._filePath = filePath;

        File f = new File(_filePath);
        f.delete();

        FileWriter fw;
        try {

            fw = new FileWriter(_filePath, true);
            _writer = new BufferedWriter(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void writeLn(String toWrite){

        try {
            _writer.write(toWrite);
            _writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(String toWrite){

        try {
            _writer.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            _writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
