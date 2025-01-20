package com.jj.social.util;

public class Script {
    public String back(String msg) {
        StringBuilder sb = new StringBuilder(msg);
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }
}
