package cn.gch.girl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "girl")
public class GirlProperties {
    private String cupSize ;

    private Integer age ;
}
