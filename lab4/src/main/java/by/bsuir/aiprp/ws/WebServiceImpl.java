package by.bsuir.aiprp.ws;

import javax.jws.WebService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@WebService(endpointInterface = "by.bsuir.aiprp.ws.AwesomeWebService")
public class WebServiceImpl implements AwesomeWebService {

    private static final String RESOURCES_PATH = "C:\\Users\\annab\\Downloads\\lab4\\lab4\\src\\main\\resources\\";

    @Override
    public Object[] getFiles() {
        File[] files = new File(RESOURCES_PATH).listFiles();

        return Arrays.stream(Optional.ofNullable(files).orElseThrow(() -> new NullPointerException("Empty directory")))
                .filter(File::isFile)
                .map(File::getName)
                .toArray();
    }

    @Override
    public String getFile(String fileName) {
        File inputFile = new File(RESOURCES_PATH + fileName);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            return bufferedReader.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: file (" + fileName + ") cannot be read";
        }
    }
}
