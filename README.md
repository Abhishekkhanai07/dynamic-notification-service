<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dynamic Gmail Notification Service - Spring Boot + PostgreSQL</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 20px;
            color: #222;
        }
        h1, h2, h3 {
            color: #1f2933;
        }
        code {
            background: #f4f4f4;
            padding: 2px 4px;
            border-radius: 3px;
            font-family: Consolas, monospace;
        }
        pre {
            background: #f4f4f4;
            padding: 10px;
            border-radius: 5px;
            overflow-x: auto;
        }
        ul {
            margin-left: 20px;
        }
        .tag {
            display: inline-block;
            background: #e5f0ff;
            color: #1f4fd1;
            padding: 3px 8px;
            border-radius: 12px;
            font-size: 12px;
            margin-right: 5px;
        }
        hr {
            margin: 20px 0;
        }
    </style>
</head>
<body>

    <h1>Dynamic Gmail Notification Service</h1>

    <p>
        <span class="tag">Java</span>
        <span class="tag">Spring Boot</span>
        <span class="tag">PostgreSQL</span>
        <span class="tag">REST API</span>
        <span class="tag">Email Notification</span>
    </p>

    <p>
        This project is a <strong>Dynamic Gmail Notification Service</strong> built using
        <strong>Spring Boot</strong> and <strong>PostgreSQL</strong>. It exposes a REST API to send
        email notifications where <strong>all SMTP configuration is fully database-driven</strong> 
        (no hardcoded SMTP credentials in the code or properties file).
    </p>

    <hr>

    <h2>1. Project Overview</h2>
    <p>
        The main objective of this project is to create a notification service that can send emails
        using Gmail (or other providers) with dynamic configuration. The SMTP details such as
        <code>host</code>, <code>port</code>, <code>username</code>, <code>password</code>, and 
        <code>TLS/SSL</code> flags are stored in a PostgreSQL database table and loaded at runtime.
    </p>

    <hr>

    <h2>2. Features</h2>
    <ul>
        <li>Single REST API to send notification emails.</li>
        <li>All SMTP configuration is stored in the database.</li>
        <li>No hardcoded SMTP credentials in the application code.</li>
        <li>Supports Gmail using App Password for secure authentication.</li>
        <li>Dynamic and easily extendable to multiple email providers.</li>
        <li>Clean layered architecture: Controller → Service → Repository → Database.</li>
    </ul>

    <hr>

    <h2>3. Tech Stack</h2>
    <ul>
        <li><strong>Backend:</strong> Java, Spring Boot</li>
        <li><strong>Database:</strong> PostgreSQL</li>
        <li><strong>ORM:</strong> Spring Data JPA / Hibernate</li>
        <li><strong>Email:</strong> JavaMailSender (Spring Boot Starter Mail)</li>
        <li><strong>Build Tool:</strong> Maven</li>
        <li><strong>Tools:</strong> Eclipse / VS Code, Postman</li>
    </ul>

    <hr>

    <h2>4. API Details</h2>

    <h3>Endpoint</h3>
    <pre><code>POST /api/sendNotification</code></pre>

    <h3>Request Body (JSON)</h3>
    <pre><code>{
  "tomail": "receiver@example.com",
  "subject": "Email Subject",
  "body": "Email Body Content"
}</code></pre>

    <h3>Behavior</h3>
    <ul>
        <li>Validates the incoming request payload.</li>
        <li>Fetches the active SMTP configuration from the <code>mail_config</code> table.</li>
        <li>Builds a <code>JavaMailSenderImpl</code> object dynamically using DB configuration.</li>
        <li>Sends the email to the requested <code>tomail</code> address.</li>
        <li>Returns a success or error response based on the result.</li>
    </ul>

    <hr>

    <h2>5. Database Design</h2>

    <p><strong>Table: <code>mail_config</code></strong></p>

    <pre><code>CREATE TABLE mail_config (
    id          SERIAL PRIMARY KEY,
    provider    VARCHAR(20),
    host        VARCHAR(100),
    port        INT,
    username    VARCHAR(100),
    password    VARCHAR(200),
    from_email  VARCHAR(100),
    use_ssl     BOOLEAN,
    use_tls     BOOLEAN,
    active      BOOLEAN
);</code></pre>

    <p>
        Only the row with <code>active = true</code> is used for sending emails. This allows
        switching the email provider just by updating the database, without changing any code.
    </p>

    <hr>

    <h2>6. High-Level Flow</h2>

    <ol>
        <li>Client sends a POST request to <code>/api/sendNotification</code> with <code>tomail</code>, <code>subject</code>, and <code>body</code>.</li>
        <li>Controller receives the request and forwards it to the service layer.</li>
        <li>Service reads the active SMTP configuration from <code>mail_config</code> via JPA repository.</li>
        <li>Service builds a dynamic <code>JavaMailSenderImpl</code> instance using DB values.</li>
        <li>Email is constructed and sent through Gmail SMTP (using App Password).</li>
        <li>Response is returned to the client.</li>
    </ol>

    <hr>

    <h2>7. How to Run</h2>

    <ol>
        <li>Clone the repository:
            <pre><code>git clone https://github.com/&lt;your-username&gt;/dynamic-notification-service.git</code></pre>
        </li>
        <li>Open the project in your IDE (Eclipse / VS Code / IntelliJ).</li>
        <li>Configure PostgreSQL in <code>application.properties</code>:
<pre><code>spring.datasource.url=jdbc:postgresql://localhost:5432/notificationdb
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
</code></pre>
        </li>
        <li>Create the <code>mail_config</code> table and insert Google SMTP configuration using a Gmail App Password.</li>
        <li>Run the Spring Boot application:
            <pre><code>mvn spring-boot:run</code></pre>
        </li>
        <li>Test the API using Postman:
            <pre><code>POST http://localhost:8080/api/sendNotification</code></pre>
        </li>
    </ol>

    <hr>

    <h2>8. Notes on Gmail App Password</h2>
    <ul>
        <li>Normal Gmail passwords cannot be used for SMTP authentication.</li>
        <li>Enable 2-Step Verification in your Google account.</li>
        <li>Generate an App Password and store it in the <code>password</code> field of <code>mail_config</code>.</li>
    </ul>

    <hr>

    <h2>9. Author</h2>
    <p>
        <strong>Name:</strong> Abhishek Khanai<br>
        <strong>Email:</strong> abhishekkhanai264@gmail.com<br>
        <strong>GitHub:</strong> <a href="https://github.com/Abhishekkhanai07" target="_blank">Abhishekkhanai07</a>
    </p>

</body>
</html>
