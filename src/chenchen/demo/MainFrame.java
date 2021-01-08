package chenchen.demo;


import chenchen.demo.utils.GlobalDef;
import chenchen.demo.utils.RSAUtil;
import chenchen.demo.widget.OutputDialog;
import chenchen.demo.widget.SwitchButtonPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {

    private final JFrame jFrame;
    private OutputDialog outputDialog;
    private JTextArea jTextArea;
    private boolean isEncrypt = true;

    public MainFrame(String title, Dimension dimension) {
        jFrame = new JFrame(title);
        jFrame.setSize(dimension);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 显示
     */
    public void show() {
        if (jFrame != null) {
            jFrame.setVisible(true);
        }
    }

    private final SwitchButtonPanel.SwitchButtonListener switchButtonListener = new SwitchButtonPanel.SwitchButtonListener() {
        @Override
        public void onChange(String action) {
            if (GlobalDef.ENCRYPT.equals(action)) {
                if (!isEncrypt) {
                    jTextArea.setText("");
                    isEncrypt = !isEncrypt;
                }
                System.out.println("SwitchButtonListener action = ENCRYPT");
            } else if (GlobalDef.DECRYPT.equals(action)) {
                if (isEncrypt) {
                    jTextArea.setText("");
                    isEncrypt = !isEncrypt;
                }
                System.out.println("SwitchButtonListener action = DECRYPT");
            } else {
                System.out.println("SwitchButtonListener action unknown!");
            }
        }
    };

    /**
     * 搭建Frame内部结构
     */
    public void buildFrame() {
        if (jFrame == null) {
            return;
        }

        //整体边框布局
        JPanel jPanel = new JPanel();
        BorderLayout borderLayout = new BorderLayout(10, 10);
        jPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
        jPanel.setLayout(borderLayout);

        buildNorth(jPanel);
        buildWest(jPanel);
        buildCenter(jPanel);
        buildSouth(jPanel);

        jFrame.add(jPanel);
    }

    private void buildNorth(JPanel jPanel) {
        //North
        SwitchButtonPanel switchButtonPanel = new SwitchButtonPanel();
        switchButtonPanel.setSwitchButtonListener(switchButtonListener);
        jPanel.add(BorderLayout.NORTH, switchButtonPanel);
    }

    private void buildWest(JPanel jPanel) {
        //West
        JLabel jLabel = new JLabel("文本输入：");
        Font font = new Font("黑体", Font.PLAIN, 18);
        jLabel.setFont(font);
        jPanel.add(BorderLayout.WEST, jLabel);
    }

    private void buildCenter(JPanel jPanel) {
        jTextArea = new JTextArea();
        Font font = new Font(null, Font.PLAIN, 16);
        jTextArea.setFont(font);
        jTextArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(jTextArea);
        scrollPane.setBounds(0, 0, jTextArea.getWidth(), jTextArea.getHeight());

        jPanel.add(BorderLayout.CENTER, scrollPane);
    }

    private void buildSouth(JPanel jPanel) {
        JPanel southPanel = new JPanel();

        JButton jButton = new JButton("开始");
        Font font = new Font(null, Font.BOLD, 16);
        jButton.setFont(font);
        jButton.addActionListener(actionListener);

        southPanel.add(jButton);
        jPanel.add(BorderLayout.SOUTH, southPanel);
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (jTextArea == null) {
                return;
            }
            String res;
            if (isEncrypt) {
                res = doEncrypt(jTextArea.getText());
            } else {
                res = doDecrypt(jTextArea.getText());
            }

            if (res == null) {
                JOptionPane.showMessageDialog(jFrame, isEncrypt ? "加密出错！" : "解密出错！", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (outputDialog == null) {
                    outputDialog = new OutputDialog(jFrame, "输出结果");
                }
                outputDialog.setResultText(res);
                outputDialog.showDialog();
            }
        }
    };

    private String doEncrypt(String text) {
        String res = null;
        try {
            res = RSAUtil.encrypt(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private String doDecrypt(String text) {
        String res = null;
        try {
            res = RSAUtil.decryptByPrivateKey(text, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
