package com.shiyan2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestFrame extends JFrame
{
    private JLabel lblText = new JLabel("Text");
    private JButton btnChangeText = new JButton("Change Text");
    private JButton btnAdd = new JButton("Add Button");
    private int i = 0;

    public TestFrame()
    {
        this.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        panel1.add(lblText);

        final JPanel panel2 = new JPanel(new GridLayout(0, 5));

        JPanel panel3 = new JPanel();
        panel3.add(btnChangeText);
        panel3.add(btnAdd);

        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH);

        btnChangeText.addActionListener(new ActionListener()    //改变标签的时候，只要改了标签的text值，界面就会相应变化
        {
            public void actionPerformed(ActionEvent event)
            {
                if (lblText.getText().equals("Text"))
                {
                    lblText.setText("12345");
                }
                else
                {
                    lblText.setText("Text");
                }
            }
        });

        btnAdd.addActionListener(new ActionListener()    //添加组件的时候，add完需要validate才能生效
        {
            public void actionPerformed(ActionEvent event)
            {
                final JButton button = new JButton("Delete " + i);
                i++;
                panel2.add(button);
                button.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)    //删除组件的时候，先把组件设为不可见，然后删去，最后validate
                    {
                        button.setVisible(false);
                        panel2.remove(button);
                        panel2.validate();
                        TestFrame.this.pack();    //这句话主要是用来调节界面大小，也可以不要这句，自己手动调节界面大小
                    }
                });
                panel2.validate();
                TestFrame.this.pack();
            }
        });
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new TestFrame();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
