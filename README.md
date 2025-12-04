<h1>📧 Dynamic Gmail Notification Service</h1>

<p>
A robust <b>Java + Spring Boot based Email Notification Service</b> built as a 
<b>Dynamic Gmail Notification System</b>.  
The service exposes a single API to send emails where <b>all Gmail SMTP configurations
are loaded dynamically from PostgreSQL</b>, with <b>no hardcoded credentials</b> in the code.
</p>

<hr>

<h2>📸 Output Screenshots</h2>

<p>Replace these with your actual images (Postman / Swagger / Logs):</p>

<img src="https://your-repo-url/Output/Postman-Success.png" width="800" />
<br><br>

<img src="https://your-repo-url/Output/DB-Config.png" width="800" />
<br><br>

<img src="https://your-repo-url/Output/Console-Logs.png" width="800" />
<br><br>

<hr>

<h2>📄 Project Overview</h2>

<p>
This project is a <b>Dynamic Gmail Notification API</b> that:
</p>

<ul>
    <li>Exposes a single REST endpoint: <code>POST /api/sendNotification</code></li>
    <li>Accepts <code>tomail</code>, <code>subject</code>, and <code>body</code> in JSON format</li>
    <li>Loads all mail configuration (host, port, username, password, TLS, etc.) from <b>PostgreSQL</b></li>
    <li>Builds a <b>JavaMailSender</b> instance dynamically at runtime</li>
    <li>Sends email using Gmail SMTP with <b>no hardcoded credentials</b> in the application code</li>
</ul>

<hr>

<h2>📌 Project Objectives</h2>

<ul>
    <li>Expose a single, clean email notification API</li>
    <li>Validate request payload fields (email, subject, body)</li>
    <li>Fetch Gmail SMTP configuration dynamically from the database</li>
    <li>Build and send email using the dynamic configuration</li>
    <li>Return proper success and error responses as JSON</li>
    <li>Keep the service easily configurable without code changes</li>
</ul>

<hr>

<h2>🛠️ Tools & Technologies</h2>

<ul>
    <li><b>Language:</b> Java</li>
    <li><b>Framework:</b> Spring Boot</li>
    <li><b>Database:</b> PostgreSQL</li>
    <li><b>ORM:</b> Spring Data JPA (Hibernate)</li>
    <li><b>Mail:</b> Spring Boot Starter Mail (JavaMailSender)</li>
    <li><b>Validation:</b> Jakarta Validation (Bean Validation)</li>
    <li><b>Build Tool:</b> Maven</li>
    <li><b>API Testing:</b> Postman / cURL</li>
    <li><b>IDE:</b> IntelliJ IDEA / Eclipse / VS Code</li>
</ul>

<hr>

<h2>📂 Project Structure</h2>

<pre>
dynamic-notification-service/
│── src/
│   └── main/
│       ├── java/
│       │   └── com/example/notificationservice/
│       │       ├── controller/
│       │       │   └── NotificationController.java      # REST API controller
│       │       ├── dto/
│       │       │   ├── NotificationRequest.java         # Request payload DTO
│       │       │   └── NotificationResponse.java        # Response DTO
│       │       ├── entity/
│       │       │   └── MailConfig.java                  # Mail configuration entity
│       │       ├── exception/
│       │       │   └── GlobalExceptionHandler.java      # Validation & global error handling
│       │       ├── repository/
│       │       │   └── MailConfigRepository.java        # JPA repo for MailConfig
│       │       ├── service/
│       │       │   └── DynamicMailService.java          # Core email sending logic
│       │       └── NotificationServiceApplication.java  # Spring Boot main class
│       │
│       └── resources/
│           ├── application.properties                   # DB config only (no mail creds)
│           └── data.sql                                 # Optional: seed DB data
│
├── pom.xml
└── README.md
</pre>

<hr>

<h2>⚙️ Methodology</h2>

<ol>
  <li><b>Request Handling & Validation</b></li>
  <ul>
    <li>Client sends <code>POST /api/sendNotification</code> with JSON payload:</li>
    <pre>{
  "tomail": "recipient@gmail.com",
  "subject": "Email Subject",
  "body": "Email Body Content"
}</pre>
    <li><code>@RestController</code> receives the request in <code>NotificationController</code></li>
    <li><code>@Valid</code> and Bean Validation annotations ensure:
      <ul>
        <li><code>tomail</code> is a valid email</li>
        <li><code>subject</code> and <code>body</code> are not blank</li>
      </ul>
    </li>
    <li>Validation errors are handled by <code>GlobalExceptionHandler</code> and returned as JSON</li>
  </ul>

  <li><b>Dynamic Configuration Loading</b></li>
  <ul>
    <li>Mail configuration is stored in <b>PostgreSQL</b> table <code>mail_config</code></li>
    <li><code>MailConfigRepository</code> fetches the active row:
      <pre>findFirstByProviderNameAndActiveTrue("GMAIL")</pre>
    </li>
    <li>No SMTP details (host, port, username, password) are hardcoded in properties or code</li>
  </ul>

  <li><b>Email Composition & Sending</b></li>
  <ul>
    <li><code>DynamicMailService</code> builds a <code>JavaMailSenderImpl</code> instance at runtime</li>
    <li>SMTP properties like <code>mail.smtp.auth</code> and <code>mail.smtp.starttls.enable</code> 
        are set from the DB configuration</li>
    <li><code>MimeMessage</code> and <code>MimeMessageHelper</code> are used to compose:
      <ul>
        <li>From address (from DB)</li>
        <li>To address (<code>tomail</code> from request)</li>
        <li>Subject & Body (from request)</li>
      </ul>
    </li>
    <li>Mail is sent via Gmail SMTP using <code>sender.send(message)</code></li>
  </ul>

  <li><b>Error Handling & Responses</b></li>
  <ul>
    <li>Any failure (missing DB config, SMTP error, etc.) is caught in the controller</li>
    <li>Returns a <code>NotificationResponse</code> JSON with:
      <ul>
        <li><code>success: true/false</code></li>
        <li><code>message: "Email sent successfully" / error description</code></li>
      </ul>
    </li>
  </ul>
</ol>

<hr>

<h2>🗄 Database Design</h2>

<p>Core table for storing dynamic mail configuration:</p>

<pre>
CREATE TABLE mail_config (
    id              SERIAL PRIMARY KEY,
    provider_name   VARCHAR(50) NOT NULL,
    host            VARCHAR(255) NOT NULL,
    port            INT NOT NULL,
    username        VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    protocol        VARCHAR(20) NOT NULL,
    smtp_auth       BOOLEAN NOT NULL,
    starttls_enable BOOLEAN NOT NULL,
    from_address    VARCHAR(255) NOT NULL,
    active          BOOLEAN NOT NULL DEFAULT TRUE
);
</pre>

<p>Sample seed data (Gmail):</p>

<pre>
INSERT INTO mail_config (
  provider_name, host, port, username, password,
  protocol, smtp_auth, starttls_enable, from_address, active
) VALUES (
  'GMAIL',
  'smtp.gmail.com',
  587,
  'yourgmail@gmail.com',
  'your_app_password',
  'smtp',
  true,
  true,
  'yourgmail@gmail.com',
  true
);
</pre>

<hr>

<h2>🚀 How to Run</h2>

<ol>
  <li><b>Clone the Repository</b></li>
  <ul>
    <li><code>git clone https://github.com/YourUserName/dynamic-notification-service.git</code></li>
    <li><code>cd dynamic-notification-service</code></li>
  </ul>

  <li><b>Set Up PostgreSQL</b></li>
  <ul>
    <li>Create database, e.g. <code>notification_db</code></li>
    <li>Run the <code>CREATE TABLE</code> and <code>INSERT</code> statements shown above</li>
  </ul>

  <li><b>Configure Application Properties</b></li>
  <ul>
    <li>Update <code>src/main/resources/application.properties</code>:</li>
<pre>
spring.datasource.url=jdbc:postgresql://localhost:5432/notification_db
spring.datasource.username=postgres
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
</pre>
    <li>No Gmail config here – they come from DB only</li>
  </ul>

  <li><b>Build & Run the Application</b></li>
  <ul>
    <li><code>mvn clean install</code></li>
    <li><code>mvn spring-boot:run</code></li>
    <li>Application runs on <code>http://localhost:8080</code> by default</li>
  </ul>

  <li><b>Test the API</b></li>
  <ul>
    <li>Endpoint: <code>POST http://localhost:8080/api/sendNotification</code></li>
    <li>Body (JSON):</li>
<pre>
{
  "tomail": "recipient@gmail.com",
  "subject": "Test Dynamic Gmail Service",
  "body": "Hello, this email is sent using DB-driven Gmail configuration."
}
</pre>
    <li>Expected Response:</li>
<pre>
{
  "success": true,
  "message": "Email sent successfully"
}
</pre>
  </ul>
</ol>

<hr>

<h2>📬 Sample cURL Command</h2>

<pre>
curl -X POST http://localhost:8080/api/sendNotification \
  -H "Content-Type: application/json" \
  -d '{
    "tomail": "recipient@gmail.com",
    "subject": "Test Dynamic Email",
    "body": "Hello from Dynamic Gmail Notification Service!"
  }'
</pre>

<hr>

<h2>🙋‍♂️ Author</h2>

<p>
<b>Abhishek Khanai</b><br>
Java Full Stack / Backend Developer<br>
GitHub: 
<a href="https://github.com/Abhishekkhanai07">Abhishekkhanai07</a>
</p>

