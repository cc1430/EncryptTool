package chenchen.demo.widget;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OutputDialog extends JDialog implements ClipboardOwner {

    private JTextArea jTextArea;
    private final Clipboard clipboard;

    public OutputDialog(JFrame jFrame, String title) {
        super(jFrame, title, true);

        // 设置对话框的宽高
        this.setSize(600, 350);
        // 设置对话框大小不可改变
        this.setResizable(false);
        // 设置对话框相对显示的位置
        this.setLocationRelativeTo(jFrame);

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        addContent();
    }

    private void addContent() {
        //盒式布局
        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);
        jPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        //文本框
        jTextArea = new JTextArea();
        Font font = new Font("", Font.PLAIN, 16);
        jTextArea.setFont(font);
        jTextArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jPanel.add(jScrollPane);

        //复制按钮
        JButton jButton = new JButton("复制文本");
        font = new Font(null, Font.BOLD, 16);
        jButton.setFont(font);
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton.addActionListener(actionListener);
        jPanel.add(Box.createVerticalStrut(20));
        jPanel.add(jButton);

        this.setContentPane(jPanel);
    }

    public void setResultText(String text) {
        if (jTextArea != null) {
            jTextArea.setText(text);
        }
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jTextArea == null) {
                return;
            }
            String text = jTextArea.getText();
            StringSelection stringSelection = new StringSelection(text);
            if (clipboard != null) {
                clipboard.setContents(stringSelection, OutputDialog.this);
            }
        }
    };

    public void showDialog() {
        this.setVisible(true);
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }
}
