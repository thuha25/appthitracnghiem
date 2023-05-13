package dutjava.tracnghiem.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dutjava.tracnghiem.model.model.TestModel;

public class TestSelectPanel extends JPanel {

    private TestModel test;

    private JLabel testNameL;
    private JButton takeTestButton;

    private void init() {
        testNameL = new JLabel(test.getName());
        testNameL.setPreferredSize( new Dimension(275, 50));
        takeTestButton = new JButton("Join");
        this.setBackground(Color.white);
        
        this.add(testNameL);
        
        this.add(takeTestButton);
    }

    public void addActionListener(ActionListener actionListener) {
        this.takeTestButton.addActionListener(actionListener);
    }

    public TestSelectPanel(TestModel test) {
        this.test = test;
        init();
    }
}
