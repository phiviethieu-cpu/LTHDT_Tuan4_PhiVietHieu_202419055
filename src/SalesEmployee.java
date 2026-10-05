/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

// Nhân viên kinh doanh
// Thu nhập = lương cơ bản + doanh số * tỷ lệ hoa hồng + thưởng
public class SalesEmployee extends Employee {
    public static final double MAX_COMMISSION_RATE = 0.3;

    private double baseSalary;      // lương cơ bản (>= 0)
    private double salesRevenue;    // doanh số (>= 0)
    private double commissionRate;  // tỷ lệ hoa hồng (0 - 0.3)

    // Constructor rút gọn: phòng ban mặc định, doanh số = 0
    public SalesEmployee(String employeeId, String fullName, double baseSalary, double commissionRate) {
        this(employeeId, fullName, DEFAULT_DEPARTMENT, baseSalary, 0, commissionRate);
    }

    // Constructor đầy đủ
    public SalesEmployee(String employeeId, String fullName, String department,
                         double baseSalary, double salesRevenue, double commissionRate) {
        super(employeeId, fullName, department);
        setBaseSalary(baseSalary);
        updateSalesRevenue(salesRevenue);
        setCommissionRate(commissionRate);
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = requireNonNegative(baseSalary, "Luong co ban");
    }

    public double getSalesRevenue() {
        return salesRevenue;
    }

    // Cập nhật doanh số, không cho số âm
    public void updateSalesRevenue(double salesRevenue) {
        this.salesRevenue = requireNonNegative(salesRevenue, "Doanh so");
    }

    // Cộng thêm doanh số khi có hợp đồng mới
    public void addSales(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Doanh so cong them phai lon hon 0");
        }
        salesRevenue += amount;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        if (commissionRate < 0 || commissionRate > MAX_COMMISSION_RATE) {
            throw new IllegalArgumentException("Ty le hoa hong phai trong khoang [0; 0.3]");
        }
        this.commissionRate = commissionRate;
    }

    // Tiền hoa hồng
    public double calculateCommission() {
        return salesRevenue * commissionRate;
    }

    @Override
    public double calculateGrossPay() {
        return baseSalary + calculateCommission() + getMonthlyBonus();
    }

    @Override
    public String getEmployeeType() {
        return "Sales";
    }

    @Override
    public void displayPayrollInfo() {
        super.displayPayrollInfo();
        System.out.printf("   Luong co ban: %,.0f | Doanh so: %,.0f x %.1f%% = Hoa hong: %,.0f%n",
                baseSalary, salesRevenue, commissionRate * 100, calculateCommission());
        printGrossPay();
    }
}
