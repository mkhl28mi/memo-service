package io.github.mkhl28mi.memo_service.domain.department_unit.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
import io.github.mkhl28mi.memo_service.domain.memo_label.entity.MemoLabel;
import io.github.mkhl28mi.memo_service.domain.memo_log.entity.MemoLog;
import io.github.mkhl28mi.memo_service.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "department_units")
@EntityListeners(AuditingEntityListener.class)
public class DepartmentUnit {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "Code cannot be null")
	@Size(min = 1, max = 8, message = "Code must be between 1 and 8 characters")
	@Column(name = "code", unique = true, nullable = false, length = 8)
	private String code;
	
	@NotNull(message = "Department cannot be null")
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
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
    
    @OneToMany(mappedBy = "departmentUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Memo> memos = new ArrayList<>();
    
    @OneToMany(mappedBy = "departmentUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoLog> memoLogs = new ArrayList<>();
    
    @OneToMany(mappedBy = "departmentUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoLabel> memoLabels = new ArrayList<>();
    
    @OneToMany(mappedBy = "departmentUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <User> users = new ArrayList<>();
    
	protected DepartmentUnit() {
		super();
	}
	
	public DepartmentUnit(String code, Department department, boolean enabled) {
		super();
		this.code = code;
		this.department = department;
		this.enabled = enabled;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public void addMemo(Memo memo) {
	    this.memos.add(memo);
	    memo.setDepartmentUnit(this);
	}
	
	public void removeMemo(Memo memo) {
	    this.memos.remove(memo);
	    memo.setDepartmentUnit(null);
	}
	
	public void addMemoLog(MemoLog memoLog) {
	    this.memoLogs.add(memoLog);
	    memoLog.setDepartmentUnit(this);
	}
	
	public void removeMemoLog(MemoLog memoLog) {
	    this.memoLogs.remove(memoLog);
	    memoLog.setDepartmentUnit(null);
	}
	
	public void addMemoLabel(MemoLabel memoLabel) {
	    this.memoLabels.add(memoLabel);
	    memoLabel.setDepartmentUnit(this);
	}
	
	public void removeMemoLabel(MemoLabel memoLable) {
	    this.memoLabels.remove(memoLable);
	    memoLable.setDepartmentUnit(null);
	}
	
	public void addUser(User user) {
	    this.users.add(user);
	    user.setDepartmentUnit(this);
	}
	
	public void removeUser(User user) {
	    this.users.remove(user);
	    user.setDepartmentUnit(null);
	}
	
	public List<Memo> getMemos() {
		return Collections.unmodifiableList(memos);
	}

	public List<MemoLog> getMemoLogs() {
		return Collections.unmodifiableList(memoLogs);
	}
	
	public List<MemoLabel> getMemoLabels() {
		return Collections.unmodifiableList(memoLabels);
	}
	
	public List<User> getUsers() {
		return Collections.unmodifiableList(users);
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
		DepartmentUnit other = (DepartmentUnit) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "DepartmentUnit [id=" + id + ", code=" + code + ", department=" + department + ", enabled=" + enabled
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
