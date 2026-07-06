package io.github.mkhl28mi.memo_service.domain.memo_employee.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.employee_position.entity.EmployeePosition;
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
import jakarta.validation.constraints.Size;

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
	
	@NotNull(message = "Employee cannot be null")
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@NotNull(message = "Employee position cannot be null")
    @ManyToOne
    @JoinColumn(name = "employee_position_id", nullable = false)
	private EmployeePosition employeePosition;
	
	@NotNull(message = "Role cannot be null")
    @Size(min = 1, max = 20, message = "Role must be between 1 and 20 characters")
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
			Employee employee, 
			EmployeePosition employeePosition,
			Role role,
			int placementOrder) {
		super();
		this.memo = memo;
		this.employee = employee;
		this.employeePosition = employeePosition;
		this.role = role;
		this.placementOrder = placementOrder;
	}
	
	public Memo getMemo() {
		return memo;
	}

	public void setMemo(Memo memo) {
		this.memo = memo;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmployeePosition getEmployeePosition() {
		return employeePosition;
	}

	public void setEmployeePosition(EmployeePosition employeePosition) {
		this.employeePosition = employeePosition;
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
		return "MemoEmployee [id=" + id + ", memo=" + memo + ", employee=" + employee + ", employeePosition="
				+ employeePosition + ", role=" + role + ", placementOrder=" + placementOrder + ", createdAt="
				+ createdAt + "]";
	}

	public enum Role {
		
		RECIPIENT,
		
		COPY_RECIPIENT,
		
		SIGNER,
		
		APPROVER
		
	}
}
