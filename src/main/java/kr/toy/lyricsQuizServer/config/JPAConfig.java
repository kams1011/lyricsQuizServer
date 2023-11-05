package kr.toy.lyricsQuizServer.config;

import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class JPAConfig {

    @PersistenceContext
    private EntityManager entityManager;
}
