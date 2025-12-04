<h1>📧 Dynamic Gmail Notification Service</h1>

<p>
A powerful <b>Java & Spring Boot based Email Notification System</b> where all
<b>Gmail SMTP configurations are loaded dynamically from PostgreSQL</b> —
no hardcoded credentials in the code.  
This project exposes a single API to send professional email notifications.
</p>

<hr>

<h2>📸 Output Screenshots</h2>

<p>Replace these with your actual screenshots:</p>

<img src="https://your-repo-url/Output/Postman-Success.png" width="800" />
<br><br>

<img src="https://your-repo-url/Output/DB-Config.png" width="800" />
<br><br>

<img src="https://your-repo-url/Output/Eclipse-Console.png" width="800" />
<br><br>

<hr>

<h2>📄 Project Overview</h2>

<p>
A dynamic and secure email notification service built using Spring Boot.
This project follows industry-level backend architecture and ensures full flexibility
by loading Gmail SMTP configuration from the database at runtime.
</p>

<ul>
    <li>REST API for sending emails</li>
    <li>Dynamic SMTP configuration (host, port, username, password, TLS, etc.)</li>
    <li>No hard-coded secrets inside code or properties</li>
    <li>Uses PostgreSQL as central config storage</li>
    <li>JavaMailSender built dynamically for every request</li>
    <li>Postman-ready API endpoint</li>
</ul>

<hr>

<h2>📌 Project Objectives</h2>

<ul>
    <li>Expose a clean notification API (<code>POST /api/sendNotification</code>)</li>
    <li>Validate request fields (<code>tomail</code>, <code>subject</code>, <code>body</code>)</li>
    <li>Fetch SMTP configuration dynamically from PostgreSQL</li>
    <li>Build mail sender instance programmatically</li>
    <li>Send email to any target Gmail account</li>
    <li>Provide success/error JSON responses</li>
</ul>

<hr>

<h2>🛠️ Tools & Technologies Used</h2>

<ul>
    <li><b>Backend Framework:</b> Spring Boot</li>
    <li><b>Language:</b> Java</li>
    <li><b>Database:</b> PostgreSQL</li>
    <li><b>ORM:</b> Spring Data JPA</li>
    <li><b>Email Library:</b> Spring Mail (JavaMailSender)</li>
    <li><b>Validation:</b> Jakarta Validation</li>
    <li><b>Testing Tool:</b> Postman</li>
    <li><b>IDE:</b> Eclipse</li>
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
│       │       │   └── NotificationController.java
│       │       ├── dto/
│       │       │   ├── NotificationRequest.java
│       │       │   └── NotificationResponse.java
│       │       ├── entity/
│       │       │   └── MailConfig.java
│       │       ├── exception/
│       │       │   └── GlobalExceptionHandler.java
│       │       ├── repository/
│       │       │   └── MailConfigRepository.java
│       │       ├── service/
│       │       │   └── DynamicMailService.java
│       │       └── NotificationServiceApplication.java
│       │
│       └── resources/
│           ├── application.properties
│           └── data.sql (optional)
│
├── pom.xml
└── README.md
</pre>

<hr>

<h2>⚙️ Methodology</h2>

<ol>
<li><b>1. Request Validation</b></li>
<ul>
<li>User sends JSON to <code>/api/sendNotification</code></li>
<li>DTO validates:
<ul>
<li>Valid email format</li>
<li>Subject and body not empty</li>
</ul>
</li>
</ul>

<li><b>2. Load SMTP Config From DB</b></li>
<ul>
<li>All Gmail settings stored in <code>mail_config</code> table</li>
<li>No credentials in code or properties</li>
<li>Repository fetches active configuration</li>
</ul>

<li><b>3. Dynamic Mail Sender Creation</b></li>
<ul>
<li><code>JavaMailSenderImpl</code> is created on every request</li>
<li>Properties like <code>smtp_auth</code>, <code>starttls</code> are set from DB</li>
</ul>

<li><b>4. Build Email</b></li>
<ul>
<li>Set From, To, Subject, Body</li>
<li>Supports text emails</li>
</ul>

<li><b>5. Send Email</b></li>
<ul>
<li>Uses Gmail SMTP at runtime</li>
<li>Returns success or failure JSON response</li>
</ul>
</ol>

<hr>

<h2>🗄 Database Structure</h2>

<pre>
CREATE TABLE mail_config (
    id SERIAL PRIMARY KEY,
    provider_name   VARCHAR(50),
    host            VARCHAR(255),
    port            INT,
    username        VARCHAR(255),
    password        VARCHAR(255),
    protocol        VARCHAR(20),
    smtp_auth       BOOLEAN,
    starttls_enable BOOLEAN,
    from_address    VARCHAR(255),
    active BOOLEAN
);
</pre>

<h3>Sample Insert</h3>

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
<pre>git clone https://github.com/YourName/dynamic-notification-service.git</pre>

<li><b>Configure PostgreSQL</b></li>
<ul>
<li>Create DB: <code>notification_db</code></li>
<li>Run table + insert queries</li>
</ul>

<li><b>Update application.properties</b></li>
<pre>
spring.datasource.url=jdbc:postgresql://localhost:5432/notification_db
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
</pre>

<li><b>Run Application in Eclipse</b></li>
<ul><li>Right-click → Run As → Spring Boot App</li></ul>

<li><b>Test in Postman</b></li>
<pre>
POST http://localhost:8080/api/sendNotification
Content-Type: application/json
{
  "tomail": "recipient@gmail.com",
  "subject": "Test Dynamic Email",
  "body": "This email is sent using dynamic DB configuration."
}
</pre>

</ol>

<hr>

<h2>🧪 Sample Response</h2>

<pre>
{
  "success": true,
  "message": "Email sent successfully"
}
</pre>

<hr>

<h2>🙋‍♂️ Author</h2>

<p>
<b>Abhishek Khanai</b><br>
Java Full Stack Developer<br>
GitHub: <a href="https://github.com/Abhishekkhanai07">Abhishekkhanai07</a>
</p>
