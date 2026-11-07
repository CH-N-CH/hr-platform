package com.hr.employee_service.ws;

import com.hr.employee_service.model.Employee;
import com.hr.employee_service.model.EmployeeStatus;
import com.hr.employee_service.ws.generated.EmployeeInfo;
import com.hr.employee_service.ws.generated.EmployeeStatusType;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

@Component
public class EmployeeMapper {

    public EmployeeInfo toInfo(Employee e) {
        EmployeeInfo info = new EmployeeInfo();
        info.setId(e.getId());
        info.setMatricule(e.getMatricule());
        info.setFirstName(e.getFirstName());
        info.setLastName(e.getLastName());
        info.setEmail(e.getEmail());
        info.setPhone(e.getPhone());
        info.setHireDate(toXmlDate(e.getHireDate()));
        info.setStatus(EmployeeStatusType.valueOf(e.getStatus().name()));
        info.setPositionId(e.getPositionId());
        info.setDepartmentId(e.getDepartmentId());
        return info;
    }

    public Employee toEntity(EmployeeInfo info) {
        return Employee.builder()
                .id(info.getId())
                .matricule(info.getMatricule())
                .firstName(info.getFirstName())
                .lastName(info.getLastName())
                .email(info.getEmail())
                .phone(info.getPhone())
                .hireDate(toLocalDate(info.getHireDate()))
                .status(EmployeeStatus.valueOf(info.getStatus().name()))
                .positionId(info.getPositionId())
                .departmentId(info.getDepartmentId())
                .build();
    }

    private XMLGregorianCalendar toXmlDate(LocalDate date) {
        if (date == null) return null;
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
        } catch (DatatypeConfigurationException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private LocalDate toLocalDate(XMLGregorianCalendar cal) {
        if (cal == null) return null;
        return LocalDate.of(cal.getYear(), cal.getMonth(), cal.getDay());
    }
}