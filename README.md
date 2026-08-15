# Workflow Automation Engineering
BPMN Exercises - RA2411026011244


## Scenario 1: Employee Leave Approval

### Description
This process handles automated balance verification and multi-tier management sign-off for employee leave requests.

### BPMN Process Flow Explanations
1. **Start Event**: Triggers when an employee submits a leave request payload containing `employeeId` and `requestedDays`.
2. **Check Leave Balance (Service Task)**: An automated background worker queries the HR microservice to verify available days.
3. **Balance Sufficient? (Exclusive Gateway)**: Evaluates the balance check variable.
   * **Path [No]**: Routes to *Send Insufficient-Balance Notification* and triggers the End Event.
   * **Path [Yes]**: Routes to the manager's active task queue.
4. **Approve Leave Request (User Task)**: Assigned to the manager group for human review and sign-off.
5. **Approved? (Exclusive Gateway)**: Evaluates the manager's form output.
   * **Path [No]**: Routes to *Send Rejection Notification*.
   * **Path [Yes]**: Routes to *Update Leave Balance* followed by *Send Approval Notification*.
6. **End Events**: Terminate cleanly based on the final absolute resolution state.

### Camunda 8 FEEL Gateway Routing Expressions
* **Sufficient Balance (Yes)**: `balanceSufficient = true`
* **Insufficient Balance (No)**: `balanceSufficient = false`
* **Manager Approved (Yes)**: `managerApproval = true`
* **Manager Rejected (No)**: `managerApproval = false`

---

## Scenario 2: Online Purchase Order Processing

### Description
An e-commerce order fulfillment pipeline managing inventory checks, payment gateway transactions, and physical logistics tracking.

### BPMN Process Flow Explanations
1. **Start Event**: Triggered when a customer submits a checkout order.
2. **Check Product Availability (Service Task)**: Automatically calls inventory validation systems.
3. **Available? (Exclusive Gateway)**: Evaluates stock status.
   * **Path [No]**: Diverges to *Notify Out of Stock* and aborts.
   * **Path [Yes]**: Continues directly to payment systems.
4. **Process Payment (Service Task)**: Interfaces with the external banking/payment gateway API.
5. **Payment Successful? (Exclusive Gateway)**: Evaluates the payment gateway API transaction result.
   * **Path [No]**: Diverges to *Notify Payment Failure* and aborts.
   * **Path [Yes]**: Proceeds to confirmation steps.
6. **Confirm Order & Prepare Product (Sequential Tasks)**: Order status flips to confirmed, and a warehouse picker receives a manual fulfillment instruction task.
7. **Ship Order & Send Confirmation (Service Tasks)**: Logistical handover triggers tracking code generation, emailing the final shipping receipt to the end user.
8. **End Events**: Dedicated end nodes signify successful order completion versus distinct failure branch endpoints.

### Camunda 8 FEEL Gateway Routing Expressions
* **Product Available (Yes)**: `available = true`
* **Product Out of Stock (No)**: `available = false`
* **Payment Successful (Yes)**: `paymentSuccess = true`
* **Payment Failed (No)**: `paymentSuccess = false`

---

## Scenario 3: IT Service Request

### Description
An internal IT service management (ITSM) ticketing workflow dividing incoming corporate support queries based on complexity and resolution capabilities.

### BPMN Process Flow Explanations
1. **Start Event**: Initiated when a corporate employee fills out an issue ticket.
2. **Submit IT Support Request (User Task)**: The user provides descriptive issue data.
3. **Register Request (Service Task)**: Automatically captures log details and generates a unique tracking ID ticket.
4. **Check Severity (Service Task)**: Evaluates ticket categories or impact dimensions to assign triage flags.
5. **Severity Level? (Exclusive Gateway)**: Evaluates triage flags.
   * **Path [Low]**: Directly routes and alerts a standard *Support Technician* workspace queue.
   * **Path [High]**: Diverges directly to the *Senior Technician* high-priority escalation queue.
6. **Investigate Problem (User Task)**: Converges back into a single operational point where the assigned resource performs diagnostic assessments.
7. **Can Be Resolved Internally? (Exclusive Gateway)**:
   * **Path [Yes]**: The technician applies the internal remediation fix.
   * **Path [No]**: Bypasses internal tracks to assign a task out to the *External Service Provider* contract SLA window.
8. **Update Status & Notify (Service Tasks)**: Converges back to synchronize final logging state updates to the database and alerts the employee.
9. **End Event**: Reaches final closed-ticket resolution state.

### Camunda 8 FEEL Gateway Routing Expressions
* **Low Severity Path**: `severity = "low"`
* **High Severity Path**: `severity = "high"`
* **Can Be Resolved Internally (Yes)**: `resolvedInternally = true`
* **Cannot Be Resolved Internally (No)**: `resolvedInternally = false`

---


