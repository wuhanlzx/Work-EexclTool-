package com.tansun.casetransfertool.test;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * 2015-06-10
 */

public class GridLayoutDemo extends JFrame{

    JButton resultButton = new JButton("0");
    JPanel panel = new JPanel();
    
    public GridLayoutDemo()
    {
        //set to BorderLayout
        setLayout(new BorderLayout());
        
        add(resultButton, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        
        //gridLayout
        GridLayout gridLayout = new GridLayout(4,4,3,3);  //四行四列垂直水平的都为3
        panel.setLayout(gridLayout);
        
        //add buttons
        String [] buttonNames = new String []{"7", "8","9","/","4","5","6","*","1","2","3","-","0",".","=","+"};
        for (String string : buttonNames) {
            panel.add(new JButton(string));
        }
        //config resultButton
        resultButton.setSize(200, 50);
        resultButton.setHorizontalAlignment(SwingConstants.RIGHT);
        resultButton.setEnabled(false);        
        
        //setSize(250,200);
        pack();
        setTitle("GridLayoutDemo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        GridLayoutDemo demo = new GridLayoutDemo();
        demo.setVisible(true);
    }

}
