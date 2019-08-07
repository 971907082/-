package org.wlxy.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件实体类
 */
@Component
@ConfigurationProperties(prefix = "app")
@Data
public class SystemConfig {
    private  String info;
    private  String author;
    private  String email;


    private String SwaggerTitle;
    private String SwaggerContactName;
    private String SwaggerContactWebUrl;
    private String SwaggerContactEmail;
    private String SwaggerVersion;
    private String SwaggerDescription;
    private String SwaggerTermsOfServiceUrl;


}
