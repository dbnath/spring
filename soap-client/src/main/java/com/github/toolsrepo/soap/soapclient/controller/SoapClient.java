package com.github.toolsrepo.soap.soapclient.controller;

import com.github.toolsrepo.soap.soapclient.config.SoapConfig;
import com.github.toolsrepo.soap_server.students.GetStudentDetailsRequest;
import com.github.toolsrepo.soap_server.students.GetStudentDetailsResponse;
import com.github.toolsrepo.soap_server.students.StudentDetails;
import com.github.toolsrepo.soap_server.students.StudentPort;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SoapClient {


    private StudentPort studentPort;

    public SoapClient(StudentPort studentPort) {
        this.studentPort = studentPort;
    }

    @ApiOperation(value="Call soap service passing student Id", response = StudentDetails.class)
    @GetMapping(value = "/student/{studentId}/details")
    public ResponseEntity<StudentDetails> getStudentDetails(@PathVariable("studentId") String studentId) {

        GetStudentDetailsRequest getStudentDetailsRequest = new GetStudentDetailsRequest(){{
            setId(Integer.parseInt(studentId));
        }};

        GetStudentDetailsResponse studentDetailsResponse = this.studentPort.getStudentDetails(getStudentDetailsRequest);

        return new ResponseEntity<>(studentDetailsResponse.getStudentDetails(), HttpStatus.OK);

    }

}
