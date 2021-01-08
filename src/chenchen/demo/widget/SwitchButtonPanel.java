package chenchen.demo.widget;

import chenchen.demo.utils.GlobalDef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchButtonPanel extends Panel {

    /**
     * 切换按钮点击事件
     */
    public interface SwitchButtonListener {

        void onChange(String action);

    }

    private SwitchButtonListener switchButtonListener;
    private FlowLayout flowLayout;
    private JRadioButton encryptButton;
    private JRadioButton decryptButton;

    public SwitchButtonPanel() {
        init();
    }

    private void init() {
        flowLayout = new FlowLayout(FlowLayout.CENTER, 50, 0);
        this.setLayout(flowLayout);

        Font font = new Font("黑体", Font.PLAIN, 18);
        encryptButton = new JRadioButton(GlobalDef.ENCRYPT);
        encryptButton.setFont(font);
        encryptButton.setActionCommand(GlobalDef.ENCRYPT);
        encryptButton.addActionListener(actionListener);

        decryptButton = new JRadioButton(GlobalDef.DECRYPT);
        decryptButton.setFont(font);
        decryptButton.setActionCommand(GlobalDef.DECRYPT);
        decryptButton.addActionListener(actionListener);

        // 创建按钮组，把两个单选按钮添加到该组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(encryptButton);
        btnGroup.add(decryptButton);
        encryptButton.setSelected(true);

        this.add(encryptButton);
        this.add(decryptButton);
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (switchButtonListener == null) {
                return;
            }

            String actionCommand = e.getActionCommand();
            switchButtonListener.onChange(actionCommand);
        }
    };

    public void setSwitchButtonListener(SwitchButtonListener listener) {
        this.switchButtonListener = listener;
    }
}
