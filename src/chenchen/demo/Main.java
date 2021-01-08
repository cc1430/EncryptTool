package chenchen.demo;

import chenchen.demo.utils.GlobalDef;
import chenchen.demo.utils.RSAUtil;

public class Main {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame("加解密工具v1.1 （author：cici）", GlobalDef.FRAME_DIMENSION);
        mainFrame.buildFrame();
        mainFrame.show();
        try {
            RSAUtil.encrypt("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
