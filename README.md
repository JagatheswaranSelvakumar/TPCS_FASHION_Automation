# 🧪 TPCS Fashion Automation Framework

This project is a Selenium-based test automation framework built using **Java, TestNG, Maven, ExtentReports, and Log4j2**. It follows the **Page Object Model (POM)** design pattern for maintainability and scalability.

---

## 🚀 Tech Stack

* **Java 21**
* **Selenium 4**
* **TestNG**
* **Maven**
* **ExtentReports**
* **Log4j2**
* **WebDriverManager**

---

## 📁 Project Structure

```
TPCS_FASHION-Automation/
│
├── src/main/java
│   ├── pages/              # Page Object classes
│   ├── utilities/          # DriverFactory, ConfigReader, etc.
│   └── reports/            # ExtentReports configuration
│
├── src/test/java
│   ├── base/               # BaseTest setup
│   └── tests/              # Test classes (ColorTest, etc.)
│
├── src/test/resources
│   ├── config.properties   # Environment config
│   ├── log4j2.xml          # Logging configuration
│   └── testng.xml          # TestNG suite
│
├── test-output/            # Reports & screenshots
└── pom.xml                 # Maven dependencies
```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository

```
git clone <your-repo-url>
cd TPCS_FASHION-Automation
```

### 2️⃣ Install Dependencies

```
mvn clean install
```

### 3️⃣ Update Configuration

Edit `src/test/resources/config.properties`:

```
browser=chrome
url=https://your-app-url
username=your-username
password=your-password
```

---

## ▶️ Running Tests

### Run via Maven

```
mvn test
```

### Run via TestNG XML

```
Right-click → testng.xml → Run
```

---

## 📊 Reports

### ✅ Extent Report

Generated at:

```
test-output/ExtentReport.html
```

### 📸 Screenshots

Captured on failure:

```
test-output/screenshots/
```

### 🪵 Logs

Log4j logs:

```
test-output/logs/automation.log
```

---

## 🧱 Framework Design

### ✔ Page Object Model (POM)

* Separates test logic from UI interactions
* Improves readability and reusability

### ✔ DriverFactory

* Centralized WebDriver handling
* Supports Chrome, Firefox, Edge

### ✔ BaseTest

* Manages test lifecycle
* Integrates ExtentReports

### ✔ Utilities

* ConfigReader
* RandomUtils
* Screenshot handling

---

## ✅ Best Practices Used

* No assertions in Page classes ❌
* Exception handling via RuntimeException ✔
* Logging with Log4j ✔
* Reporting with ExtentReports ✔
* Reusable utility methods ✔

---

## 🧪 Sample Test Flow

1. Launch browser
2. Login to application
3. Navigate to Color module
4. Add new color
5. Verify toast message
6. Validate color in list

---

## ❗ Troubleshooting

### 🔴 Screenshot not captured

* Ensure driver is not quit before capture
* Check `test-output/screenshots` folder

### 🔴 ExtentTest is null

* Ensure `startTest()` is called before test steps

### 🔴 Element not found

* Use proper waits (`waitForElementVisible`)

---

## 📌 Future Enhancements

* Parallel execution
* CI/CD (Jenkins/GitHub Actions)
* Docker integration
* API testing integration

---

## 👨‍💻 Author

Automation Framework by **Your Name**

---

## ⭐ Support

If you like this project, give it a ⭐ on GitHub!
