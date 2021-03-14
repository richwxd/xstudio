package io.github.xbeeant.aop.entity;

import ua_parser.Client;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaobiao
 * @version 2020/2/16
 */
public class LogEntity implements Serializable {
    /**
     * 行为人名称
     */
    private String actionName;
    /**
     * 客户端信息
     */
    private UserAgent agent;
    /**
     * 执行方法的类
     */
    private String className;
    /**
     * 执行耗时
     */
    private Long cost;
    /**
     * 输入参数
     */
    private Object inputs;
    /**
     * IP
     */
    private String ip;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 输出参数
     */
    private Object outputs;
    /**
     * 执行时间
     */
    private Date time;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public UserAgent getAgent() {
        return agent;
    }

    public void setAgent(Client client) {
        agent = new UserAgent();
        agent.setDevice(client.device.family);
        agent.setFamily(client.userAgent.family);
        agent.setVersion(client.userAgent.major + client.userAgent.minor + client.userAgent.patch);

        agent.setOs(client.os.family);
        agent.setOsVersion(client.os.major + client.os.minor + client.os.patch + client.os.patchMinor);
    }

    public void setAgent(UserAgent agent) {
        this.agent = agent;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Object getInputs() {
        return inputs;
    }

    public void setInputs(Object inputs) {
        this.inputs = inputs;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getOutputs() {
        return outputs;
    }

    public void setOutputs(Object outputs) {
        this.outputs = outputs;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
