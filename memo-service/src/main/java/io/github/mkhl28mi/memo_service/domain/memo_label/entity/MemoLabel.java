package io.github.mkhl28mi.memo_service.domain.memo_label.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
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
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "memo_labels")
@EntityListeners(AuditingEntityListener.class)
public class MemoLabel {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "Memo cannot be null")
    @ManyToOne
    @JoinColumn(name = "memo_id", nullable = false)
	private Memo memo;
	
	@NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
	private User createdBy;
	
	@NotNull(message = "Department unit cannot be null")
    @ManyToOne
    @JoinColumn(name = "department_unit_id", nullable = false)
	private DepartmentUnit departmentUnit;
	
	@NotNull(message = "Name cannot be null")
	@Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

	public MemoLabel() {
		super();
	}

	public MemoLabel(Memo memo,
			User createdBy,
			DepartmentUnit departmentUnit,
			String name) {
		super();
		this.memo = memo;
		this.createdBy = createdBy;
		this.departmentUnit = departmentUnit;
		this.name = name;
	}

	public Memo getMemo() {
		return memo;
	}

	public void setMemo(Memo memo) {
		this.memo = memo;
	}

	public DepartmentUnit getDepartmentUnit() {
		return departmentUnit;
	}

	public void setDepartmentUnit(DepartmentUnit departmentUnit) {
		this.departmentUnit = departmentUnit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getCreatedBy() {
		return createdBy;
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
		MemoLabel other = (MemoLabel) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "MemoLabel [id=" + id + ", memo=" + memo + ", createdBy=" + createdBy + ", departmentUnit=" + departmentUnit
				+ ", name=" + name + ", createdAt=" + createdAt + "]";
	}
	
}
