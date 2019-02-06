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

    Object lexAnalyze(String str) {
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(str);
        while (m.find()) {
            list.add(m.group(1)); 
            // Add .replace("\"", "") to remove surrounding quotes.
        }
        if ("#TRANS".equals(list.get(0))) {
            Transaction transaction = new Transaction();
            transaction.accountno = list.get(1);
            transaction.objectlist = list.get(2);
            transaction.amount = list.get(3);
            if (list.size() > 4) {      
                transaction.transdate = list.get(4);
            }
            if (list.size() > 5) {
                transaction.transtext = list.get(5);
            }
            if (list.size() > 6) {
                transaction.quantity = list.get(6);
            }
            if (list.size() > 7) {
                transaction.sign = list.get(7);
            }
            transaction.width = list.size();
            return transaction;
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
                Object lexLines = lexAnalyze(line);
                if (line.length() > 1) {
                    boolean notEmpty = true;
                    if (lexLines instanceof java.util.List) {
                        notEmpty = !((List) lexLines).isEmpty();
                    }
                    if (notEmpty) {
                        curPos.add(lexLines);
                    }
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
