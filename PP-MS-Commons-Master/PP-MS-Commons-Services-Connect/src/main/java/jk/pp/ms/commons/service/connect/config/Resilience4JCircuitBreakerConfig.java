package jk.pp.ms.commons.service.connect.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@ConditionalOnProperty(name = "pp.ms.serviceconnect.circuitbreaker.enabled", havingValue = "true")
//@Profile("pp.ms.serviceconnect.circuitbreaker.enabled")
@Configuration
public class Resilience4JCircuitBreakerConfig {

	public static final String REST_TEMPLATE_BEAN_ID = "CircuitBreakerRestTemplate";

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build());
	}

	@Bean(name = REST_TEMPLATE_BEAN_ID)
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(Duration.ofMillis(3000)).setReadTimeout(Duration.ofMillis(3000)).build();
	}

}
