package com.optimagrowth.license.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=true)
public class License extends RepresentationModel<License> {
	private int id;
	private String licenseId;
	private String description;
	private String organizationId;
	private String productName;
	private String licenseType;
}
