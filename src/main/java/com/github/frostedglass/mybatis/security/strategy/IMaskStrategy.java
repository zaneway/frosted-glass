package com.github.frostedglass.mybatis.security.strategy;

import com.github.frostedglass.mybatis.security.strategy.params.MaskSecurityParams;

public interface IMaskStrategy extends IStrategy<MaskSecurityParams>{
    /**
     * 脱敏数据（从数据库读取后，用于显示）
     *
     * @param plainText 明文
     * @return 脱敏后的数据
     */
    String mask(String plainText);
}
