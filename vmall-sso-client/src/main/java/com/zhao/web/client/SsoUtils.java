package com.zhao.web.client;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-30 18:19
 * @描述
 */
public class SsoUtils{
    private static String sso_server;

    public static void setSso_server(String sso_server) {
        SsoUtils.sso_server = sso_server;
    }

    public static String getSso_server() {
        return sso_server;
    }
}
