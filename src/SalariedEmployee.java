/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

// Nhân viên lương cố định
// Thu nhập = lương tháng + phụ cấp + thưởng
public class SalariedEmployee extends Employee {
    private double monthlySalary;            // lương tháng (>= 0)
    private double responsibilityAllowance;  // phụ cấp trách nhiệm (>= 0)

    // Constructor rút gọn: phòng ban mặc định, phụ cấp = 0
    public SalariedEmployee(String employeeId, String fullName, double monthlySalary) {
        this(employeeId, fullName, DEFAULT_DEPARTMENT, monthlySalary, 0);
    }

    // Constructor đầy đủ
    public SalariedEmployee(String employeeId, String fullName, String department,
                            double monthlySalary, double responsibilityAllowance) {
        super(employeeId, fullName, department);
        setMonthlySalary(monthlySalary);
        setResponsibilityAllowance(responsibilityAllowance);
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = requireNonNegative(monthlySalary, "Luong thang");
    }

    public double getResponsibilityAllowance() {
        return responsibilityAllowance;
    }

    public void setResponsibilityAllowance(double responsibilityAllowance) {
        this.responsibilityAllowance = requireNonNegative(responsibilityAllowance, "Phu cap");
    }

    @Override
    public double calculateGrossPay() {
        return monthlySalary + responsibilityAllowance + getMonthlyBonus();
    }

    @Override
    public String getEmployeeType() {
        return "Salaried";
    }

    @Override
    public void displayPayrollInfo() {
        super.displayPayrollInfo();
        System.out.printf("   Luong thang: %,.0f | Phu cap: %,.0f%n",
                monthlySalary, responsibilityAllowance);
        printGrossPay();
    }
}
