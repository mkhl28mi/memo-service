package io.github.mkhl28mi.memo_service.domain.memo.entity;

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
import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.memo_employee.entity.MemoEmployee;
import io.github.mkhl28mi.memo_service.domain.memo_label.entity.MemoLabel;
import io.github.mkhl28mi.memo_service.domain.memo_log.entity.MemoLog;
import io.github.mkhl28mi.memo_service.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "memos")
@EntityListeners(AuditingEntityListener.class)
public class Memo {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "Content cannot be null")
	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
	
	@NotNull(message = "Status cannot be null")
    @Size(min = 1, max = 20, message = "Status must be between 1 and 20 characters")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private Status status;
	
	@NotNull(message = "Assignee cannot be null")
    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
	private User assignee;
	
	@NotNull(message = "Position cannot be null")
    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
	private DepartmentUnit position;
	
	@NotNull(message = "Department cannot be null")
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
	@Positive
	@Column(name = "sequence_number", nullable = false)
	private int sequenceNumber;
	
	@Positive
	@Column(name = "year", nullable = false)
	private int year;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoEmployee> memoEmployees = new ArrayList<>();
    
    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoLog> memoLogs = new ArrayList<>();
    
    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <MemoLabel> memoLabels = new ArrayList<>();
    
    public Memo() {
		super();
	}
    
	public Memo(String content, 
			Status status, 
			User assignee, 
			DepartmentUnit position, 
			Department department, 
			int sequenceNumber, 
			int year) {
		super();
		this.content = content;
		this.status = status;
		this.assignee = assignee;
		this.position = position;
		this.department = department;
		this.sequenceNumber = sequenceNumber;
		this.year = year;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
	
	public DepartmentUnit getPosition() {
		return position;
	}

	public void setPosition(DepartmentUnit position) {
		this.position = position;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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
	
	public void addMemoEmployee(MemoEmployee memoEmployee) {
	    this.memoEmployees.add(memoEmployee);
	    memoEmployee.setMemo(this);
	}
	
	public void removeMemoEmployee(MemoEmployee memoEmployee) {
	    this.memoEmployees.remove(memoEmployee);
	    memoEmployee.setMemo(null);
	}
	
	public void addMemoLog(MemoLog memoLog) {
	    this.memoLogs.add(memoLog);
	    memoLog.setMemo(this);
	}
	
	public void removeMemoLog(MemoLog memoLog) {
	    this.memoLogs.remove(memoLog);
	    memoLog.setMemo(null);
	}
	
	public void addMemoLabel(MemoLabel memoLabel) {
	    this.memoLabels.add(memoLabel);
	    memoLabel.setMemo(this);
	}
	
	public void removeMemoLabel(MemoLabel memoLable) {
	    this.memoLabels.remove(memoLable);
	    memoLable.setMemo(null);
	}
	
	public List<MemoEmployee> getMemoEmployees() {
		return Collections.unmodifiableList(memoEmployees);
	}
	
	public List<MemoLabel> getMemoLabels() {
		return Collections.unmodifiableList(memoLabels);
	}
	
	public List<MemoLog> getMemoLogs() {
		return Collections.unmodifiableList(memoLogs);
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
		Memo other = (Memo) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Memo [id=" + id + ", content=" + content + ", status=" + status + ", assignee=" + assignee
				+ ", position=" + position + ", department=" + department + ", sequenceNumber="
				+ sequenceNumber + ", year=" + year + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	public enum Status {
    	
    	IN_PROGRESS,
    	
    	DONE,
    	
    	DRAFT,
    	
    	ON_APPROVAL,
    	
    	APPROVED,
    	
    	REJECTED
    	
    }
}
