package com.github.frostedglass.mybatis.security.strategy;

public interface ISecurityStrategy extends IStrategy{
    /**
     * 加密数据（写入数据库前）
     *
     * @param plainText 明文
     * @return 密文
     * @throws Exception 加密异常
     */
    byte[] encrypt(byte[] plainText) throws Exception;

    /**
     * 解密数据（从数据库读取后）
     *
     * @param cipherText 密文
     * @return 明文
     * @throws Exception 解密异常
     */
    byte[] decrypt(byte[] cipherText) throws Exception;

}
