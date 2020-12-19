package com.xstudio.location.baidu.response;

public enum BaiduStatusInfo {
    C0("正常"),
    C1("服务器内部错误"),
    C10("上传内容超过8M"),
    C101("AK参数不存在"),
    C102("MCODE参数不存在，mobile类型mcode参数必需"),
    C200("APP不存在，AK有误请检查再重试"),
    C201("APP被用户自己禁用，请在控制台解禁"),
    C202("APP被管理员删除"),
    C203("APP类型错误"),
    C210("APP IP校验失败"),
    C211("APP SN校验失败"),
    C220("APP Referer校验失败"),
    C230("APP Mcode码校验失败"),
    C240("APP 服务被禁用"),
    C250("用户不存在"),
    C251("用户被自己删除"),
    C252("用户被管理员删除"),
    C260("服务不存在"),
    C261("服务被禁用"),
    C301("永久配额超限，限制访问"),
    C302("天配额超限，限制访问"),
    C401("当前并发量已经超过约定并发配额，限制访问"),
    C402("当前并发量已经超过约定并发配额，并且服务总并发量也已经超过设定的总并发配额，限制访问");

    private String description;


    BaiduStatusInfo(String description) {
        this.description = description;
    }

    public static String getDescription(Integer infoCode) {
        BaiduStatusInfo[] values = BaiduStatusInfo.values();
        for (BaiduStatusInfo value : values) {
            if (value.name().equals("C" + infoCode)) {
                return value.getDescription();
            }
        }

        return "";
    }

    public String getDescription() {
        return description;
    }
}
