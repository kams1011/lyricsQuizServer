package kr.toy.lyricsQuizServer.config;


import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@ConfigurationProperties(prefix = "oauth")
@Component
public class SecurityProperties {
}
