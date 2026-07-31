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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.memo_employee.entity.MemoEmployee;
import io.github.mkhl28mi.memo_service.domain.position.entity.Position;
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
	@Column(name = "full_name", unique = true, nullable = false, length = 100)
	private String fullName;
	
	@NotNull(message = "Target full name cannot be null")
	@Size(min = 1, max = 100, message = "Target full name must be between 1 and 100 characters")
	@Column(name = "target_full_name", unique = true, nullable = false, length = 100)
	private String targetFullName;
	
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
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "employee_positions",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    private Set<Position> positions = new HashSet<>();
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoEmployee> memoEmployees = new ArrayList<>();
    
	public Employee() {
		super();
	}
	
	public Employee(String fullName, String targetFullName, boolean enabled) {
		super();
		this.fullName = fullName;
		this.targetFullName = targetFullName;
		this.enabled = enabled;
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
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
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

	public void addPosition(Position position) {
        this.positions.add(position);
        position.getEmployees().add(this);
    }
	
    public void removePosition(Position position) {
        this.positions.remove(position);
        position.getEmployees().remove(this);
    }
	
	public void addMemoEmployee(MemoEmployee memoEmployee) {
	    this.memoEmployees.add(memoEmployee);
	    memoEmployee.setEmployee(this);
	}
	
	public void removeMemoEmployee(MemoEmployee memoEmployee) {
	    this.memoEmployees.remove(memoEmployee);
	    memoEmployee.setEmployee(null);
	}
	
	public Set<Position> getPositions() {
		return Collections.unmodifiableSet(this.positions);
	}
	
	public List<MemoEmployee> getMemoEmployees() {
		return Collections.unmodifiableList(memoEmployees);
	}
	
	public void initializePositions() {
        Hibernate.initialize(this.positions);
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
		return "Employee [id=" + id + ", fullName=" + fullName + ", targetFullName=" + targetFullName + ", enabled="
				+ enabled + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
