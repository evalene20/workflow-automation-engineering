## Workflow Automation Engineering
BPMN Exercises - RA2411026011244

## Scenario 1: Employee Leave Approval Process

### Description
This process handles automated balance verification and multi-tier management sign-off for employee leave requests.

### BPMN Process Flow Explanations
1. **Start Event**: Triggers when an employee submits a leave request payload containing `employeeId` and `requestedDays`.
2. **Check Leave Balance (Service Task)**: An automated task queries the HR database to fetch the remaining balance.
3. **Balance Sufficient? (Exclusive Gateway)**: Evaluates the system data.
   * **Path [No]**: Routes to *Send Insufficient-Balance Notification* and triggers the End Event.
   * **Path [Yes]**: Routes to the manager's queue.
4. **Approve Leave Request (User Task)**: Assigned to the manager group for an active human decision.
5. **Approved? (Exclusive Gateway)**: Evaluates the manager's input form variable `managerApproval`.
   * **Path [No]**: Routes to *Send Rejection Notification*.
   * **Path [Yes]**: Routes to *Update Leave Balance* (Service Task) followed by *Send Approval Notification*.
6. **End Events**: Terminate cleanly based on the final resolution (Approved, Rejected, or Insufficient Balance).

### Gateway Routing Expressions (FEEL / Camunda 7 JUEL)
* **Sufficient Balance**: `${leaveBalance >= requestedDays}` or `=${balanceSufficient == true}`
* **Insufficient Balance**: `${leaveBalance < requestedDays}` or `=${balanceSufficient == false}`
* **Manager Approved**: `=${managerApproval == true}`
* **Manager Rejected**: `=${managerApproval == false}`

---

## Scenario 2: Online Purchase Order Processing

### Description
An e-commerce order fulfillment pipeline managing inventory checks, payment gateway transactions, and physical logistics tracking.

### BPMN Process Flow Explanations
1. **Start Event**: Triggered when a customer submits a checkout order.
2. **Check Product Availability (Service Task)**: Calls inventory services.
3. **Available? (Exclusive Gateway)**: Evaluates stock status.
   * **Path [No]**: Diverges to *Notify Out of Stock* and aborts.
   * **Path [Yes]**: Continues to payment.
4. **Process Payment (Service Task)**: Interfaces with the external payment gateway.
5. **Payment Successful? (Exclusive Gateway)**: Evaluates the payment gateway API response string `paymentStatus`.
   * **Path [No]**: Diverges to *Notify Payment Failure* and aborts.
   * **Path [Yes]**: Proceeds to confirmation.
6. **Confirm Order & Prepare Product (Sequential Tasks)**: Order status flips to confirmed, and a warehouse picker receives a manual fulfillment instruction task.
7. **Ship Order & Send Confirmation (Service Tasks)**: Logistical handover triggers a physical tracking code generation, emailing the final shipping receipt to the end user.
8. **End Events**: Dedicated end nodes signify successful order completion versus failure branch endpoints.

---

## Scenario 3: IT Service Request Handling

### Description
An internal IT service management (ITSM) ticketing workflow dividing incoming corporate support queries based on complexity and resolution capabilities.

### BPMN Process Flow Explanations
1. **Start Event**: Initiated when a corporate employee fills out an issue ticket.
2. **Submit & Register Request (User/Service Tasks)**: Captures log details and generates a unique tracking ID ticket.
3. **Check Severity (Business Rule / User Task)**: Evaluates ticket categories or impact dimensions to assign triage flags.
4. **Severity Level? (Exclusive Gateway)**:
   * **Path [Low]**: Directly routes and alerts a standard *Support Technician* workspace queue.
   * **Path [High]**: Diverges directly to the *Senior Technician* high-priority escalation queue.
5. **Investigate Problem (User Task)**: The assigned resource performs diagnostic assessments.
6. **Can Be Resolved Internally? (Exclusive Gateway)**:
   * **Path [Yes]**: The technician applies the internal remediation fix.
   * **Path [No]**: Bypasses internal resource tracks to assign a task out to the *External Service Provider* contract SLA window.
7. **Update Status & Notify (Service Tasks)**: Synchronizes final logging state updates back to the core enterprise service desk DB and alerts the employee.
8. **End Event**: Reaches final closed-ticket resolution state.

---

## Deployment & Verification Notes
* All models use standard **BPMN 2.0 notation elements** compatible with Camunda Engine architectures.
* Gateways use distinct, unique **Default Flow** fallbacks to prevent runtime exceptions or stuck tokens.
* Variable naming conventions use standard strict camelCase syntax throughout (e.g., `isAvailable`, `ticketSeverity`).
