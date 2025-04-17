package com.inzi.mes.be.common.bookmark.impl;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkEntityId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="NAME")
	private String name;

	public boolean equals(Object object) {
        if (object instanceof BookmarkEntityId) {
        	BookmarkEntityId pk = (BookmarkEntityId)object;
            return userId.equals(pk.userId) && name.equals(pk.name);
        } else {
            return false;
        }
	}
	
	 public int hashCode() {
		 return userId.hashCode() + name.hashCode();
	 }
}
