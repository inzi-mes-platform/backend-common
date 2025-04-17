package com.inzi.mes.be.common.bookmark.impl;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.inzi.mes.be.common.bookmark.BookmarkInfo;
import com.inzi.mes.be.common.user.impl.UserEntity;
import com.inzi.mes.be.framework.persist.PersistentEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="BOOKMARK")
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@Slf4j
public class BookmarkEntity extends PersistentEntity<BookmarkInfo> {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@Id
	private BookmarkEntityId entityId;
	
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="ID", nullable=false, updatable=false, insertable=false)
	private UserEntity user;
	
	@Column(name="PATH_NAME")
	private String pathName;
	
	@Column(name="STATE_JSON", columnDefinition="TEXT", length=4096)
	private String stateJson;
	
	@Column(name="DESCRIPTION", length=1024)
	private String description;
	
	public BookmarkEntity() {
		super();
	}

	@Override
	public BookmarkInfo info() {
		BookmarkInfo info = new BookmarkInfo();
		info.setUserId(this.entityId.getUserId());
		info.setName(this.entityId.getName());
		info.setPathName(this.getPathName());
		
		Gson gson=new GsonBuilder().setPrettyPrinting().create();
		info.setState(gson.fromJson(stateJson, new TypeToken<Map<String, String>>(){}.getType()));
		
		info.setDescription(this.getDescription());
		info.setCreatedBy(super.getCreatedBy());
		info.setCreatedDate(super.getCreatedDate());
		info.setUpdatedBy(super.getUpdatedBy());
		info.setUpdatedDate(super.getUpdatedDate());

		return info;

	}

	@Override
	public void from(BookmarkInfo info) {
		BookmarkEntityId entityId=new BookmarkEntityId();
		entityId.setUserId(info.getUserId());
		entityId.setName(info.getName());
		this.setEntityId(entityId);
		
		this.setPathName(info.getPathName());
		Gson gson=new GsonBuilder().setPrettyPrinting().create();
		this.setStateJson(gson.toJson(info.getState()));
		this.setDescription(info.getDescription());

		super.setCreatedBy(info.getCreatedBy());
		super.setCreatedDate(info.getCreatedDate());
		super.setUpdatedBy(info.getUpdatedBy());
		super.setUpdatedDate(info.getUpdatedDate());
	}

	@Override
	public String toPlainText() {
		return null;
	}
}
