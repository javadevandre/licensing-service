package com.optimagrowth.license.service.client.interceptor;

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class OrganizationClientInterceptor implements RequestInterceptor {
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String TOKEN_TYPE = "Bearer";

	@Override
	public void apply(RequestTemplate template) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String accessToken = ((SimpleKeycloakAccount) authentication.getDetails()).getKeycloakSecurityContext()
				.getTokenString();
		template.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, accessToken));
	}

}
