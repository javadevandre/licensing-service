package com.optimagrowth.license.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.optimagrowth.license.model.Organization;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient("organization-service")
@CircuitBreaker(name = "organization-service", fallbackMethod = "organizationServiceFallback")
public interface OrganizationClient {
	
	@GetMapping(value = "/v1/organization/{organizationId}", consumes = "application/json")
	Organization getOrganization(@PathVariable("organizationId") String organizationId);
	
	default Organization organizationServiceFallback(String organizationId, Throwable t) {
		Organization fallbackOrganization = new Organization();
		fallbackOrganization.setOrganizationId(organizationId);
		fallbackOrganization.setName("No organization information currently available");
		return fallbackOrganization;
	}

}
