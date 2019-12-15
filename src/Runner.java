import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * author: Viacheslav
 * date: 10.12.2019
 * time: 20:53
 **/
public class Runner {
    private static Runner innstance;

    public static Runner getInstance() {
        if (innstance == null) {
            innstance = new Runner();
        }
        return innstance;
    }

    public void run() {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retValue = jFileChooser.showOpenDialog(null);
        String path = "";
        if (retValue == JFileChooser.APPROVE_OPTION) {
            path = jFileChooser.getSelectedFile().getPath();
            if (!path.equals("")) {
                folderWalker(path);
            }
        }
    }

    private void folderWalker(final String path) {
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {
            List<String> paths = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            if (path.length() > 0) {
                reader(paths);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reader(List<String> path) throws IOException {
        List<byte[]> byteList = new ArrayList<>();
        List<int[]> binaryList = new ArrayList<>();
        Map<String, FileStorage> fileStorageMap = new HashMap<>();

        path.forEach(p -> {
            try {
                byteList.add(Files.readAllBytes(Paths.get(p)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        byteList.forEach(bytes -> {
            char[] chars = new BigInteger(bytes).toString(2).toCharArray();
            int[] binary = new int[chars.length];
            for (int i = 0; i < chars.length; i++) {
                binary[i] = Integer.valueOf(String.valueOf(chars[i]),2);
            }
            binaryList.add(binary);
        });

        for (int i = 0; i < binaryList.size(); i++) {
            String name = new File(path.get(i)).getName();
            fileStorageMap.put(name, new FileStorage(name, path.get(i), binaryList.get(i)));
        }

        if (fileStorageMap.size() > 0) {
            Heming.getInstance().run(fileStorageMap);
        }
    }
}
