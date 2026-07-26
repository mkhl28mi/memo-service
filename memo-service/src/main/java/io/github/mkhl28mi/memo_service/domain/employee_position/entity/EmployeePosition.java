package io.github.mkhl28mi.memo_service.domain.employee_position.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.memo_employee.entity.MemoEmployee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employee_positions")
@EntityListeners(AuditingEntityListener.class)
public class EmployeePosition {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "Name cannot be null")
	@Size(min = 1, max = 400, message = "Name must be between 1 and 400 characters")
	@Column(name = "name", nullable = false, length = 400)
	private String name;
	
	@NotNull(message = "Target name cannot be null")
	@Size(min = 1, max = 400, message = "Target name must be between 1 and 400 characters")
	@Column(name = "target_name", nullable = false, length = 400)
	private String targetName;
	
	@PositiveOrZero(message = "Order cannot be less than zero")
	@Column(name = "placement_order")
	private int placementOrder;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @ManyToMany(mappedBy = "employeePositions")
    private Set<Employee> employees = new HashSet<>();
    
    @OneToMany(mappedBy = "employeePosition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoEmployee> memoEmployees = new ArrayList<>();
    
	public EmployeePosition() {
		super();
	}

	public EmployeePosition(String name, String targetName, int placementOrder) {
		super();
		this.name = name;
		this.targetName = targetName;
		this.placementOrder = placementOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
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
	
	public void addMemoEmployee(MemoEmployee memoEmployee) {
	    this.memoEmployees.add(memoEmployee);
	    memoEmployee.setEmployeePosition(this);
	}
	
	public void removeMemoEmployee(MemoEmployee memoEmployee) {
	    this.memoEmployees.remove(memoEmployee);
	    memoEmployee.setEmployeePosition(null);
	}
	
	public Set<Employee> getEmployees() {
		return this.employees;
	}
	
	public List<MemoEmployee> getMemoEmployees() {
		return Collections.unmodifiableList(this.memoEmployees);
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
		EmployeePosition other = (EmployeePosition) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "EmployeePosition [id=" + id + ", name=" + name + ", targetName=" + targetName + ", placementOrder="
				+ placementOrder + ", createdAt=" + createdAt + "]";
	}

}
