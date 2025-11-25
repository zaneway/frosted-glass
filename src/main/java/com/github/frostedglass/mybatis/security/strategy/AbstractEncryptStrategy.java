package com.github.frostedglass.mybatis.security.strategy;

import com.github.frostedglass.mybatis.security.strategy.params.BaseSecurityParams;

/**
 * 抽象加密策略基类
 * 提供通用的加密/解密框架，子类只需实现具体的算法
 * 
 * @author frostedglass
 */
public abstract class AbstractEncryptStrategy implements ISecurityStrategy {
    
    /**
     * 默认密钥（子类可以覆盖）
     */
    protected String defaultKey = "DefaultKey123456";
    
    /**
     * 构造函数
     */
    public AbstractEncryptStrategy() {
    }
    
    /**
     * 带默认密钥的构造函数
     * 
     * @param defaultKey 默认密钥
     */
    public AbstractEncryptStrategy(String defaultKey) {
        this.defaultKey = defaultKey;
    }

    @Override
    public byte[] encrypt(byte[] plainText) throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] decrypt(byte[] cipherText) throws Exception {
        return new byte[0];
    }

    @Override
    public void init(BaseSecurityParams params) {

    }


    /**
     * 获取加密密钥
     *
     * @param params 参数（可能包含密钥）
     * @return 密钥
     */
    protected String getKey(String params) {
        return (params != null && !params.isEmpty()) ? params : defaultKey;
    }

    /**
     * 执行加密（子类实现具体算法）
     * 
     * @param plainText 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception 加密异常
     */
    protected abstract String doEncrypt(String plainText, String key) throws Exception;
    
    /**
     * 执行解密（子类实现具体算法）
     * 
     * @param cipherText 密文
     * @param key 密钥
     * @return 明文
     * @throws Exception 解密异常
     */
    protected abstract String doDecrypt(String cipherText, String key) throws Exception;
    
    /**
     * 设置默认密钥
     * 
     * @param defaultKey 默认密钥
     */
    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }
    
    /**
     * 获取默认密钥
     * 
     * @return 默认密钥
     */
    public String getDefaultKey() {
        return defaultKey;
    }
}

