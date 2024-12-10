**FinanceTracker by team MarJAVA**
FinanceTracker is a personal finance management application built using Spring Boot that allows users to track their financial transactions and manage debts efficiently. 
This application enables users to add, view, and manage transactions, along with keeping track of debts and people who owe money for each transaction, the due date for the debt and a description of the debt.

**Features**

-Transaction Management: Add, view, delete and update transactions including the amount spent, if it is an income or expense, and the associated people, as well as when, why and how much money these people need to pay you back.
-Debt Manager: Track multiple debtors for each transaction and automatically calculate the amounts owed by each person.
-Debt Table: A dedicated table for displaying debts related to each transaction, as well as standalone debts.
-Debt Calculation: When adding a transaction, users can add one or more debtors, and the system will automatically update the amount each person owes.
-Real-time Data Sync: Any changes made in the transactions automatically update the debt table and the database, and vice versa.

**Technologies Used**

- Backend: Spring Boot, Spring Data JPA, Hibernate
- Frontend: HTML, CSS, jQuery
- Database: H2 (In-memory)

**Setup and Installation**

*Prerequisites*
- Java 11 or higher
- Maven
- IDE
- Database (H2)

*Steps to Run the Application*

1.Clone the repository:
   
   git clone https://github.com/Prachi-sarda/FinanceTracker.git
   cd FinanceTracker
   
2. Configure Database Settings:
Navigate to src/main/resources/application.properties and modify the database connection settings.

If you wish to store the database file on your system, change the database URL to a file path. For example:
spring.datasource.url=jdbc:h2:file:/path/to/your/database/finance_tracker;DB_CLOSE_ON_EXIT=FALSE

3. Build the project using Maven:
   
   mvn clean install
   

4. Run the Spring Boot application:

   mvn spring-boot:run


5. The application will start running on [http://localhost:8080](http://localhost:8080). You can access it via your browser.

*Database Configuration*
- The application uses H2 by default for simplicity


*Frontend Configurations*
- The frontend uses jQuery to dynamically add/remove debtors in the transactions table.
- Ensure that the necessary jQuery libraries are included in your project.


**Usage**

1. Adding Transactions: 
   - When adding a transaction, you can specify the amount and the people associated with it. The system will automatically calculate how much each person owes, and update it in the debt table.
   
2. Debt Management: 
   - Each transaction has a related entry in the debt table where the debtor, the owed amount, the description, due date and debt type are displayed.
   
3. Debt Table: 
   - All debts are displayed below the transaction list, and you can manage them in real-time.


**Future Enhancements**
- Add user authentication to track finances for different users.
- Support for exporting financial reports (PDF/Excel).
- Integration with external payment platforms or bank APIs for automated transaction syncing.


Prachi