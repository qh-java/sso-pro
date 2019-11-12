package com.huaita.ssoclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "sso", ignoreUnknownFields = false)
@PropertySource("classpath:conf/sso.properties")
@Component
public class SsoProperty {

  private String authCheck;

  private String authPreLogin;


    public String getAuthCheck() {
        return authCheck;
    }

    public void setAuthCheck(String authCheck) {
        this.authCheck = authCheck;
    }

    public String getAuthPreLogin() {
        return authPreLogin;
    }

    public void setAuthPreLogin(String authPreLogin) {
        this.authPreLogin = authPreLogin;
    }
}
