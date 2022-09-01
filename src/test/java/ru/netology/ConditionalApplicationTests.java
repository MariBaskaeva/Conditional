package ru.netology;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
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
    void devTest() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + devContainer.getMappedPort(8080) + "/profile", String.class);
        Assertions.assertEquals("Current profile is dev", forEntity.getBody());
    }

    @Test
    void prodTest() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + prodContainer.getMappedPort(8081) + "/profile", String.class);
        Assertions.assertEquals("Current profile is production", forEntity.getBody());
    }
}
