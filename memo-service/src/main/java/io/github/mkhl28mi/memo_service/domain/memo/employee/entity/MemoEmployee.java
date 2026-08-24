package io.github.mkhl28mi.memo_service.domain.memo.employee.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.entity.EmployeeAssignment;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "memo_employees")
@EntityListeners(AuditingEntityListener.class)
public class MemoEmployee {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "Memo cannot be null")
    @ManyToOne
    @JoinColumn(name = "memo_id", nullable = false)
	private Memo memo;
	
	@NotNull(message = "EmployeeAssignment cannot be null")
    @ManyToOne
    @JoinColumn(name = "employee_assignment_id", nullable = false)
	private EmployeeAssignment employeeAssignment;
	
	@NotNull(message = "Role cannot be null")
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20)
	private Role role;
	
	@Positive
	@Column(name = "placement_order", nullable = false)
	private int placementOrder;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
	
	public MemoEmployee() {
		super();
	}
	
	public MemoEmployee(Memo memo, 
			EmployeeAssignment employeeAssignment, 
			Role role,
			int placementOrder) {
		super();
		this.memo = memo;
		this.employeeAssignment = employeeAssignment;
		this.role = role;
		this.placementOrder = placementOrder;
	}
	
	public Memo getMemo() {
		return memo;
	}

	public void setMemo(Memo memo) {
		this.memo = memo;
	}

	public EmployeeAssignment getEmployeeAssignment() {
		return employeeAssignment;
	}

	public void setEmployeeAssignment(EmployeeAssignment employeeAssignment) {
		this.employeeAssignment = employeeAssignment;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public int getPlacementOrder() {
		return placementOrder;
	}

	public void setPlacementOrder(int placementOrder) {
		this.placementOrder = placementOrder;
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
		MemoEmployee other = (MemoEmployee) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "MemoEmployee [id=" + id + ", memo=" + memo + ", employeeAssignment=" + employeeAssignment + ", role="
				+ role + ", placementOrder=" + placementOrder + ", createdAt=" + createdAt + "]";
	}

	public enum Role {
		
		RECIPIENT,
		
		COPY_RECIPIENT,
		
		SIGNER,
		
		APPROVER
		
	}
}
