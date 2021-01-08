package chenchen.demo.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZWls1vhVCRqI8JcTdclOES4GQaHFnq51bQqqqnZrp3kXS8cmMOtCNkL9zMiM/0nTZcD7NWyqiuVZWG4SEiwyy0x1ywVWh0QSGsmAEknThxYx19TWeGks5s/BIoApnf2nNq7NgZIlLFRSAia/UtOUvoPsu5otBeRTu+1KC5Y80lwIDAQAB";

	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJlaWzW"
			+ "+FUJGojwlxN1yU4RLgZBocWernVtCqqqdmuneRdLxyYw60I2Qv3MyIz"
			+ "/SdNlwPs1bKqK5VlYbhISLDLLTHXLBVaHRBIayYASSdOHFjHX1NZ4aSzmz8EigCmd" + "/ac2rs2BkiUsVFICJr9S05S+g"
			+ "+y7mi0F5FO77UoLljzSXAgMBAAECgYB5WOGB5lcGKcRo2e4s2TGzJthwf" + "+T1BvyuYVORTMNvuj4ATOmSeFvdGaQVMKBp"
			+ "+UwTX1miwDg9qXDjFEVJaIpzoSu94j7jdDgejvnrh6yx+Z+p+u7QKzI1JKGW/FMl6"
			+ "+tyEN7SmcCncclrHeMd1GJAd/FctsnJ/ktqtzSbN6O6cQJBAMpkAPoFt1o"
			+ "/E9eFkCsLlvAtKJvbQK1cN5rJy9tnhDpF0WeHcO+dmx+FF0c64R" + "/jtJwPdlOcSO2iHo8wCyFiOG8CQQDB"
			+ "+SJpAdKQ5auHqqmNSaQ2JL4rP1jhBcYDLfF9vjrIiZpBlySk7nIjQfkuPu2hkthkNcya"
			+ "+jjfELEpYdhRPspZAkAg1jLWXeEOslc8y33Fjh7SHvGMv/jxZ5nZ8k2x7kyky7ka6"
			+ "/2l0tNdPNG3C9WEwAQIofWXwS6P55K/tpLHutDfAkBcVFUPvKFY"
			+ "/SmPsylvqjzy1Lx8Y3PP8GqXetf3LYRXjkARtnrn0uQbajzMyYFdJkPAN2eD4VUOwKhDdWJ9EbFJAkBZ2tcsW21N6bKAIM1exgO0+anrZUeY1BkBT9yerZ+vpISgqsuj29fTQQ/3+J6zFTBETAJhowQmj84rFQ1jky1j";

	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 加密算法RSA
	 */
	public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	public static String decryptByPublicKey(String data) throws Exception {
		byte[] encryptedData = new BASE64Decoder().decodeBuffer(data);
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(PUBLIC_KEY);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData);
	}

	public static String decryptByPrivateKey(String data, String privateKey) throws Exception {
		privateKey = PRIVATE_KEY;
		byte[] encryptedData = new BASE64Decoder().decodeBuffer(data);
		byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return new String(decryptedData, "UTF-8");
	}

	/**
	 * RSA公钥加密
	 *
	 * @param str 加密字符串
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
    public static String encrypt(String str) throws Exception {
		//base64编码的公钥
        byte[] decoded = new BASE64Decoder().decodeBuffer(PUBLIC_KEY);
        RSAPublicKey pubKey =
                (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = new BASE64Encoder().encode(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }
}
