package io.github.mkhl28mi.memo_service.domain.admin.employee.dto.xml_parsing;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EMPLOYEES")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeListDto {
	
	@XmlElement(name = "EMPLOYEE")
	private List<EmployeeDto> employees;

	public List<EmployeeDto> getEmployees() {
		return employees;
	}
	
}
