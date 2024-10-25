package com.kosmin.project.data_relation.integration.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("int")
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

  protected static final String COMMON_POST_URL = "expenses/v1/insert";
  protected static final String COMMON_GET_URL = "expenses/v1/records";
  @Autowired protected WebTestClient webTestClient;

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}
}
