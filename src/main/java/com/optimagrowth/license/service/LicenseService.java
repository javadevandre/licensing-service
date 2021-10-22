package com.optimagrowth.license.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.LicenseRepository;
import com.optimagrowth.license.service.client.OrganizationClient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class LicenseService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServiceConfig config;

	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private OrganizationClient organizationClient;

	public License createLicense(License license, String organizationId, Locale locale) {
		License savedLicense = licenseRepository.save(license);
		return savedLicense.withComment(config.getProperty());
	}

	public License readLicense(String licenseId, String organizationId, Locale locale) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		if (license == null) {
			throw new IllegalArgumentException(String.format(
				messageSource.getMessage("license.read.error.message", null, locale), licenseId, organizationId));
		}
		Organization organization = organizationClient.getOrganization(organizationId);
		if (organization != null) {
			license.setOrganization(organization);
		}
		return license.withComment(config.getProperty());
	}

	public License updateLicense(License license, String organizationId, Locale locale) {
		License updatedLicense = licenseRepository.save(license);
		return updatedLicense.withComment(config.getProperty());
	}

	public String deleteLicense(String licenseId, String organizationId, Locale locale) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		licenseRepository.delete(license);
		return String.format(messageSource.getMessage("license.delete.message", null, locale), licenseId,
				organizationId);
	}
	
	@Retry(name = "license-db", fallbackMethod = "licenseDbFallback")
	@CircuitBreaker(name = "license-db", fallbackMethod = "licenseDbFallback")
	@RateLimiter(name = "license-db", fallbackMethod = "licenseDbFallback")
	@Bulkhead(name = "license-db", type = Type.THREADPOOL, fallbackMethod = "licenseDbFallback")
	public List<License> getLicensesByOrganization(String organizationId) {
		return licenseRepository.findByOrganizationId(organizationId);
	}
	
	@SuppressWarnings("unused")
	private List<License> licenseDbFallback(String organizationId, Throwable t) {
		License fallbackLicense = new License();
		fallbackLicense.setOrganizationId(organizationId);
		fallbackLicense.setProductName("No licensing information currently available");
		return List.of(fallbackLicense);
	}
	
}
