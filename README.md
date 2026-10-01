# Workflow Automation Engineering
> **BPMN Exercises Portfolio** — `RA2411026011244`

This repository documents the implementation of various Business Process Model and Notation (BPMN) workflows designed and deployed using the **Camunda 8** ecosystem, utilizing **FEEL expressions** for dynamic gateway routing.

---

## 📅 Weekly Progress Summary

### 🔹 Week 1
Focuses on designing fundamental BPMN workflows using Camunda 8 FEEL routing expressions:
*   **Employee Leave Approval:** Validates leave balances (`balanceSufficient`) and routes requests to managers for final sign-off (`managerApproval`).
*   **Online Purchase Order Processing:** Manages automated inventory checks (`available`), payment gateway API transactions (`paymentSuccess`), manual fulfillment, and shipping logistics.
*   **IT Service Request:** Triages support tickets by `severity` (`"low"` / `"high"`), routes them to standard or senior technicians, and handles external vendor escalations (`resolvedInternally`).

### 🔹 Week 2
Focuses on architectural design for multi-tier industry execution tracks:
*   **Hotel Room Reservation:** Models the automated pipeline for checking room availability, processing guest bookings, and managing check-in or cancellation paths.
*   **Loan Application Processing:** Designs multi-tier financial evaluation tracks, integrating background credit scoring checks, and establishing risk-based approval routing.
*   **Job Recruitment Process:** Automates the HR talent pipeline, spanning resume screening, multi-stage interview scheduling, and final candidate onboarding tracks.

### 🔹 Week 3
Focuses on enhancing the Week 1 *Employee Leave Approval* workflow by introducing dynamic leave type classification:
*   **Leave Type Categorisation:** Routing is handled based on specific leave categories (such as `Annual`, `Sick`, or `Paid` leave). 
*   **Dynamic Validation:** Allows the engine to apply customized approval rules, balance checks, and automated validation tracks tailored to each leave type.

### 🔹 Week 4
Focuses on building, deploying, and executing a complete **Event Registration** process in the cloud:
*   **Form Design:** Created a custom Camunda Form linked directly to a User Task via its ID.
*   **Data Fields:** Configured key fields including `Name`, `Date`, and `Reason` using unique variables.
*   **Execution Lifecycle:** Deployed the process to Camunda 8, claimed and completed the task inside **Tasklist**, and audited the captured variables inside **Operate**.
