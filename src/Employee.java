/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Lớp cha cho các loại nhân viên.
// Để abstract vì không có nhân viên "chung chung", loại nào cũng có cách tính lương riêng.
// Ràng buộc: mã, họ tên, phòng ban không rỗng; thưởng không âm.
public abstract class Employee {
    public static final String DEFAULT_DEPARTMENT = "Unassigned";
    private static final String DEFAULT_REASON = "Thuong co dinh";
    private static final double MAX_BONUS_RATE = 0.5;

    private final String employeeId;
    private String fullName;
    private String department;
    private double monthlyBonus;
    // lưu thêm lịch sử để biết từng khoản thưởng vì lý do gì
    private final List<BonusRecord> bonusHistory = new ArrayList<>();

    // Constructor rút gọn, phòng ban mặc định là "Unassigned"
    protected Employee(String employeeId, String fullName) {
        this(employeeId, fullName, DEFAULT_DEPARTMENT);
    }

    // Constructor đầy đủ, các constructor khác đều gọi về đây nên chỉ cần kiểm tra 1 lần
    protected Employee(String employeeId, String fullName, String department) {
        this.employeeId = requireNotBlank(employeeId, "Ma nhan su");
        this.fullName = requireNotBlank(fullName, "Ho ten");
        this.department = requireNotBlank(department, "Phong ban");
        this.monthlyBonus = 0;
    }

    // Hàm kiểm tra chuỗi không rỗng, dùng chung cho cả lớp con
    protected static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " khong duoc rong");
        }
        return value.trim();
    }

    // Hàm kiểm tra số không âm, dùng chung cho cả lớp con
    protected static double requireNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " khong duoc am");
        }
        return value;
    }

    // ===== Getter / Setter =====

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = requireNotBlank(fullName, "Ho ten");
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = requireNotBlank(department, "Phong ban");
    }

    public double getMonthlyBonus() {
        return monthlyBonus;
    }

    // Trả về list chỉ đọc để bên ngoài không sửa được
    public List<BonusRecord> getBonusHistory() {
        return Collections.unmodifiableList(bonusHistory);
    }

    // ===== Nạp chồng addBonus =====

    // Cách 1: thưởng số tiền cố định, không có lý do
    public void addBonus(double amount) {
        addBonus(amount, DEFAULT_REASON);
    }

    // Cách 2: thưởng số tiền cố định kèm lý do
    // 2 cách còn lại đều gọi vào hàm này
    public void addBonus(double amount, String reason) {
        BonusRecord record = new BonusRecord(amount, reason); // kiểm tra amount, reason trong BonusRecord
        bonusHistory.add(record);
        monthlyBonus += record.getAmount();
    }

    // Cách 3: thưởng theo tỷ lệ của 1 số tiền tham chiếu, rate trong (0; 0.5]
    public void addBonus(double rate, double referenceAmount, String reason) {
        if (rate <= 0 || rate > MAX_BONUS_RATE) {
            throw new IllegalArgumentException("Ty le thuong phai trong khoang (0; 0.5]");
        }
        if (referenceAmount <= 0) {
            throw new IllegalArgumentException("Gia tri tham chieu phai lon hon 0");
        }
        addBonus(rate * referenceAmount, reason);
    }

    // Xóa thưởng khi sang kỳ lương mới
    public void resetBonus() {
        monthlyBonus = 0;
        bonusHistory.clear();
    }

    // ===== Các hàm lớp con phải ghi đè =====

    // Tính thu nhập trước khấu trừ, mỗi loại nhân viên có công thức khác nhau
    public abstract double calculateGrossPay();

    public abstract String getEmployeeType();

    // In thông tin chung, lớp con ghi đè để in thêm phần riêng
    public void displayPayrollInfo() {
        System.out.printf("[%s] %s - %s | Phong: %s%n",
                getEmployeeType(), employeeId, fullName, department);
        System.out.printf("   Thuong: %,.0f%n", monthlyBonus);
        for (BonusRecord record : bonusHistory) {
            System.out.println("     + " + record);
        }
    }

    // In dòng tổng thu nhập (lớp con gọi ở cuối displayPayrollInfo)
    protected void printGrossPay() {
        System.out.printf("   => Thu nhap: %,.0f%n", calculateGrossPay());
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s (%s): %,.0f",
                getEmployeeType(), employeeId, fullName, department, calculateGrossPay());
    }
}
