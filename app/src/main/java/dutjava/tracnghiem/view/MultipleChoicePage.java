package dutjava.tracnghiem.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MultipleChoicePage extends JFrame {
    
    JRadioButton radioButtonA;
    JRadioButton radioButtonB;
    JRadioButton radioButtonC;
    JRadioButton radioButtonD;


    JLabel questionLabel, titleLabel;
    public MultipleChoicePage(){


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Multiple-choice test");
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        titleLabel = new JLabel("Multiple-choice test");
        titleLabel.setBounds(screenWidth / 4, 50, 360, 50);
        titleLabel.setFont(new Font("Ariel", Font.BOLD, 28));
        titleLabel.setBackground(Color.CYAN);
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(0, 35, 0, 0));

        questionLabel = new JLabel("Question: Why didn't you invest in SUI?");
        questionLabel.setBounds(screenWidth/ 4, 120, 500, 50);
        questionLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        questionLabel.setForeground(Color.BLACK);




        radioButtonA = new JRadioButton("A");
        radioButtonA.setBounds((screenWidth / 4) + 30, 200, 300, 50);


        radioButtonB = new JRadioButton("B");
        radioButtonB.setBounds((screenWidth / 4) + 30, 250, 300, 50);

        radioButtonC = new JRadioButton("C");
        radioButtonC.setBounds((screenWidth / 4) + 30, 300, 300, 50);

        radioButtonD = new JRadioButton("D");
        radioButtonD.setBounds((screenWidth / 4) + 30, 350, 300, 50);


        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButtonA);
        bg.add(radioButtonB);
        bg.add(radioButtonC);
        bg.add(radioButtonD);

        this.add(titleLabel);
        this.add(questionLabel);
        this.add(radioButtonA);
        this.add(radioButtonB);
        this.add(radioButtonC);
        this.add(radioButtonD);


        this.setVisible(true);
    }




}
