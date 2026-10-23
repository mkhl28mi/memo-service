package io.github.mkhl28mi.memo_service.domain.admin.employee.dto.xml_parsing;

import java.util.Objects;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PositionDto {
	
	@XmlElement(name = "NAME")
	private String name;
	
	@XmlElement(name = "TARGET_NAME")
	private String targetName;
	
	@XmlElement(name = "PLACEMENT_ORDER")
	private String placementOrder;

	public String getName() {
		return name;
	}

	public String getTargetName() {
		return targetName;
	}

	public String getPlacementOrder() {
		return placementOrder;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, targetName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionDto other = (PositionDto) obj;
		return Objects.equals(name, other.name) && Objects.equals(targetName, other.targetName);
	}

	@Override
	public String toString() {
		return "PositionDto [name=" + name + ", targetName=" + targetName + ", placementOrder=" + placementOrder + "]";
	}
	
}
