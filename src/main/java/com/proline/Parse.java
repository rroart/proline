package com.proline;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    private static final String BEGINNING_OF_ARRAY = "{";
    private static final String END_OF_ARRAY       = "}";

    List<String> lexAnalyze(String str) {
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(str);
        while (m.find()) {
            list.add(m.group(1)); 
            // Add .replace("\"", "") to remove surrounding quotes.
        }
        return list;
    }

    public SIEFile parse(List<String> lines) {
        SIEFile file = new SIEFile();
        Stack<List> stack = new Stack<>();
        List curPos = file.getEntries();
        for (String line : lines) {
             switch (line) {
            case BEGINNING_OF_ARRAY:
                stack.push(curPos);
                List newList = new ArrayList<>();
                curPos.add(newList);
                curPos = newList;
                break;
            case END_OF_ARRAY:
                curPos = stack.pop();
                break;
            default:
                List lexLines = lexAnalyze(line);
                if (line.length() > 1 && !lexLines.isEmpty()) {
                    curPos.add(lexLines);
               }
            }
        }

        return file;
    }

    public SIEFile getSIEFile(String file) {
        String charsetStr = "ISO-8859-1";
        Charset charset = Charset.forName(charsetStr);
        List<String> lines = new ArrayList<>();
        try {
            Path path = Paths.get(file);
            lines = Files.readAllLines(path, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Parse().parse(lines);
    }

}
