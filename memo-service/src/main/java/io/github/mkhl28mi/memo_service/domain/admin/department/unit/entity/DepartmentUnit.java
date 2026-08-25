package io.github.mkhl28mi.memo_service.domain.admin.department.unit.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.admin.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "department_units")
@EntityListeners(AuditingEntityListener.class)
public class DepartmentUnit {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "Code cannot be null")
	@Size(min = 1, max = 8, message = "Code must be between 1 and 8 characters")
	@Column(name = "code", unique = true, nullable = false, length = 8)
	private String code;
	
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
	@Column(name = "is_enabled", nullable = false)
	private boolean enabled;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = true)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
   
    @OneToMany(mappedBy = "departmentUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List <UserAssignment> userAssignments = new ArrayList<>();
    
	protected DepartmentUnit() {
		super();
	}
	
	public DepartmentUnit(String code, Department department, boolean enabled) {
		super();
		this.code = code;
		this.department = department;
		this.enabled = enabled;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public UUID getId() {
		return id;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void addUserAssignment(UserAssignment userAssignment) {
	    this.userAssignments.add(userAssignment);
	    userAssignment.setDepartmentUnit(this);
	}
	
	public void removeUserAssignment(UserAssignment userAssignment) {
	    this.userAssignments.remove(userAssignment);
	    userAssignment.setDepartmentUnit(null);
	}
	
	public List<UserAssignment> getUserAssignments() {
		return Collections.unmodifiableList(userAssignments);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentUnit other = (DepartmentUnit) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "DepartmentUnit [id=" + id + ", code=" + code + ", department=" + department + ", enabled=" + enabled
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
