package com.proline;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class TestMe {
    
    public SIEFile getSIEFile(String file) throws URISyntaxException {
        Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource(file).toURI());
        return new Parse().getSIEFile(path.toFile().getAbsolutePath());
    }
    
    @Test
    public void test() throws IOException, URISyntaxException {
        String file = "BL0001_typ4.SE";
        SIEFile result = getSIEFile(file);
        System.out.println(result);
        result.getData();
    }

    @Test
    public void test2() throws IOException, URISyntaxException {
        String file = "typ4.se";
        SIEFile result = getSIEFile(file);
        System.out.println(result);
        result.getData();
    }

    @Test
    public void test3() throws IOException, URISyntaxException {
        String file = "Test4.SE";
        SIEFile result = getSIEFile(file);
        System.out.println(result);
        result.getData();
    }

    @Test
    public void parseTest() {
        String str = "Location \"Welcome  to india\" Bangalore " +
                "Channai \"IT city\"  Mysore";

        List<String> list = new Parse().lexAnalyze(str);
        System.out.println(list);
    }
}
