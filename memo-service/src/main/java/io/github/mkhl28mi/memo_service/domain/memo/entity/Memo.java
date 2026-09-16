package io.github.mkhl28mi.memo_service.domain.memo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.admin.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;
import io.github.mkhl28mi.memo_service.domain.memo.comment.entity.MemoComment;
import io.github.mkhl28mi.memo_service.domain.memo.employee.entity.MemoEmployee;
import io.github.mkhl28mi.memo_service.domain.memo.label.entity.MemoLabel;
import io.github.mkhl28mi.memo_service.domain.memo.log.entity.MemoLog;
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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

@Entity
@Table(
		name = "memos", 
		uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_memos_sequence_number_department_id_creation_cyear",
                columnNames = {"sequence_number", "department_id", "creation_cyear"}
            )
        }
)
@EntityListeners(AuditingEntityListener.class)
public class Memo {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotEmpty(message = "Content cannot be empty")
	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
	
	@NotNull(message = "Status cannot be null")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private Status status;
	
    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
	private UserAssignment assignee;
	
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
	@Positive
	@Column(name = "sequence_number", nullable = false)
	private int sequenceNumber;
	
	@Positive
	@Column(name = "creation_cyear", nullable = false)
	private int creationYear;
	
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
    
    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List <MemoEmployee> memoEmployees = new ArrayList<>();
    
    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List <MemoLog> memoLogs = new ArrayList<>();
    
    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List <MemoLabel> memoLabels = new ArrayList<>();
    
    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List <MemoComment> memoComments = new ArrayList<>();
    
    public Memo() {
		super();
	}
    
	public Memo(String content, 
			Status status, 
			UserAssignment assignee, 
			Department department, 
			int sequenceNumber, 
			int creationYear) {
		super();
		this.content = content;
		this.status = status;
		this.assignee = assignee;
		this.department = department;
		this.sequenceNumber = sequenceNumber;
		this.creationYear = creationYear;
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

	public UserAssignment getAssignee() {
		return assignee;
	}

	public void setAssignee(UserAssignment assignee) {
		this.assignee = assignee;
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

	public int getCreationYear() {
		return creationYear;
	}

	public void setCreationYear(int creationYear) {
		this.creationYear = creationYear;
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
	
	public void addMemoComment(MemoComment memoComment) {
	    this.memoComments.add(memoComment);
	    memoComment.setMemo(this);
	}
	
	public void removeMemoComment(MemoComment memoComment) {
	    this.memoComments.remove(memoComment);
	    memoComment.setMemo(null);
	}
	
	public List<MemoEmployee> getMemoEmployees() {
		return Collections.unmodifiableList(this.memoEmployees);
	}
	
	public List<MemoLog> getMemoLogs() {
		return Collections.unmodifiableList(this.memoLogs);
	}
	
	public List<MemoLabel> getMemoLabels() {
		return Collections.unmodifiableList(this.memoLabels);
	}
	
	public List<MemoComment> getMemoComments() {
		return Collections.unmodifiableList(this.memoComments);
	}
	
	public void initializeMemoEmployees() {
		Hibernate.initialize(this.memoEmployees);
	}
	
	public void initializeMemoLabels() {
		Hibernate.initialize(this.memoLabels);
	}
	
	public void initializeMemoComments() {
		Hibernate.initialize(this.memoComments);
	}
	
	public void initializeMemoLogs() {
		Hibernate.initialize(this.memoLogs);
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
				+ ", department=" + department + ", sequenceNumber=" + sequenceNumber + ", creationYear=" + creationYear
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	public enum Status {
		
    	ON_APPROVAL,
    	
    	REVISE_MEMO,
    	
    	APPROVED,
    	
    	REJECTED
    	
    }
	
}
