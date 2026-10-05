# LTHDT - Tuần 4: Hệ thống tính lương và thưởng nhân sự

- Mã sinh viên: 202419055
- Họ tên: Phí Việt Hiếu

Minh họa kế thừa, nạp chồng constructor / phương thức (`addBonus`), ghi đè (`calculateGrossPay`)
và đa hình qua bảng lương `Payroll`.

## Cấu trúc thư mục

```
src/Employee.java          # lớp cơ sở trừu tượng, 3 phiên bản addBonus()
src/BonusRecord.java       # một khoản thưởng (số tiền + lý do)
src/SalariedEmployee.java  # nhân viên lương cố định
src/HourlyEmployee.java    # nhân viên theo giờ (vượt 160h x1.5)
src/SalesEmployee.java     # nhân viên kinh doanh (lương cơ bản + hoa hồng)
src/Payroll.java           # bảng lương một kỳ, tổng hợp đa hình
src/Main.java              # kiểm thử phần C và 30 tình huống biên/lỗi (C.1)
```

## Sơ đồ lớp

Ký hiệu: `+` public, `-` private, `#` protected, `$` static, `*` abstract;
`<|--` kế thừa, `o--` kết tập (aggregation), `*--` hợp thành (composition).

```mermaid
classDiagram
    direction TB

    class Employee {
        <<abstract>>
        +String DEFAULT_DEPARTMENT$
        -double MAX_BONUS_RATE$
        -String employeeId
        -String fullName
        -String department
        -double monthlyBonus
        -List~BonusRecord~ bonusHistory
        #Employee(String employeeId, String fullName)
        #Employee(String employeeId, String fullName, String department)
        #requireNotBlank(String value, String fieldName)$ String
        #requireNonNegative(double value, String fieldName)$ double
        +getEmployeeId() String
        +getFullName() String
        +setFullName(String fullName) void
        +getDepartment() String
        +setDepartment(String department) void
        +getMonthlyBonus() double
        +getBonusHistory() List~BonusRecord~
        +addBonus(double amount) void
        +addBonus(double amount, String reason) void
        +addBonus(double rate, double referenceAmount, String reason) void
        +resetBonus() void
        +calculateGrossPay()* double
        +getEmployeeType()* String
        +displayPayrollInfo() void
        #printGrossPay() void
    }

    class BonusRecord {
        <<final>>
        -double amount
        -String reason
        +BonusRecord(double amount, String reason)
        +getAmount() double
        +getReason() String
        +toString() String
    }

    class SalariedEmployee {
        -double monthlySalary
        -double responsibilityAllowance
        +SalariedEmployee(String id, String name, double monthlySalary)
        +SalariedEmployee(String id, String name, String dept, double salary, double allowance)
        +getMonthlySalary() double
        +setMonthlySalary(double monthlySalary) void
        +getResponsibilityAllowance() double
        +setResponsibilityAllowance(double allowance) void
        +calculateGrossPay() double
        +getEmployeeType() String
        +displayPayrollInfo() void
    }

    class HourlyEmployee {
        +double STANDARD_HOURS$
        +double OVERTIME_MULTIPLIER$
        +double MAX_HOURS$
        -double hourlyRate
        -double workedHours
        +HourlyEmployee(String id, String name, double hourlyRate)
        +HourlyEmployee(String id, String name, String dept, double rate, double hours)
        +getHourlyRate() double
        +setHourlyRate(double hourlyRate) void
        +getWorkedHours() double
        +setWorkedHours(double workedHours) void
        +getRegularHours() double
        +getOvertimeHours() double
        +calculateBasePay() double
        +calculateGrossPay() double
        +getEmployeeType() String
        +displayPayrollInfo() void
    }

    class SalesEmployee {
        +double MAX_COMMISSION_RATE$
        -double baseSalary
        -double salesRevenue
        -double commissionRate
        +SalesEmployee(String id, String name, double baseSalary, double commissionRate)
        +SalesEmployee(String id, String name, String dept, double base, double revenue, double rate)
        +getBaseSalary() double
        +setBaseSalary(double baseSalary) void
        +getSalesRevenue() double
        +updateSalesRevenue(double salesRevenue) void
        +addSales(double amount) void
        +getCommissionRate() double
        +setCommissionRate(double commissionRate) void
        +calculateCommission() double
        +calculateGrossPay() double
        +getEmployeeType() String
        +displayPayrollInfo() void
    }

    class Payroll {
        -String period
        -List~Employee~ employees
        +Payroll(String period)
        +getPeriod() String
        +size() int
        +addEmployee(Employee employee) boolean
        +findEmployee(String employeeId) Employee
        +calculateTotalPayroll() double
        +calculatePayrollByDepartment(String department) double
        +findHighestPaidEmployee() Employee
        +resetAllBonuses() void
        +displayPayroll() void
    }

    Employee <|-- SalariedEmployee
    Employee <|-- HourlyEmployee
    Employee <|-- SalesEmployee
    Payroll "1" o-- "0..*" Employee : employees
    Employee "1" *-- "0..*" BonusRecord : bonusHistory
```

Ghi chú: `calculateGrossPay()`, `getEmployeeType()`, `displayPayrollInfo()` trong ba lớp dẫn xuất
là phương thức **ghi đè** (`@Override`); ba phiên bản `addBonus()` trong `Employee` là **nạp chồng**.

## Chạy

```
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```
