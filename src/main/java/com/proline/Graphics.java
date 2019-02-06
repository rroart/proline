package com.proline;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Graphics {
    static JFrame frame; 

    public static void main(SIEFile file) {
        frame = new JFrame("frame"); 
        JPanel panel = new JPanel();
        Object[][] data = file.getData();
        int columns = data[0].length;
        String[] columnNames = new String[columns];
        for (int i = 0; i < columns; i++) {
            columnNames[i] = "Column " + i;
        }
        JTable table = new JTable(data, columnNames);
        table.getTableHeader().setResizingAllowed(true);
        panel.add(table);
        frame.add(panel); 
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 1024, 768);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(1280, 800));
        contentPane.add(scrollPane);
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
