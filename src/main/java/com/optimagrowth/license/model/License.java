package com.optimagrowth.license.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=true)
@Entity
@Table(name = "tb_license")
public class License extends RepresentationModel<License> {
	@Id
	@Column(name = "license_id", nullable = false)
	private String licenseId;
	
	@Column(name = "organization_id", nullable = false)
	private String organizationId;
	
	@Column(name = "product_name", nullable = false)
	private String productName;
	
	@Column(name = "license_type", nullable = false)
	private String licenseType;
	
	private String description;

	private String comment;
	
	@Transient
	private Organization organization;
	
	public License withComment(String comment) {
		setComment(comment);
		return this;
	}
}
