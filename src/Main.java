import java.io.File;
import java.io.FileWriter;

/**
 * The main method creates a File object and a FileController object and calls the readInputFile method to read the input file.
 * @throws Exception if there is an error reading the input file
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String path= args[0];
        File file = new File(path);
        FileController fileController = new FileController(file);
        String outputName=args[1];
        FileWriter fileWriter = new FileWriter(outputName);
        fileController.readInputFile(fileWriter);
        fileWriter.close();

    }
}