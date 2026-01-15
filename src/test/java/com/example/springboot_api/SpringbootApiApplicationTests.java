package com.example.springboot_api;

import com.example.springboot_api.security.RateLimitFilter;
import com.example.springboot_api.service.JwtAuthFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SpringbootApiApplicationTests {

	@Mock
	private RateLimitFilter rateLimitFilter;

	@Mock
	private JwtAuthFilter jwtAuthFilter;

	@Test
	void contextLoads() {
	}
}
