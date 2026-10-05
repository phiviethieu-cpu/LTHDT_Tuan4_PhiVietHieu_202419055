/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

import java.util.ArrayList;
import java.util.List;

// Bảng lương của 1 kỳ (vd: 2026-09)
// Payroll không phải là Employee nên không kế thừa, chỉ chứa danh sách Employee.
// Các hàm tính tổng đều gọi calculateGrossPay() qua kiểu Employee (đa hình),
// nên không cần if/else kiểm tra loại nhân viên.
public class Payroll {
    private final String period;  // kỳ lương, vd: 2026-09
    private final List<Employee> employees = new ArrayList<>();

    public Payroll(String period) {
        if (period == null || period.isBlank()) {
            throw new IllegalArgumentException("Ky luong khong duoc rong");
        }
        this.period = period.trim();
    }

    public String getPeriod() {
        return period;
    }

    public int size() {
        return employees.size();
    }

    // Thêm nhân viên, trả về false nếu null hoặc trùng mã
    public boolean addEmployee(Employee employee) {
        if (employee == null || findEmployee(employee.getEmployeeId()) != null) {
            return false;
        }
        employees.add(employee);
        return true;
    }

    // Tìm theo mã (không phân biệt hoa thường), không thấy thì trả về null
    public Employee findEmployee(String employeeId) {
        if (employeeId == null) {
            return null;
        }
        for (Employee e : employees) {
            if (e.getEmployeeId().equalsIgnoreCase(employeeId.trim())) {
                return e;
            }
        }
        return null;
    }

    // Tổng lương cả bảng, danh sách rỗng thì = 0
    public double calculateTotalPayroll() {
        double total = 0;
        for (Employee e : employees) {
            total += e.calculateGrossPay(); // gọi đa hình
        }
        return total;
    }

    // Tổng lương theo phòng ban
    public double calculatePayrollByDepartment(String department) {
        double total = 0;
        for (Employee e : employees) {
            if (e.getDepartment().equalsIgnoreCase(department == null ? "" : department.trim())) {
                total += e.calculateGrossPay();
            }
        }
        return total;
    }

    // Tìm người lương cao nhất, danh sách rỗng thì trả về null
    public Employee findHighestPaidEmployee() {
        Employee highest = null;
        for (Employee e : employees) {
            if (highest == null || e.calculateGrossPay() > highest.calculateGrossPay()) {
                highest = e;
            }
        }
        return highest;
    }

    // Sang kỳ mới thì reset thưởng cho tất cả
    public void resetAllBonuses() {
        for (Employee e : employees) {
            e.resetBonus();
        }
    }

    // In bảng lương, mỗi nhân viên tự in theo kiểu của mình
    public void displayPayroll() {
        System.out.println("===== BANG LUONG KY " + period + " =====");
        if (employees.isEmpty()) {
            System.out.println("(Bang luong rong)");
            return;
        }
        for (Employee e : employees) {
            e.displayPayrollInfo();
        }
        System.out.printf("TONG BANG LUONG: %,.0f%n", calculateTotalPayroll());
    }
}
