package io.github.mkhl28mi.memo_service.domain.memo.comment.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "memo_comments")
@EntityListeners(AuditingEntityListener.class)
public class MemoComment {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
    @ManyToOne
    @JoinColumn(name = "memo_id", nullable = false)
	private Memo memo;
    
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
	private UserAssignment createdBy;
    
	@NotEmpty(message = "Content cannot be empty")
	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
	
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @PastOrPresent
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

	public MemoComment() {
		super();
	}

	public MemoComment(Memo memo, UserAssignment createdBy, String content) {
		super();
		this.memo = memo;
		this.createdBy = createdBy;
		this.content = content;
	}

	public Memo getMemo() {
		return memo;
	}

	public void setMemo(Memo memo) {
		this.memo = memo;
	}

	public UserAssignment getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserAssignment createdBy) {
		this.createdBy = createdBy;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
		MemoComment other = (MemoComment) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "MemoComment [id=" + id + ", memo=" + memo + ", createdBy=" + createdBy + ", content=" + content
				+ ", createdAt=" + createdAt + "]";
	}
    
}
