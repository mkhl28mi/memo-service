package io.github.mkhl28mi.memo_service.domain.application_setting.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "aplication_settings")
@EntityListeners(AuditingEntityListener.class)
public class AplicationSetting {
	
    @Size(min = 1, max = 50, message = "Key must be between 1 and 50 characters")
	@Id
    @Column(name = "setting_key", length = 50)
	private String key;
	
	@NotNull(message = "Value cannot be null")
    @Size(min = 1, max = 50, message = "Value must be between 1 and 50 characters")
	@Column(name = "setting_value", nullable = false, length = 50)
	private String value;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

	public AplicationSetting() {
		super();
	}
	
	public AplicationSetting(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AplicationSetting other = (AplicationSetting) obj;
		return Objects.equals(key, other.key);
	}

	@Override
	public String toString() {
		return "AplicationSetting [key=" + key + ", value=" + value + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
}
