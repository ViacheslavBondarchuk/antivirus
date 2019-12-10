import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

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

    public void run(final Map<String, FileStorage> fileStorageMap) {
        List<String> virusHex = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int retValue = fileChooser.showOpenDialog(null);
        File file = null;
        if (retValue == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        if (file != null) {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    virusHex.add(scanner.nextLine().trim());
                }
                if (virusHex.size() > 0) {
                    virusHex.forEach(vh -> {
                        fileStorageMap.entrySet().forEach(fsm -> {
                            if (compare(fsm.getValue().getHex(), vh)) {
                                int result = JOptionPane.showConfirmDialog(null
                                        , String.format("Virus detected at file %s. Delete?", fsm.getValue().getName())
                                        , "Information by compare virus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                                if (result == 0) {
                                    new File(fsm.getValue().getPath()).delete();
                                    JOptionPane.showConfirmDialog(null
                                            , String.format("File %s, was deleted", fsm.getValue().getName())
                                            , "Information by virus", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        });
                    });
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean compare(final String virusHex, final String fileHex) {
        BitSet virusBitSet = initBitSet(virusHex);
        BitSet fileBitSet = initBitSet(fileHex);
        virusBitSet.xor(fileBitSet);
        double deviation = virusBitSet.cardinality() / fileBitSet.length() * 100;
        System.out.println("Deviation => " + deviation + "%");
        if (Math.round(deviation) < 30 || Math.round(deviation) == 30) {
            return true;
        }
        return false;
    }

    private BitSet initBitSet(String hex) {
        String binaryHex = String.valueOf(new BigInteger(hex, 16));
        BitSet resultBitSet = new BitSet(binaryHex.length());
        char[] chars = binaryHex.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '1') {
                resultBitSet.set(i, true);
            } else {
                resultBitSet.set(i, false);
            }
        }
        return resultBitSet;
    }
}