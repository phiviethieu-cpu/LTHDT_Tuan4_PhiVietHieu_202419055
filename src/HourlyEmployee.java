/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

// Nhân viên làm theo giờ
// Giờ làm quá 160 thì phần vượt được tính 1.5 lần đơn giá
// Thu nhập = lương theo giờ + thưởng
public class HourlyEmployee extends Employee {
    public static final double STANDARD_HOURS = 160;      // ngưỡng giờ chuẩn
    public static final double OVERTIME_MULTIPLIER = 1.5; // hệ số giờ vượt
    public static final double MAX_HOURS = 250;           // số giờ tối đa

    private double hourlyRate;   // đơn giá 1 giờ (>= 0)
    private double workedHours;  // số giờ làm (0 - 250)
    // không lưu tiền làm thêm, khi cần thì tính lại từ 2 biến trên

    // Constructor rút gọn: phòng ban mặc định, số giờ = 0
    public HourlyEmployee(String employeeId, String fullName, double hourlyRate) {
        this(employeeId, fullName, DEFAULT_DEPARTMENT, hourlyRate, 0);
    }

    // Constructor đầy đủ
    public HourlyEmployee(String employeeId, String fullName, String department,
                          double hourlyRate, double workedHours) {
        super(employeeId, fullName, department);
        setHourlyRate(hourlyRate);
        setWorkedHours(workedHours);
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = requireNonNegative(hourlyRate, "Don gia gio");
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(double workedHours) {
        if (workedHours < 0 || workedHours > MAX_HOURS) {
            throw new IllegalArgumentException("So gio lam phai trong khoang [0; 250]");
        }
        this.workedHours = workedHours;
    }

    // Số giờ thường (tối đa 160)
    public double getRegularHours() {
        return Math.min(workedHours, STANDARD_HOURS);
    }

    // Số giờ vượt 160, không vượt thì = 0
    public double getOvertimeHours() {
        return Math.max(0, workedHours - STANDARD_HOURS);
    }

    // Lương theo giờ, chưa cộng thưởng
    public double calculateBasePay() {
        return getRegularHours() * hourlyRate
                + getOvertimeHours() * hourlyRate * OVERTIME_MULTIPLIER;
    }

    @Override
    public double calculateGrossPay() {
        return calculateBasePay() + getMonthlyBonus();
    }

    @Override
    public String getEmployeeType() {
        return "Hourly";
    }

    @Override
    public void displayPayrollInfo() {
        super.displayPayrollInfo();
        System.out.printf("   Don gia: %,.0f | Gio thuong: %.1f | Gio vuot nguong: %.1f | Luong gio: %,.0f%n",
                hourlyRate, getRegularHours(), getOvertimeHours(), calculateBasePay());
        printGrossPay();
    }
}
