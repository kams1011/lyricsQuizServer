package kr.toy.lyricsQuizServer.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import kr.toy.lyricsQuizServer.config.ConfigurationProperties.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class S3Config {

    private final StorageProperties storageProperties;

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(storageProperties.getCredentials().getAccessKey(), storageProperties.getCredentials().getSecretKey());

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(storageProperties.getRegion_static())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
