package edu.srmist.outpass_workflow.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.ProcessInstanceEvent;

@RestController
@RequestMapping("/outpass")
public class OutpassController {

    @Autowired
    private CamundaClient camundaClient;

    @PostMapping
    public Map<String, Object> applyOutpass(@RequestBody OutpassRequest request) {
        ProcessInstanceEvent instance = camundaClient.newCreateInstanceCommand()
                .bpmnProcessId("hostel-outpass")
                .latestVersion()
                .variables(request)
                .send()
                .join();

        return Map.of("processInstanceKey", instance.getProcessInstanceKey());
    }
}

class OutpassRequest {
    public String studentName;
    public String regNo;
    public String hostelBlock;
    public String outDate;
    public String returnDate;
    public String parentPhone;
    public String purpose;
}
