package com.labus;

import javax.swing.*;
import java.io.File;

public class Main extends JFrame {
    private Signature signature = new Signature("Virus labus", "4F70656E506F72742E636C617373");

    public static void main(String[] args) {
        //   new Main();
    }

    public Main() {
        System.out.println("123");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выбор директории");
        // Определение режима - только каталог
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        // Если директория выбрана, покажем ее в сообщении
        if (result == JFileChooser.APPROVE_OPTION)
            JOptionPane.showMessageDialog(Main.this,
                    fileChooser.getSelectedFile());
        findAllFiles(fileChooser.getSelectedFile());
    }

    public void findAllFiles(File root) {
        for (File file : root.listFiles()) {
            if (file.isDirectory())
                findAllFiles(file);
            else {
                System.out.println(file.getName() + " " + new ScannerFile(file).scanSignature(signature));
            }

        }
    }


}
