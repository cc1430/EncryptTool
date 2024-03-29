package chenchen.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @date 2020/4/20 21:32
 * @create ltx
 * @description
 */
class MD5 {
    static String getEncode(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bs = md.digest(input.getBytes());
            return toMD5String(bs);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String toMD5String(byte[] bs) {
        char[] chars = new char[bs.length];
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            int val = ((int) bs[i]) & 0xff;
            if (val < 16)
                buffer.append("0");
            buffer.append(Integer.toHexString(val));
        }
        return buffer.toString();
    }
}
