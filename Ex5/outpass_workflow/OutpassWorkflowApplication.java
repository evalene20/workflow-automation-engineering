package edu.srmist.outpass_workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.client.annotation.Deployment;

@SpringBootApplication
@Deployment(resources = {
    "classpath:bpmn/hostel-outpass.bpmn", 
    "classpath:bpmn/warden-approval.form"
})
public class OutpassWorkflowApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutpassWorkflowApplication.class, args);
    }
}
