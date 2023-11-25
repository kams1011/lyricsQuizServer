package kr.toy.lyricsQuizServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
		exclude = {
		org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
		org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
		org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
}) // aws s3 세팅으로 인해 어플리케이션 기동이 느려지는 걸 방지하기 위해 추가
@EnableFeignClients
public class LyricsQuizServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyricsQuizServerApplication.class, args);
	}

}
