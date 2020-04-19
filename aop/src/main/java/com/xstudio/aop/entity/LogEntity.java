package com.xstudio.aop.entity;

import lombok.Data;
import ua_parser.Client;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaobiao
 * @version 2020/2/16
 */
@Data
public class LogEntity implements Serializable {
    /**
     * 执行方法的类
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 输入参数
     */
    private Object inputs;

    /**
     * 输出参数
     */
    private Object outputs;

    /**
     * 执行耗时
     */
    private Long cost;

    /**
     * 行为人名称
     */
    private String actionName;

    /**
     * 执行时间
     */
    private Date time;

    /**
     * IP
     */
    private String ip;

    /**
     * 客户端信息
     */
    private UserAgent agent;

    public void setAgent(Client client) {
        agent = new UserAgent();
        agent.setDevice(client.device.family);
        agent.setFamily(client.userAgent.family);
        agent.setVersion(client.userAgent.major + client.userAgent.minor + client.userAgent.patch);

        agent.setOs(client.os.family);
        agent.setOsVersion(client.os.major + client.os.minor + client.os.patch + client.os.patchMinor);
    }
}
