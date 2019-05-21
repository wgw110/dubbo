package tv.mixiong.saas.pay.constants;

/**
 */
public enum PayResult {

    SUCCESS("SUCCESS"),
    FAILURE("FAILURE");

    private String result;

    PayResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
