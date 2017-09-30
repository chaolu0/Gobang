package ui.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyDialog extends JDialog {

    public interface Listener {
        void click();
    }

    private Listener l;
    private JLabel label;
    private JButton button;
    private String msg;

    public MyDialog(String msg) {
        this(msg,false);
    }

    public MyDialog(String msg,boolean model){
        setModel(model);
        setTitle("提示");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        label = new JLabel();
        label.setBounds(50, 100, 300, 50);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText(msg);
        label.setVisible(true);
        button = new JButton();
        button.setText("ok");
        button.setBounds(150, 200, 100, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (l != null)
                    l.click();
                MyDialog.this.setVisible(false);
                MyDialog.this.dispose();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (l != null)
                    l.click();
            }
        });
        add(button);
        add(label);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setMessage(String message) {
        label.setText(message);
        label.setVisible(true);

    }
    public void setModel(boolean model){
        super.setModal(model);
    }

    /**
     * 点击回调  （确定按钮和关闭按钮）
     *
     * @param l
     */
    public void setOnClickListener(Listener l) {
        this.l = l;
    }


}
