package com.labus;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

public class ScannerFile {
    File file;
    private static ScannerFile instance;

    public ScannerFile(File file) {
        this.file = file;
    }

    public ScannerFile() {
    }

    public static ScannerFile getInstance() {
        if (instance == null) {
            instance = new ScannerFile();
        }
        return instance;
    }

    public String scanSignature(Signature... searchingSign) {
        byte[] byteFiles = new byte[1024], oldBytes = new byte[1024];

        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            BufferedInputStream buffFileIn = new BufferedInputStream(fileInputStream, 1024);
            String hex = new String();
            for (Signature signature : searchingSign)
                while (buffFileIn.read(byteFiles) != -1) {
                    String curHex = DatatypeConverter.printHexBinary(byteFiles);

                    String doubleHex = hex + curHex;
                    hex = curHex;
                    if (doubleHex.contains(signature.getHex()))
                        return signature.getName();
                }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
