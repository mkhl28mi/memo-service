package io.github.mkhl28mi.memo_service.domain.admin.employee.dto.xml_parsing;

import java.util.List;
import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeDto {
	
	@XmlElement(name = "FULL_NAME")
	private String fullName;
	
	@XmlElement(name = "TARGET_FULL_NAME")
	private String targetFullName;
	
	@XmlElement(name = "POSITION")
	private List<PositionDto> positions;

	public String getFullName() {
		return fullName;
	}

	public String getTargetFullName() {
		return targetFullName;
	}

	public List<PositionDto> getPositions() {
		return positions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fullName, targetFullName);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeDto other = (EmployeeDto) obj;
		return Objects.equals(fullName, other.fullName) && Objects.equals(targetFullName, other.targetFullName);
	}
	
	@Override
	public String toString() {
		return "EmployeeDto [fullName=" + fullName + ", targetFullName=" + targetFullName + "]";
	}
	
}
