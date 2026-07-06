package io.github.mkhl28mi.memo_service.domain.department_unit.entity;

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
	@Column(name = "code", nullable = false, length = 8)
	private String code;
	
	@NotNull(message = "Department cannot be null")
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Memo> memos = new ArrayList<>();
    
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoLog> memoLogs = new ArrayList<>();
    
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoLabel> memoLabels = new ArrayList<>();
    
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <User> users = new ArrayList<>();
    
	protected DepartmentUnit() {
		super();
	}

	public DepartmentUnit(String code, Department department) {
		super();
		this.code = code;
		this.department = department;
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
	
	public UUID getId() {
		return id;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void addMemo(Memo memo) {
	    this.memos.add(memo);
	    memo.setPosition(this);
	}
	
	public void removeMemo(Memo memo) {
	    this.memos.remove(memo);
	    memo.setPosition(null);
	}
	
	public void addMemoLog(MemoLog memoLog) {
	    this.memoLogs.add(memoLog);
	    memoLog.setPosition(this);
	}
	
	public void removeMemoLog(MemoLog memoLog) {
	    this.memoLogs.remove(memoLog);
	    memoLog.setPosition(null);
	}
	
	public void addMemoLabel(MemoLabel memoLabel) {
	    this.memoLabels.add(memoLabel);
	    memoLabel.setPosition(this);
	}
	
	public void removeMemoLabel(MemoLabel memoLable) {
	    this.memoLabels.remove(memoLable);
	    memoLable.setPosition(null);
	}
	
	public void addUser(User user) {
	    this.users.add(user);
	    user.setPosition(this);
	}
	
	public void removeUser(User user) {
	    this.users.remove(user);
	    user.setPosition(null);
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
		return "DepartmentUnit [id=" + id + ", code=" + code + ", department=" + department + ", createdAt=" + createdAt
				+ "]";
	}
	
}
