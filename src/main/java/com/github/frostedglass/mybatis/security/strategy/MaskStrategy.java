package com.github.frostedglass.mybatis.security.strategy;

import com.github.frostedglass.mybatis.security.strategy.params.MaskSecurityParams;

/**
 * 脱敏策略实现
 * 支持多种脱敏规则：手机号、身份证、邮箱、银行卡等
 * 
 * @author frostedglass
 */
public class MaskStrategy implements IMaskStrategy {


    @Override
    public void init(MaskSecurityParams params) {

    }


    /**
     * 脱敏类型
     */
    public enum MaskType {
        /** 手机号：保留前3后4 */
        MOBILE,
        /** 身份证：保留前6后4 */
        ID_CARD,
        /** 邮箱：保留@前1位和@后全部 */
        EMAIL,
        /** 银行卡：保留后4位 */
        BANK_CARD,
        /** 姓名：保留姓，名用*代替 */
        NAME,
        /** 默认：中间部分用*代替 */
        DEFAULT
    }
    @Override
    public String mask(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            return plainText;
        }

        MaskType maskType = MaskType.DEFAULT;

        return applyMask(plainText, maskType);
    }

    /**
     * 应用脱敏规则
     */
    private String applyMask(String text, MaskType type) {
        switch (type) {
            case MOBILE:
                return maskMobile(text);
            case ID_CARD:
                return maskIdCard(text);
            case EMAIL:
                return maskEmail(text);
            case BANK_CARD:
                return maskBankCard(text);
            case NAME:
                return maskName(text);
            case DEFAULT:
            default:
                return maskDefault(text);
        }
    }
    
    /**
     * 手机号脱敏：138****5678
     */
    private String maskMobile(String mobile) {
        if (mobile.length() < 11) {
            return maskDefault(mobile);
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(7);
    }
    
    /**
     * 身份证脱敏：110101******1234
     */
    private String maskIdCard(String idCard) {
        if (idCard.length() < 10) {
            return maskDefault(idCard);
        }
        return idCard.substring(0, 6) + "******" + idCard.substring(idCard.length() - 4);
    }
    
    /**
     * 邮箱脱敏：z****@example.com
     */
    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return maskDefault(email);
        }
        return email.charAt(0) + "****" + email.substring(atIndex);
    }
    
    /**
     * 银行卡脱敏：**** **** **** 1234
     */
    private String maskBankCard(String bankCard) {
        if (bankCard.length() < 8) {
            return "****";
        }
        return "**** **** **** " + bankCard.substring(bankCard.length() - 4);
    }
    
    /**
     * 姓名脱敏：张**
     */
    private String maskName(String name) {
        if (name.length() <= 1) {
            return "*";
        }
        StringBuilder masked = new StringBuilder();
        masked.append(name.charAt(0));
        for (int i = 1; i < name.length(); i++) {
            masked.append("*");
        }
        return masked.toString();
    }
    
    /**
     * 默认脱敏：保留前后各1/4，中间用*代替
     */
    private String maskDefault(String text) {
        int length = text.length();
        if (length <= 2) {
            return "*";
        }
        
        int visibleLength = length / 4;
        if (visibleLength == 0) {
            visibleLength = 1;
        }
        
        String prefix = text.substring(0, visibleLength);
        String suffix = text.substring(length - visibleLength);
        int maskLength = length - 2 * visibleLength;
        
        StringBuilder masked = new StringBuilder(prefix);
        for (int i = 0; i < maskLength; i++) {
            masked.append("*");
        }
        masked.append(suffix);
        
        return masked.toString();
    }
}

