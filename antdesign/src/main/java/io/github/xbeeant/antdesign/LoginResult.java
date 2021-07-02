package io.github.xbeeant.antdesign;

/**
 * @author xiaobiao
 * @version 2021/7/2
 */
public class LoginResult {
    private String currentAuthority;
    private String status;
    private String type;

    public String getCurrentAuthority() {
        return currentAuthority;
    }

    public void setCurrentAuthority(String currentAuthority) {
        this.currentAuthority = currentAuthority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
