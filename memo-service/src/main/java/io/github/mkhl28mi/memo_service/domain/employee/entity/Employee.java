package io.github.mkhl28mi.memo_service.domain.employee.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.employee_position.entity.EmployeePosition;
import io.github.mkhl28mi.memo_service.domain.memo_employee.entity.MemoEmployee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	private String fullName;
	
	@NotNull(message = "Target full name cannot be null")
	@Size(min = 1, max = 100, message = "Target full name must be between 1 and 100 characters")
	@Column(name = "target_full_name", nullable = false, length = 100)
	private String targetFullName;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "employee_employee_positions",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_position_id")
    )
    private Set<EmployeePosition> employeePositions = new HashSet<>();
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoEmployee> memoEmployees = new ArrayList<>();
    
	public Employee() {
		super();
	}

	public Employee(String fullName, String targetFullName) {
		super();
		this.fullName = fullName;
		this.targetFullName = targetFullName;
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
	
	public UUID getId() {
		return id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void addEmployeePosition(EmployeePosition employeePosition) {
        this.employeePositions.add(employeePosition);
        employeePosition.getEmployees().add(this);
    }
	
    public void removeEmployeePosition(EmployeePosition employeePosition) {
        this.employeePositions.remove(employeePosition);
        employeePosition.getEmployees().remove(this);
    }
	
	public void addMemoEmployee(MemoEmployee memoEmployee) {
	    this.memoEmployees.add(memoEmployee);
	    memoEmployee.setEmployee(this);
	}
	
	public void removeMemoEmployee(MemoEmployee memoEmployee) {
	    this.memoEmployees.remove(memoEmployee);
	    memoEmployee.setEmployee(null);
	}
	
	public Set<EmployeePosition> getEmployeePositions() {
		return Collections.unmodifiableSet(this.employeePositions);
	}
	
	public List<MemoEmployee> getMemoEmployees() {
		return Collections.unmodifiableList(memoEmployees);
	}
	
	public void initializeEmployeePositions() {
        Hibernate.initialize(this.employeePositions);
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
		return "Employee [id=" + id + ", fullName=" + fullName + ", targetFullName=" + targetFullName + ", createdAt="
				+ createdAt + "]";
	}

}
