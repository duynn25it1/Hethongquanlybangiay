package shared;

import java.io.Serializable;

public class Request implements Serializable {
    public String action;
    public Object data;

    public Request(String action, Object data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public Object getData() {
        return data;
    }
}