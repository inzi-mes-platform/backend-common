package com.inzi.mes.be.endpoints;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition
(
		info=@Info(
				title="MES V1 API Spec",
				description="INZI 표준 MES 시스템의 서버측 REST API",
				version="v0.0.1"
		)
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi chatOpenApi() {
		// "api/v1/**" 경로에 매칭되는 API를 그룹화하여 문서화 한다
		String[] paths={ "/api/v1/**", "/auth/**" };
		return GroupedOpenApi.builder()
				// Group Name
				.group("MES API v1")
				// Define Path pattern which included in group
				.pathsToMatch(paths)
				.build();
	}
}
