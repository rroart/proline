package com.proline;

import java.util.ArrayList;
import java.util.List;

public class SIEFile {
    private List entries = new ArrayList<>();

    public List getEntries() {
        return entries;
    }

    public void setEntries(List entries) {
        this.entries = entries;
    }

    public boolean validate() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] mandatory = { "#FLAGGA", "#PROGRAM", "#FORMAT", "#GEN", "#SIETYP", "#FNAMN", "#RAR", "#KONTO", "#IB", "#UB", "#RES" };
        for (String str : mandatory) {
            boolean found = false;
            for (Object entry : entries) {
                List<Object> objectlist = (List<Object>) entry;
                if (objectlist.get(0) instanceof java.lang.String) {
                    List<String> list = (List<String>) entry;
                    if (str.equals(list.get(0))) {
                        found = true;
                    }
                }
            }
            if (!found) {
                stringBuilder.append(str).append(" er null\n");
            }
        }
        if (stringBuilder.length() > 0) {
            System.out.println(stringBuilder.toString());
        }
        return stringBuilder.length() == 0;

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Object entry : entries) {
            result.append(entry).append("\n");
        }
        return result.toString();
    }

    public Object[][] getData() {
        int size = 0;
        int width = 0;
        for (Object entry : entries) {
            List<Object> objectlist = (List<Object>) entry;
            if (objectlist.get(0) instanceof java.lang.String) {
                size++;
                width = Math.max(width, objectlist.size());
            } else {
                size += objectlist.size();                
                List<Object> list = (List<Object>) entry;
                for (Object object : list) {
                    if (object instanceof Transaction) {
                        width = Math.max(width, ((Transaction) object).width);
                    } else {
                        width = Math.max(width, ((List)object).size());
                    }
                }
            }
        }
        Object[][] data = new Object[size][width];
        int index = 0;
        for (Object entry : entries) {
            List<Object> objectlist = (List<Object>) entry;
            if (objectlist.get(0) instanceof java.lang.String) {
                data[index] = new Object[width];
                data[index] = objectlist.toArray(data[index]);
                index++;
            } else {
                List<Object> list = (List<Object>) entry; 
                for (Object object : list) {
                    data[index] = new Object[width];
                    if (object instanceof Transaction) {
                        data[index] = ((Transaction) object).toArray(width);
                    } else {
                        data[index] = ((List) object).toArray(data[index]);
                    }
                    index++;
                }
            }
        }        
        return data;
    }

    public List<String> search(String string) {
        if (string == null) {
            return new ArrayList<>();
        }
        for (Object entry : entries) {
            List<Object> objectlist = (List<Object>) entry;
            if (objectlist.get(0) instanceof java.lang.String) {
                List<String> list = (List<String>) entry;
                if (string.equals(list.get(0))) {
                    return list;
                }
            }
        }
        return new ArrayList<>();
    }
}
