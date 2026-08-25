package io.github.mkhl28mi.memo_service.domain.admin.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;
import io.github.mkhl28mi.memo_service.domain.admin.user.role.entity.Role;
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
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "Username cannot be null")
	@Size(min = 1, max = 25, message = "Username must be between 1 and 25 characters")
	@Column(name = "username", unique = true, nullable = false, length = 25)
	private String username;
	
	@NotNull(message = "Password cannot be null")
	@Size(min = 8, max = 60, message = "Username must be between 8 and 60 characters")
	@Column(name= "password", nullable = false, length = 60)
	private String password;
	
	@NotNull(message = "Full name cannot be null")
	@Size(min = 1, max = 50, message = "Full name must be between 1 and 50 characters")
	@Column(name = "full_name", nullable = false, length = 50)
	private String fullName;
	
	@NotNull(message = "Cell cannot be null")
	@Size(min = 1, max = 20, message = "Cell must be between 1 and 20 characters")
	@Column(name = "cell", nullable = false, length = 20)
	private String cell;
	
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
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List <UserAssignment> userAssignments = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

	public User() {
		super();
	}
	
	public User(String username, String password, String fullName, String cell, boolean isEnabled) {
		super();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.cell = cell;
		this.enabled = isEnabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
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

	public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }
	
    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
    
	public void addUserAssignment(UserAssignment userAssignment) {
	    this.userAssignments.add(userAssignment);
	    userAssignment.setUser(this);
	}
	
	public List<UserAssignment> getUserAssignments() {
		return Collections.unmodifiableList(userAssignments);
	}
	
	public Set<Role> getRoles() {
		return Collections.unmodifiableSet(roles);
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", fullName=" + fullName + ", cell=" + cell + ", enabled="
				+ enabled + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
