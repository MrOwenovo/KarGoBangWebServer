package com.example.config;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@ToString
@Component
@ConfigurationProperties(prefix = "constant")
public class ApplicationPro {

    public static String crossOriginValue = null;

    public void setCrossOriginValue(String crossOriginValue) {
        ApplicationPro.crossOriginValue = crossOriginValue;
    }

    public String getCrossOriginValue() {
        return ApplicationPro.crossOriginValue;
    }
}
