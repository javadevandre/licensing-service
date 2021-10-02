package com.optimagrowth.license.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
public class LicenseController {
	
	@Autowired
	private LicenseService licenseService;

	@PostMapping("/{licenseId}")
	public ResponseEntity<String> createLicense(
			@RequestHeader(value = "Accept-Language", required = false) Locale locale,
			@PathVariable("organizationId") String organizationId,
			@RequestBody License license) {
		return ResponseEntity.ok(licenseService.createLicense(license, organizationId, locale));
	}
	
	@GetMapping("/{licenseId}")
	public ResponseEntity<String> readLicense(
			@RequestHeader(value = "Accept-Language", required = false) Locale locale,
			@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		return ResponseEntity.ok(licenseService.getLicense(licenseId, organizationId, locale));
	}
	
	@PutMapping("/{licenseId}")
	public ResponseEntity<String> updateLicense(
			@RequestHeader(value = "Accept-Language", required = false) Locale locale,
			@PathVariable("organizationId") String organizationId,
			@RequestBody License license) {
		return ResponseEntity.ok(licenseService.updateLicense(license, organizationId, locale));
	}
	
	@DeleteMapping("/{licenseId}")
	public ResponseEntity<String> deleteLicense(
			@RequestHeader(value = "Accept-Language", required = false) Locale locale,
			@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId, locale));
	}
}
