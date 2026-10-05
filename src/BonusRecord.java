/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

// Lớp lưu 1 lần thưởng gồm số tiền và lý do.
// Dùng lớp này để lưu lịch sử thưởng thay vì 2 mảng song song.
public final class BonusRecord {
    private final double amount;  // số tiền thưởng (> 0)
    private final String reason;  // lý do thưởng (không rỗng)

    public BonusRecord(double amount, String reason) {
        if (amount <= 0) {
            throw new IllegalArgumentException("So tien thuong phai lon hon 0");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Ly do thuong khong duoc rong");
        }
        this.amount = amount;
        this.reason = reason.trim();
    }

    public double getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return String.format("%,.0f (%s)", amount, reason);
    }
}
