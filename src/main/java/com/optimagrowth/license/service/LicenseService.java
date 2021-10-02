package com.optimagrowth.license.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.model.License;

@Service
public class LicenseService {
	
	@Autowired
	private MessageSource messageSource;

	public String createLicense(License license, String organizationId, Locale locale) {
		if (license != null) {
			license.setOrganizationId(organizationId);
		}
		return license != null ? String.format(messageSource.getMessage("license.create.message", null, locale), license, organizationId) : null;
	}
	
	public String getLicense(String licenseId, String organizationId, Locale locale) {
		return String.format(messageSource.getMessage("license.read.message", null, locale), licenseId, organizationId);
	}
	
	public String updateLicense(License license, String organizationId, Locale locale) {
		if (license != null) {
			license.setOrganizationId(organizationId);
		}
		return license != null ? String.format(messageSource.getMessage("license.update.message", null, locale), license, organizationId) : null;
	}
	
	public String deleteLicense(String licenseId, String organizationId, Locale locale) {
		return String.format(messageSource.getMessage("license.delete.message", null, locale), licenseId, organizationId);
	}
	
}
