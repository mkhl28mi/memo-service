package io.github.mkhl28mi.memo_service.domain.employee.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.employee_position.entity.EmployeePosition;
import io.github.mkhl28mi.memo_service.domain.memo_employee.entity.MemoEmployee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
public class Employee {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "Full name cannot be null")
	@Size(min = 1, max = 100, message = "Full name must be between 1 and 100 characters")
	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName; // TODO pattern check
	
	@NotNull(message = "Target full name cannot be null")
	@Size(min = 1, max = 100, message = "Target full name must be between 1 and 100 characters")
	@Column(name = "target_full_name", nullable = false, length = 100)
	private String targetFullName; // TODO pattern check
	
	@NotNull(message = "Emloyee position cannot be null")
	@ManyToOne
	@JoinColumn(name = "emloyee_position_id", nullable = false)
	private EmployeePosition employeePosition;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @ManyToMany(mappedBy = "employees")
    private Set<Department> departments = new HashSet<>();
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoEmployee> memosEmployees = new ArrayList<>();
    
	public Employee() {
		super();
	}

	public Employee(String fullName, String targetFullName, EmployeePosition employeePosition) {
		super();
		this.fullName = fullName;
		this.targetFullName = targetFullName;
		this.employeePosition = employeePosition;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTargetFullName() {
		return targetFullName;
	}

	public void setTargetFullName(String targetFullName) {
		this.targetFullName = targetFullName;
	}
	
	public EmployeePosition getEmployeePosition() {
		return employeePosition;
	}

	public void setEmployeePosition(EmployeePosition employeePosition) {
		this.employeePosition = employeePosition;
	}

	public UUID getId() {
		return id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public Set<Department> getDepartments() {
		return departments;
	}
	
	public void addMemoEmployee(MemoEmployee memoEmployee) {
	    this.memosEmployees.add(memoEmployee);
	    memoEmployee.setEmployee(this);
	}
	
	public void removeMemoEmployee(MemoEmployee memoEmployee) {
	    this.memosEmployees.remove(memoEmployee);
	    memoEmployee.setEmployee(null);
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
		Employee other = (Employee) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", fullName=" + fullName + ", targetFullName=" + targetFullName
				+ ", employeePosition=" + employeePosition + ", createdAt=" + createdAt + "]";
	}

}
