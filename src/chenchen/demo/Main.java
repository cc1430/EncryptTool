package chenchen.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static int width = 600;
    private static int height = 300;

    private static boolean encrypt = true;
    private static JTextArea textArea;
    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("加解密工具v1.0 （作者：cici）");
        // Setting the width and height of frame
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = frame.getSize();
        System.out.println("width = " + dimension.width + ", height = " + dimension.height);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        panel.setLayout(null);
        // 添加面板
        frame.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);
        addComponent(panel);
        addButton(panel);

        // 设置界面可见
        frame.setVisible(true);

        try {
            RSAUtil.encrypt("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void placeComponents(JPanel panel) {
        JPanel jPanel = new JPanel();
        jPanel.setSize(width, 30);

        // 创建两个单选按钮
        JRadioButton radioBtn01 = new JRadioButton("加密");
        Font font = new Font(null, Font.BOLD, 16);
        radioBtn01.setFont(font);
        JRadioButton radioBtn02 = new JRadioButton("解密");
        font = new Font(null, Font.BOLD, 16);
        radioBtn02.setFont(font);

        // 创建按钮组，把两个单选按钮添加到该组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);

        // 设置第一个单选按钮选中
        radioBtn01.setSelected(true);
        radioBtn01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encrypt = true;
            }
        });

        radioBtn02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encrypt = false;
            }
        });


        jPanel.add(radioBtn01);
        jPanel.add(radioBtn02);
        panel.add(jPanel);
    }

    private static void addComponent(JPanel panel) {
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.setBounds(0, 40, width, 120);

        JLabel jLabel = new JLabel("输入: ");
        Font font = new Font(null, Font.BOLD, 16);
        jLabel.setFont(font);
        jPanel.add(jLabel);

        textArea = new JTextArea(6, 30);//构造一个文本域
        textArea.setBounds(0,0,300, 120);
        textArea.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0,0,textArea.getWidth(),textArea.getHeight());
        jPanel.add(scrollPane);

        panel.add(jPanel);
    }

    private static void addButton(JPanel panel) {
        JButton jButton = new JButton("Go");
        Font font = new Font(null, Font.BOLD, 16);
        jButton.setFont(font);
        jButton.setBounds(width / 2 - 40, 160, 80, 30);
        panel.add(jButton);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                String result;
                try {
                    if (encrypt) {
                        result = RSAUtil.encrypt(text);
                    } else {
                        result = RSAUtil.decryptByPrivateKey(text, null);
                    }
                } catch(Exception ex){
                    ex.printStackTrace();
                    result = "出错！";
                }
                showCustomDialog(frame, frame, result);
            }
        });
    }

    /**
     * 显示一个自定义的对话框
     *
     * @param owner 对话框的拥有者
     * @param parentComponent 对话框的父级组件
     */
    private static void showCustomDialog(Frame owner, Component parentComponent, String text) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "输出结果", true);
        // 设置对话框的宽高
        dialog.setSize(400, 180);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);

        // 创建一个标签显示消息内容
        JTextArea jTextArea = new JTextArea(text);
        jTextArea.setBounds(20, 10, 350, 150);
        jTextArea.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
        Font font = new Font(null, Font.PLAIN, 14);
        jTextArea.setFont(font);

        // 创建一个按钮用于关闭对话框
        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭对话框
                dialog.dispose();
            }
        });

        // 创建对话框的内容面板, 在面板内可以根据自己的需要添加任何组件并做任意是布局
        JPanel panel = new JPanel();

        // 添加组件到面板
        panel.add(jTextArea);
        panel.add(okBtn);

        // 设置对话框的内容面板
        dialog.setContentPane(panel);
        // 显示对话框
        dialog.setVisible(true);
    }
}
