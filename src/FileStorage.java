/**
 * author: Viacheslav
 * date: 10.12.2019
 * time: 22:16
 **/
public class FileStorage {
    private String name;
    private String path;
    private int[] binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int[] getBinary() {
        return binary;
    }

    public void setBinary(int[] binary) {
        this.binary = binary;
    }

    public FileStorage(String name, String path, int[] binary) {
        this.name = name;
        this.path = path;
        this.binary = binary;
    }
}
