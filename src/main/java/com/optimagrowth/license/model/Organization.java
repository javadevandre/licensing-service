package com.optimagrowth.license.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=true)
public class Organization extends RepresentationModel<Organization> {
	private String organizationId;
	private String name;
	private String contactName;
	private String contactEmail;
	private String contactPhone;
}
