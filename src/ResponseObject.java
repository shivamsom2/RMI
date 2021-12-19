
import java.io.File;
import java.io.Serializable;
import java.rmi.Remote;

public class ResponseObject implements Remote,Serializable{
    private static final long serialVersionUID = 1L;
    private File file=new File("C:\\Users\\DELL\\Documents\\RMI\\src\\resources\\Dummyfile.txt");

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    
    
}
