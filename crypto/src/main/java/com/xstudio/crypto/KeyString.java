package com.xstudio.crypto;

/**
 * 公私钥
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/11
 */
public class KeyString {

    /**
     * 系数、模数
     */
    private String modules;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;

    public KeyString(String publicKey, String privateKey, String modules) {
        this.modules = modules;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /**
     * 获取系数、模数
     *
     * @return modules 系数、模数
     */
    public String getModules() {
        return this.modules;
    }

    /**
     * 设置系数、模数
     *
     * @param modules 系数、模数
     */
    public void setModules(String modules) {
        this.modules = modules;
    }

    /**
     * 获取私钥
     *
     * @return privateKey 私钥
     */
    public String getPrivateKey() {
        return this.privateKey;
    }

    /**
     * 设置私钥
     *
     * @param privateKey 私钥
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * 获取公钥
     *
     * @return publicKey 公钥
     */
    public String getPublicKey() {
        return this.publicKey;
    }

    /**
     * 设置公钥
     *
     * @param publicKey 公钥
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }


}
