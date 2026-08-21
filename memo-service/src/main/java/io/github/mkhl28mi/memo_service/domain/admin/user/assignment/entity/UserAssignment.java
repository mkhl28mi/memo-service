package io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import io.github.mkhl28mi.memo_service.domain.admin.department.unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.admin.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user_assignments")
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
	
	@NotNull(message = "Start date cannot be null")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    private LocalDateTime endDate;

	public UserAssignment() {
		super();
	}
	
	public UserAssignment(DepartmentUnit departmentUnit, User user, LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.departmentUnit = departmentUnit;
		this.user = user;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public Optional<LocalDateTime> getEndDate() {
		return Optional.ofNullable(endDate);
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	public UUID getId() {
		return id;
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
		return "UserAssignment [id=" + id + ", departmentUnit=" + departmentUnit + ", user=" + user + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
	
}
