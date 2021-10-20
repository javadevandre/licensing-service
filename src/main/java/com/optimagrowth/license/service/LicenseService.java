package com.optimagrowth.license.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.LicenseRepository;
import com.optimagrowth.license.service.client.OrganizationClient;

@Service
public class LicenseService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ServiceConfig config;

	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired OrganizationClient organizationClient;

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

}
