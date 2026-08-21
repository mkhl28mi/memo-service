package io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import io.github.mkhl28mi.memo_service.domain.admin.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.admin.position.entity.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "employee_assignments")
public class EmployeeAssignment {
	
	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	private UUID id;
	
	@NotNull(message = "Position cannot be null")
    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
	private Position position;
	
	@NotNull(message = "Employee cannot be null")
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@NotNull(message = "Start date cannot be null")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    private LocalDateTime endDate;

	public EmployeeAssignment() {
		super();
	}
	
	public EmployeeAssignment(Position position, Employee employee, LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.position = position;
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public Optional<LocalDateTime> getEndDate() {
		return Optional.ofNullable(endDate);
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	public UUID getId() {
		return id;
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
		EmployeeAssignment other = (EmployeeAssignment) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "EmployeeAssignment [id=" + id + ", position=" + position + ", employee=" + employee + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}

}
