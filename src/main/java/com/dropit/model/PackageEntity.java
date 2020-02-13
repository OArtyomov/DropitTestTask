package com.dropit.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@DynamicUpdate
@Table(name = "package", schema = "public")
@Getter
@Setter
@javax.persistence.Entity
@EqualsAndHashCode(of = {"id"})
public class PackageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 36, nullable = false, name = "tag")
	private String tag;

	@ManyToOne
	@JoinColumn(name = "delivery_id", insertable = true, updatable = true)
	private DeliveryEntity delivery;

}