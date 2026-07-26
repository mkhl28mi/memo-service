package io.github.mkhl28mi.memo_service.domain.department.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.employee_position.entity.EmployeePosition;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "departments")
@EntityListeners(AuditingEntityListener.class)
public class Department {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
	@Column(name = "name", nullable = false, length = 100)
	private String name;
    
    @NotNull(message = "Code cannot be null")
    @Size(min = 1, max = 8, message = "Code must be between 1 and 8 characters")
	@Column(name = "code", nullable = false, length = 8)
    private String code;
    
    @NotNull(message = "Description cannot be null")
    @Size(min = 1, max = 250, message = "Description must be between 1 and 250 characters")
	@Column(name = "description", nullable = false, length = 250)
    private String description;
    
    @NotNull(message = "Employee position cannot be null")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_position_id", referencedColumnName = "id")
    private EmployeePosition employeePosition;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <DepartmentUnit> departmentUnits = new ArrayList<>();
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Memo> memos = new ArrayList<>();
    
    protected Department() {
		super();
	}
    
	public Department(String name, String code,String description, EmployeePosition employeePosition) {
		super();
		this.name = name;
		this.code = code;
		this.description = description;
		this.employeePosition = employeePosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public void addDepartmentUnit(DepartmentUnit departmentUnit) {
	    this.departmentUnits.add(departmentUnit);
	    departmentUnit.setDepartment(this);
	}

	public void removeDepartmentUnit(DepartmentUnit departmentUnit) {
	    this.departmentUnits.remove(departmentUnit);
	    departmentUnit.setDepartment(null);
	}
	
	public void addMemo(Memo memo) {
	    this.memos.add(memo);
	    memo.setDepartment(this);
	}
	
	public void removeMemo(Memo memo) {
	    this.memos.remove(memo);
	    memo.setDepartment(null);
	}
	
	public List<DepartmentUnit> getDepartmentUnits() {
		return Collections.unmodifiableList(this.departmentUnits);
	}

	public List<Memo> getMemos() {
		return Collections.unmodifiableList(this.memos);
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
		Department other = (Department) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", code=" + code + ", description=" + description
				+ ", createdAt=" + createdAt + ", employeePosition=" + employeePosition + "]";
	}
	
}
