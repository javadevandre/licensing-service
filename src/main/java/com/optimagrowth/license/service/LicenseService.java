package com.optimagrowth.license.service;

import java.util.Locale;
import java.util.Random;

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
	
	public License getLicense(String licenseId, String organizationId) {
		License license = new License();
		license.setId(new Random().nextInt(1000));
		license.setLicenseId(licenseId);
		license.setOrganizationId(organizationId);
		license.setDescription("Software product");
		license.setProductName("O-stock");
		license.setLicenseType("full");
		return license;
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
