package kr.toy.lyricsQuizServer.config.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.aws")
@Component
public class StorageProperties {

    @Value("${cloud.aws.region.static}")
    String region_static;  // static은 예약어이기 때문에 변수 선언이 불가함.

    S3 s3;

    Credentials credentials;

    Stack stack;


    @Getter
    @Setter
    public static class S3{

        String bucket;

    }

    @Getter
    @Setter
    public static class Credentials{

        String accessKey;
        String secretKey;

    }

    @Getter
    @Setter
    public static class Stack{

        boolean auto;

    }

}
