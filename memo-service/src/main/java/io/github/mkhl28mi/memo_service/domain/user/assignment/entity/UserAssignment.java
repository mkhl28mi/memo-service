package io.github.mkhl28mi.memo_service.domain.user.assignment.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "user_assignments")
@EntityListeners(AuditingEntityListener.class)
public class UserAssignment {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "DepartmentUnit cannot be null")
    @ManyToOne
    @JoinColumn(name = "department_unit_id", nullable = false)
	private DepartmentUnit departmentUnit;
	
	@NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

	public UserAssignment() {
		super();
	}
	
	public UserAssignment(DepartmentUnit departmentUnit, User user) {
		super();
		this.departmentUnit = departmentUnit;
		this.user = user;
	}

	public DepartmentUnit getDepartmentUnit() {
		return departmentUnit;
	}

	public void setDepartmentUnit(DepartmentUnit departmentUnit) {
		this.departmentUnit = departmentUnit;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
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
		UserAssignment other = (UserAssignment) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "UserAssignment [id=" + id + ", departmentUnit=" + departmentUnit + ", user=" + user + ", createdAt="
				+ createdAt + "]";
	}
	
}
