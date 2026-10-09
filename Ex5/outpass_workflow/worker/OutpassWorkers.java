package edu.srmist.outpass_workflow.worker;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
public class OutpassWorkers {

    private static final Logger log = LoggerFactory.getLogger(OutpassWorkers.class);

    @JobWorker(type = "check-outpass")
    public Map<String, Object> checkOutpass(
            @Variable String outDate, 
            @Variable String returnDate) {
        
        LocalDate start = LocalDate.parse(outDate);
        LocalDate end = LocalDate.parse(returnDate);
        
        long nights = ChronoUnit.DAYS.between(start, end);
        
        boolean valid = !end.isBefore(start);
        boolean autoApproved = valid && (nights == 0);

        return Map.of(
            "nights", nights,
            "valid", valid,
            "approved", autoApproved
        );
    }

    @JobWorker(type = "issue-outpass")
    public Map<String, Object> issueOutpass(
            @Variable String studentName,
            @Variable String regNo,
            @Variable String outDate,
            @Variable String parentPhone,
            @Variable Boolean approved,
            @Variable String wardenRemarks) { // <-- Added the missing closing parenthesis here

        String status = Boolean.TRUE.equals(approved) ? "ISSUED" : "REJECTED";
        String passNumber = "NOT_APPLICABLE";

        if ("ISSUED".equals(status)) {
            passNumber = String.format("OP-%s-%s", regNo, outDate);
            log.info("[SMS to {}] Out-pass ISSUED for {}. Pass No: {}", parentPhone, studentName, passNumber);
        } else {
            log.info("[SMS to {}] Out-pass REJECTED for {}. Reason/Remarks: {}", parentPhone, studentName, wardenRemarks);
        }

        return Map.of(
            "passStatus", status,
            "passNumber", passNumber
        );
    }
}
