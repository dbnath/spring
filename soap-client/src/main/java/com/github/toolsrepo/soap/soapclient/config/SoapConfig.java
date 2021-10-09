package com.github.toolsrepo.soap.soapclient.config;

import com.github.toolsrepo.soap_server.students.StudentPort;
import com.github.toolsrepo.soap_server.students.StudentPortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.BindingProvider;

@Configuration
@Slf4j
public class SoapConfig {

    public static final String SOAP_WEBSERVICE_PATH = "/ws";

    @Bean
    public StudentPort configureStudentServiceEndPoint(@Value("${webserviceEndPoint}") String webServiceEndPoint) {
        StudentPortService studentPortService = new StudentPortService();
        StudentPort studentPortSoap11 = studentPortService.getStudentPortSoap11();

        BindingProvider bindingProvider = (BindingProvider) studentPortSoap11;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, webServiceEndPoint.concat(SOAP_WEBSERVICE_PATH));

        log.info("Configured webservice with :{}", studentPortSoap11);

        return studentPortSoap11;
    }
}
