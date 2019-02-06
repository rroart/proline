package com.proline;

public class Transaction {
    public int width;
    
    public String accountno;
    
    public String objectlist;
    
    public String amount;
    
    public String transdate;
    
    public String transtext;
    
    public String quantity;
    
    public String sign;

    public Object[] toArray(int width) {
        Object[] array = new Object[width];
        array[0] = "#TRANS";
        array[1] = accountno;
        array[2] = objectlist;
        array[3] = amount;
        if (width > 4) {
            array[4] = transdate;
        }
        if (width > 5) {
            array[5] = transtext;
        }
        if (width > 6) {
            array[6] = quantity;
        }
        if (width > 7) {
            array[7] = sign;
        }
        return array;
    }
}
