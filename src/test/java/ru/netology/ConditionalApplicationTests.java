package ru.netology;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> devContainer = new GenericContainer<>("devapp").withExposedPorts(8080);
    public static GenericContainer<?> prodContainer = new GenericContainer<>("prodapp").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devContainer.start();
        prodContainer.start();
    }

    @Test
    void contextLoads() {
        System.out.println(devContainer.getMappedPort(8080));
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + 8080, String.class);
        System.out.println(forEntity.getBody());
    }

}
