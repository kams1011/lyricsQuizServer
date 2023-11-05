package kr.toy.lyricsQuizServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
public class LyricsQuizServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyricsQuizServerApplication.class, args);
	}

}
