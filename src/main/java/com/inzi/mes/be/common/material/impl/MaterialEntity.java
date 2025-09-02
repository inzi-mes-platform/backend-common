package com.inzi.mes.be.common.material.impl;

import org.hibernate.annotations.ColumnDefault;

import com.inzi.mes.be.common.material.MaterialInfo;
import com.inzi.mes.be.framework.persist.PersistentEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Builder
@Entity
@Table(
		name="MATERIAL",
		indexes= {
			@Index(name="idx_name", columnList="NAME"),
		}
)
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Slf4j
public class MaterialEntity extends PersistentEntity<MaterialInfo> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODE")
	private String code;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="CATEGORY")
	private String category;
	
	@Column(name="SPEC_JSON", columnDefinition="TEXT")
	@Lob
	private String specJson;
	
	@Column(name="CURRENT_STOCK")
	private Float currentStock;
	
	@Column(name="SAFETY_STOCK")
	private Float safetyStock;
	
	@Column(name="UNIT")
	private String unit;
	
	@Column(name="UNIT_PRICE")
	private Float unitPrice;
	
	@Column(name="ENABLED", nullable=false)
	@ColumnDefault("true")
	private Boolean enabled;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	public MaterialEntity() {
		
	}

	@Override
	public MaterialInfo info() {
		MaterialInfo info=new MaterialInfo();
		info.setCode(this.getCode());
		info.setName(this.getName());
		info.setCategory(this.getCategory());
		info.setSpecJson(this.getSpecJson());
		info.setCurrentStock(this.getCurrentStock());
		info.setSafetyStock(this.getSafetyStock());
		info.setUnit(this.getUnit());
		info.setUnitPrice(this.getUnitPrice());
		info.setEnabled(this.getEnabled());
		info.setDescription(this.getDescription());
		
		info.setCreatedBy(this.getCreatedBy());
		info.setCreatedDate(this.getCreatedDate());
		info.setUpdatedBy(this.getUpdatedBy());
		info.setUpdatedDate(this.getUpdatedDate());

		return info;
	}

	@Override
	public void from(MaterialInfo info) {
		this.setCode(info.getCode());
		this.setName(info.getName());
		this.setCategory(info.getCategory());
		this.setSpecJson(info.getSpecJson());
		this.setCurrentStock(info.getCurrentStock());
		this.setSafetyStock(info.getSafetyStock());
		this.setUnit(info.getUnit());
		this.setUnitPrice(info.getUnitPrice());
		this.setEnabled(info.getEnabled());
		this.setDescription(info.getDescription());
		
		this.setCreatedBy(info.getCreatedBy());
		this.setCreatedDate(info.getCreatedDate());
		this.setUpdatedBy(info.getUpdatedBy());
		this.setUpdatedDate(info.getUpdatedDate());
	}

	@Override
	public String toPlainText() {
		return null;
	}
}
