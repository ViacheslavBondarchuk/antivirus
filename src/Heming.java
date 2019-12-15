import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * author: Viacheslav
 * date: 10.12.2019
 * time: 22:38
 **/
public class Heming {
    private static Heming instance;

    public static Heming getInstance() {
        if (instance == null) {
            instance = new Heming();
        }
        return instance;
    }

    public void run(Map<String, FileStorage> fileStorageMap) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int retValue = fileChooser.showOpenDialog(null);
        int[] virusBinary = null;
        if (retValue == JFileChooser.APPROVE_OPTION) {
            virusBinary = getBinaryOfVirus(fileChooser.getSelectedFile().getPath());
        }
        if (virusBinary.length > 0) {
            int[] finalVirusBinary = virusBinary;
            fileStorageMap.entrySet().forEach(fsm -> {
                if (compare(finalVirusBinary, fsm.getValue().getBinary())) {
                    int result = JOptionPane.showConfirmDialog(null
                            , String.format("Virus detected at file %s. Delete?", fsm.getValue().getName())
                            , "Information by compare virus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (result == 0) {
                        new File(fsm.getValue().getPath()).delete();
                        JOptionPane.showConfirmDialog(null
                                , String.format("File %s, was deleted", fsm.getValue().getName())
                                , "Information by virus", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null
                            , "No one virus has been detected", "Information by compare virus", JOptionPane.DEFAULT_OPTION);
                }
            });
        }
    }

    private boolean simpleCompare(int[] binaryFile, int[] binaryVirus) {
        int[] binaryResult = and(binaryFile, binaryVirus);
        float matchPercentage = matchPercentage(binaryFile, binaryResult);
        System.out.println("Match percentage => " + matchPercentage);
        if (matchPercentage >= 90) {
            return true;
        }
        return false;
    }

    private boolean compareByPart(int[] binaryFile, int[] binaryVirus) {
        List<Float> matchPercentages = new ArrayList<>();

        for (int i = 0; i < binaryVirus.length; i++) {
            if ((binaryVirus.length + i) <= binaryFile.length) {
                int[] partOfFile = Arrays.copyOfRange(binaryFile, i, (binaryVirus.length + i));
                int[] binaryResult = and(partOfFile, binaryVirus);
                matchPercentages.add(matchPercentage(partOfFile, binaryResult));
            }
        }
        for (int i = 0; i < matchPercentages.size(); i++) {
            System.out.println("Matching percentage of part binary file to a virus  => " + matchPercentages.get(i) + "%");
            if (matchPercentages.get(i) > 90) {
                return true;
            }
        }
        return false;
    }

    private boolean compare(int[] virusBinary, int[] fileBinary) {
        if (fileBinary.length > virusBinary.length) return compareByPart(fileBinary, virusBinary);
        if (fileBinary.length == virusBinary.length) return simpleCompare(fileBinary, virusBinary);
        return false;
    }

    private int[] and(final int[] mass1, final int[] mass2) {
        if (mass1.length == mass2.length) {
            int[] result = new int[mass1.length];
            for (int i = 0; i < mass1.length; i++) {
                result[i] = mass1[i] * mass2[i];
            }
            return result;
        }
        return null;
    }

    private int[] getBinaryOfVirus(String path) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            char[] chars = new BigInteger(bytes).toString(2).toCharArray();
            int[] binary = new int[chars.length];
            for (int i = 0; i < chars.length; i++) {
                binary[i] = Integer.valueOf(String.valueOf(chars[i]), 2);
            }
            return binary;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private float matchPercentage(int[] virusBinaries, int[] virusInstanceBinaries) {
        int count = 0;
        for (int i = 0; i < virusBinaries.length; i++) {
            if (virusBinaries[i] == virusInstanceBinaries[i]) {
                count++;
            }
        }
        return (((float) count / virusBinaries.length) * 100);
    }
}