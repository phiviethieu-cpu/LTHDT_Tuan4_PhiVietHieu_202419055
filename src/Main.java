/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

// Chương trình test: chạy dữ liệu mẫu phần C rồi test các trường hợp biên / lỗi (C.1)
public class Main {
    public static void main(String[] args) {
        System.out.println("===== PHAN C: DU LIEU MAU =====");

        // dùng constructor đầy đủ, addBonus(amount) -> gọi bản 1
        SalariedEmployee an = new SalariedEmployee("E001", "Nguyen Minh An", "Dao tao",
                15000000, 2000000);
        an.addBonus(1000000);

        // addBonus(amount, reason) -> gọi bản 2
        HourlyEmployee binh = new HourlyEmployee("E002", "Tran Thu Binh", "Ho tro", 100000, 150);
        binh.addBonus(500000, "Ho tro khach hang tot");

        HourlyEmployee chi = new HourlyEmployee("E003", "Le Hoang Chi", "Ho tro", 100000, 170);

        // addBonus(rate, referenceAmount, reason) -> gọi bản 3
        SalesEmployee dung = new SalesEmployee("E004", "Pham Quoc Dung", "Kinh doanh",
                8000000, 200000000, 0.05);
        dung.addBonus(0.02, 50000000, "Thuong du an 2% cua 50 trieu");

        Payroll payroll = new Payroll("2026-09");
        payroll.addEmployee(an);
        payroll.addEmployee(binh);
        payroll.addEmployee(chi);
        payroll.addEmployee(dung);
        payroll.displayPayroll();

        System.out.println("\nSo sanh voi ket qua de bai:");
        // findEmployee trả về kiểu Employee nhưng vẫn gọi đúng hàm của lớp con
        System.out.printf("E001: %,.0f (de bai: 18,000,000)%n", payroll.findEmployee("E001").calculateGrossPay());
        System.out.printf("E002: %,.0f (de bai: 15,500,000)%n", payroll.findEmployee("E002").calculateGrossPay());
        System.out.printf("E003: %,.0f (de bai: 17,500,000)%n", payroll.findEmployee("E003").calculateGrossPay());
        System.out.printf("E004: %,.0f (de bai: 19,000,000)%n", payroll.findEmployee("E004").calculateGrossPay());
        System.out.printf("Tong bang luong: %,.0f (de bai: 70,000,000)%n", payroll.calculateTotalPayroll());
        System.out.printf("Tong phong Ho tro: %,.0f (de bai: 33,000,000)%n",
                payroll.calculatePayrollByDepartment("Ho tro"));
        Employee top = payroll.findHighestPaidEmployee();
        System.out.println("Luong cao nhat: " + top.getEmployeeId() + " - " + top.getFullName());

        System.out.println("\n===== C.1: TEST GIA TRI BIEN =====");
        testBien();

        System.out.println("\n===== C.1: TEST DU LIEU SAI =====");
        testLoi(payroll, an);

        System.out.println("\n===== C.1: TEST BANG LUONG RONG =====");
        testBangLuongRong();
    }

    // Test các giá trị biên hợp lệ
    static void testBien() {
        HourlyEmployee h1 = new HourlyEmployee("H01", "Test 160 gio", "Ho tro", 100000, 160);
        System.out.printf("T01. Lam dung 160 gio: %,.0f (mong doi 16,000,000)%n", h1.calculateGrossPay());

        HourlyEmployee h2 = new HourlyEmployee("H02", "Test 250 gio", "Ho tro", 100000, 250);
        System.out.printf("T02. Lam 250 gio (toi da): %,.0f (mong doi 29,500,000)%n", h2.calculateGrossPay());

        HourlyEmployee h3 = new HourlyEmployee("H03", "Test 0 gio", 100000);
        System.out.printf("T03. Constructor rut gon, 0 gio: %,.0f (mong doi 0)%n", h3.calculateGrossPay());
        System.out.println("T04. Phong ban mac dinh: " + h3.getDepartment() + " (mong doi Unassigned)");

        SalesEmployee s1 = new SalesEmployee("S01", "Test hoa hong", 5000000, 0.3);
        s1.updateSalesRevenue(10000000);
        System.out.printf("T05. Hoa hong 0.3: %,.0f (mong doi 8,000,000)%n", s1.calculateGrossPay());

        SalariedEmployee e1 = new SalariedEmployee("R01", "Test thuong", 10000000);
        e1.addBonus(0.5, 2000000, "Ty le 0.5");
        System.out.printf("T06. Thuong ty le 0.5: %,.0f (mong doi 11,000,000)%n", e1.calculateGrossPay());
    }

    // Test dữ liệu sai, chương trình phải báo lỗi
    static void testLoi(Payroll payroll, SalariedEmployee an) {
        try {
            new HourlyEmployee("X1", "A", "B", 100000, 251);
            System.out.println("T07. Lam 251 gio: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T07. Lam 251 gio -> " + e.getMessage());
        }

        try {
            new HourlyEmployee("X2", "A", "B", 100000, -1);
            System.out.println("T08. Lam -1 gio: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T08. Lam -1 gio -> " + e.getMessage());
        }

        try {
            new SalesEmployee("X3", "A", 1000000, 0.31);
            System.out.println("T09. Hoa hong 0.31: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T09. Hoa hong 0.31 -> " + e.getMessage());
        }

        try {
            new SalariedEmployee("  ", "A", 1000000);
            System.out.println("T10. Ma rong: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T10. Ma rong -> " + e.getMessage());
        }

        try {
            new SalariedEmployee("X4", null, 1000000);
            System.out.println("T11. Ho ten null: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T11. Ho ten null -> " + e.getMessage());
        }

        try {
            new SalariedEmployee("X5", "A", "", 1000000, 0);
            System.out.println("T12. Phong ban rong: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T12. Phong ban rong -> " + e.getMessage());
        }

        try {
            new SalariedEmployee("X6", "A", -1);
            System.out.println("T13. Luong am: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T13. Luong am -> " + e.getMessage());
        }

        try {
            new SalariedEmployee("X7", "A", "B", 1000000, -1);
            System.out.println("T14. Phu cap am: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T14. Phu cap am -> " + e.getMessage());
        }

        double thuongTruoc = an.getMonthlyBonus();

        try {
            an.addBonus(0);
            System.out.println("T15. addBonus(0): khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T15. addBonus(0) -> " + e.getMessage());
        }

        try {
            an.addBonus(-500000, "Phat");
            System.out.println("T16. addBonus(-500000, \"Phat\"): khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T16. addBonus(-500000, \"Phat\") -> " + e.getMessage());
        }

        try {
            an.addBonus(100000, "   ");
            System.out.println("T17. Ly do rong: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T17. Ly do rong -> " + e.getMessage());
        }

        try {
            an.addBonus(0, 1000000, "Ty le 0");
            System.out.println("T18. Ty le thuong 0: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T18. Ty le thuong 0 -> " + e.getMessage());
        }

        try {
            an.addBonus(0.51, 1000000, "Qua 0.5");
            System.out.println("T19. Ty le thuong 0.51: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T19. Ty le thuong 0.51 -> " + e.getMessage());
        }

        try {
            an.addBonus(0.1, 0, "Tham chieu 0");
            System.out.println("T20. Gia tri tham chieu 0: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T20. Gia tri tham chieu 0 -> " + e.getMessage());
        }

        // các lần thêm thưởng lỗi ở trên không được làm thay đổi tổng thưởng
        System.out.printf("T21. Thuong E001 sau cac lan loi: %,.0f (truoc do: %,.0f)%n",
                an.getMonthlyBonus(), thuongTruoc);

        SalesEmployee dung = (SalesEmployee) payroll.findEmployee("E004");
        try {
            dung.updateSalesRevenue(-1);
            System.out.println("T22. Doanh so am: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T22. Doanh so am -> " + e.getMessage());
        }

        SalariedEmployee trungMa = new SalariedEmployee("e001", "Trung ma", 1000000);
        System.out.println("T23. Them nhan vien trung ma e001: " + payroll.addEmployee(trungMa));
        System.out.println("T24. Them null: " + payroll.addEmployee(null));
        System.out.println("T25. Tim ma E999: " + payroll.findEmployee("E999"));
        System.out.printf("T26. Tong phong Ke toan (khong co): %,.0f%n",
                payroll.calculatePayrollByDepartment("Ke toan"));

        try {
            new Payroll("");
            System.out.println("T27. Ky luong rong: khong bao loi (SAI)");
        } catch (IllegalArgumentException e) {
            System.out.println("T27. Ky luong rong -> " + e.getMessage());
        }

        an.resetBonus();
        System.out.printf("T28. E001 sau khi reset thuong: %,.0f (mong doi 17,000,000)%n", an.calculateGrossPay());
    }

    // Test bảng lương rỗng, không được lỗi
    static void testBangLuongRong() {
        Payroll rong = new Payroll("2026-10");
        rong.displayPayroll();
        System.out.printf("T29. Tong bang luong rong: %,.0f%n", rong.calculateTotalPayroll());
        System.out.println("T30. Nguoi luong cao nhat: " + rong.findHighestPaidEmployee());
    }
}
