package com.proline;

import java.net.URISyntaxException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        for (String arg : args) {
            SIEFile file = new Parse().getSIEFile(arg);
            List<String> fnamn = file.search("#FNAMN");
            System.out.println(fnamn);
            boolean valid = file.validate();
            System.out.println("Validated " + valid);
            Graphics.main(file);
        }
    }
}
