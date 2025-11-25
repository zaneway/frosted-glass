package com.github.frostedglass.mybatis.security.strategy;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES 加密策略实现
 * 
 * @author frostedglass
 */
public class AesEncryptStrategy extends AbstractEncryptStrategy {
    
    private static final String ALGORITHM = "AES";
    
    /**
     * 默认构造函数，使用默认密钥
     */
    public AesEncryptStrategy() {
        super("FrostedGlass1234"); // 16字节密钥
    }
    
    /**
     * 带自定义默认密钥的构造函数
     * 
     * @param defaultKey 默认密钥（必须是16字节）
     */
    public AesEncryptStrategy(String defaultKey) {
        super(defaultKey);
        validateKey(defaultKey);
    }
    
    @Override
    protected String doEncrypt(String plainText, String key) throws Exception {
        validateKey(key);
        
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }
    
    @Override
    protected String doDecrypt(String cipherText, String key) throws Exception {
        validateKey(key);
        
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        
        byte[] decoded = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
    
    /**
     * 验证密钥长度（AES 要求 16、24 或 32 字节）
     */
    private void validateKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("AES key cannot be null");
        }
        int length = key.getBytes(StandardCharsets.UTF_8).length;
        if (length != 16 && length != 24 && length != 32) {
            throw new IllegalArgumentException(
                String.format("AES key length must be 16, 24 or 32 bytes, but got %d bytes", length));
        }
    }
}

