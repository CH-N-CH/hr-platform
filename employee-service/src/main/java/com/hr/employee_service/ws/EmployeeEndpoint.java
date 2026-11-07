package com.hr.employee_service.ws;

import com.hr.employee_service.model.Employee;
import com.hr.employee_service.service.EmployeeService;
import com.hr.employee_service.ws.generated.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class EmployeeEndpoint {

    private static final String NAMESPACE = "http://hr.com/employee-service/ws";

    private final EmployeeService service;
    private final EmployeeMapper mapper;

    @PayloadRoot(namespace = NAMESPACE, localPart = "getEmployeeByIdRequest")
    @ResponsePayload
    public GetEmployeeByIdResponse getById(@RequestPayload GetEmployeeByIdRequest request) {
        GetEmployeeByIdResponse response = new GetEmployeeByIdResponse();
        response.setEmployee(mapper.toInfo(service.findById(request.getId())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getAllEmployeesRequest")
    @ResponsePayload
    public GetAllEmployeesResponse getAll(@RequestPayload GetAllEmployeesRequest request) {
        GetAllEmployeesResponse response = new GetAllEmployeesResponse();
        service.findAll().forEach(e -> response.getEmployee().add(mapper.toInfo(e)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createEmployeeRequest")
    @ResponsePayload
    public CreateEmployeeResponse create(@RequestPayload CreateEmployeeRequest request) {
        Employee saved = service.create(mapper.toEntity(request.getEmployee()));
        CreateEmployeeResponse response = new CreateEmployeeResponse();
        response.setEmployee(mapper.toInfo(saved));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "updateEmployeeRequest")
    @ResponsePayload
    public UpdateEmployeeResponse update(@RequestPayload UpdateEmployeeRequest request) {
        EmployeeInfo info = request.getEmployee();
        Employee saved = service.update(info.getId(), mapper.toEntity(info));
        UpdateEmployeeResponse response = new UpdateEmployeeResponse();
        response.setEmployee(mapper.toInfo(saved));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteEmployeeRequest")
    @ResponsePayload
    public DeleteEmployeeResponse delete(@RequestPayload DeleteEmployeeRequest request) {
        service.delete(request.getId());
        DeleteEmployeeResponse response = new DeleteEmployeeResponse();
        response.setSuccess(true);
        response.setMessage("Employé supprimé");
        return response;
    }
}