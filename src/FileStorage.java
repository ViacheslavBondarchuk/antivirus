/**
 * author: Viacheslav
 * date: 10.12.2019
 * time: 22:16
 **/
public class FileStorage {
    private String name;
    private String path;
    private String hex;

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

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public FileStorage(String name, String path, String hex) {
        this.name = name;
        this.path = path;
        this.hex = hex;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", hex='" + hex + '\'';
    }
}
