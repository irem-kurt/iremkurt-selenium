##  Selenium UI Test Suite for QA Job Listings

* This project is a Selenium-based test suite that automates the verification of a job listings page, including filters, navigation, and redirections. It was built to validate the end-to-end functionality of a web job board, ensuring that filters like *Location* and *Department* are applied correctly and navigation between tabs works reliably.
* Bugs and issues encountered during development are documented in the Issues section of this repository.
---

## ✅ Features Covered

1. Open the homepage and verify it loads correctly.
2. Navigate to the Careers page via the navigation menu.
3. Validate visibility of:
    - Locations block
    - Teams block
    - Life at Insider block (renamed in code for generalization)
4. Navigate to the Quality Assurance jobs page directly.
5. Apply job filters:
    - Location: *Istanbul, Turkiye*
    - Department: *Quality Assurance*
6. Verify that all listed job cards match both filters.
7. Click the first **"View Role"** button and confirm redirection to the external Lever application form.

---

## 🛠 Technologies Used

- **Java 23+**
- **Maven 3.9.10**
- **Selenium WebDriver**
- **TestNG**
- **ChromeDriver** (Latest version installed via Homebrew)
- **OS:** macOS (Apple Silicon tested)

---

## 🚀 Getting Started

### 1. Clone the repository:
```bash
git clone https://github.com/irem-kurt/iremkurt-selenium.git
cd iremkurt-selenium

### 2. Install dependencies:
java -version
mvn -version
chromedriver --version

if not installed, run:
On macOS : brew install chromedriver
On windows : download from https://chromedriver.chromium.org/downloads or WebDriverManager.chromedriver().setup();
check: chromedriver --version

### 3. Run the tests:

Open IntelliJ IDEA or your preferred IDE and run the tests using TestNG  
**or** type the following in your terminal:

```bash
mvn test
```
---

### 📦 Test Structure

**tests/InsiderQATest.java**  
→ Main test class containing 4 prioritized TestNG tests.

**pages/** *(Page Object Model for page-specific actions)*  
├── **BasePage.java** – Contains common methods for page interactions

├── **HomePage.java** – Handles homepage navigation and verification  

├── **CareerPage.java** – Manages Career page blocks like Locations, Teams  

└── **QAJobsPage.java** – Applies filters and validates QA job listings

**tests/**

└── **InsiderQATest.java**    // Main test class with 4 TestNG test methods


---
⚠️ Known Issues
* The test currently only works reliably on larger screens (e.g., 15"+ MacBook or 1920x1080 resolution).
On smaller screens like a 13-inch MacBook, some UI elements do not render or behave properly, especially dropdown filters and job cards.

* Job filtering sometimes relies on dynamic dropdowns rendered by Select2, which may cause instability depending on screen size or resolution.

* Using Thread.sleep() was necessary in a few places to account for UI delays — can be improved with fluent waits.
---
👩‍💻 Author
@irem-kurt