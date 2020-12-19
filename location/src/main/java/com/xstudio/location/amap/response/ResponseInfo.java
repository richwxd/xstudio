package com.xstudio.location.amap.response;

public enum ResponseInfo {

    C10000("请求正常"),
    C10001("key不正确或过期"),
    C10002("没有权限使用相应的服务或者请求接口的路径拼写错误"),
    C10003("访问已超出日访问量"),
    C10004("单位时间内访问过于频繁"),
    C10005("IP白名单出错，发送请求的服务器IP不在IP白名单内"),
    C10006("绑定域名无效"),
    C10007("数字签名未通过验证"),
    C10008("MD5安全码未通过验证"),
    C10009("请求key与绑定平台不符"),
    C10010("IP访问超限"),
    C10011("服务不支持https请求"),
    C10012("权限不足，服务请求被拒绝"),
    C10013("Key被删除"),
    C10014("云图服务QPS超限"),
    C10015("受单机QPS限流限制"),
    C10016("服务器负载过高"),
    C10017("所请求的资源不可用"),
    C10019("使用的某个服务总QPS超限"),
    C10020("某个Key使用某个服务接口QPS超出限制"),
    C10021("账号使用某个服务接口QPS超出限制"),
    C10026("账号处于被封禁状态"),
    C10029("某个Key的QPS超出限制"),
    C10044("账号维度日调用量超出限制"),
    C10045("账号维度海外服务日调用量超出限制"),
    C20000("请求参数非法"),
    C20001("缺少必填参数"),
    C20002("请求协议非法"),
    C20003("其他未知错误"),
    C20011("查询坐标或规划点（包括起点、终点、途经点）在海外，但没有海外地图权限"),
    C20012("查询信息存在非法内容"),
    C20800("规划点（包括起点、终点、途经点）不在中国陆地范围内"),
    C20801("划点（起点、终点、途经点）附近搜不到路"),
    C20802("路线计算失败，通常是由于道路连通关系导致"),
    C20803("起点终点距离过长"),
    C30001("服务响应失败"),
    C30002("服务响应失败"),
    C30003("服务响应失败"),
    C32000("服务响应失败"),
    C32002("服务响应失败"),
    C32003("服务响应失败"),
    C32200("服务响应失败"),
    C32201("服务响应失败"),
    C32202("服务响应失败"),
    C32203("服务响应失败"),
    C40000("余额耗尽"),
    C40001("围栏个数达到上限"),
    C40002("购买服务到期"),
    C40003("海外服务余额耗尽");

    private String description;

    public static String getDescription(Integer infoCode) {
        ResponseInfo[] values = ResponseInfo.values();
        for (ResponseInfo value : values) {
            if (value.name().equals("C" + infoCode)) {
                return value.getDescription();
            }
        }

        return "";
    }

    public String getDescription() {
        return description;
    }

    ResponseInfo(String description) {
        this.description = description;
    }
}
