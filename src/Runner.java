import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
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
                fileWalker(path);
            }
        }
    }

    private void fileWalker(final String path) {
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
        List<byte[]> bytes = new ArrayList<>();
        List<String> hexs = new ArrayList<>();
        Map<String, FileStorage> fileStorageMap = new HashMap<>();
        StringBuffer stringBuffer = new StringBuffer();

        path.forEach(p -> {
            try {
                bytes.add(Files.readAllBytes(Paths.get(p)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bytes.forEach(b -> {
            for (int i = 0; i < b.length; i++) {
                stringBuffer.append(Integer.toHexString(0xff & b[i]));
            }
            hexs.add(stringBuffer.toString());
            stringBuffer.delete(0, stringBuffer.length());
        });

        for (int i = 0; i < hexs.size(); i++) {
            String name = new File(path.get(i)).getName();
            fileStorageMap.put(name, new FileStorage(name, path.get(i), hexs.get(i)));
        }

        if (fileStorageMap.size() > 0) {
            Heming.getInstance().run(fileStorageMap);
        }
    }

}
