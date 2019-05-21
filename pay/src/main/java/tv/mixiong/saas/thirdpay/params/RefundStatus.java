package tv.mixiong.saas.thirdpay.params;

public enum RefundStatus {
    SUCCESS(1),
    FAIL(2),
    REPEAT(3);

    private int status;
    RefundStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
