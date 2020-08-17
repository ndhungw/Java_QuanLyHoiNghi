package ConferenceManagementSystem.Entities;

public enum requestStatus {
    DECLINED(0,"Từ chối"),APPROVED(1,"Đồng ý"),PENDING(2,"Chờ duyệt");

    private int code;
    private String text;

    private requestStatus(int code, String text) {
        this.code=code;
        this.text=text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static requestStatus getRequestStatusByCode(int statusCode) {
        for (requestStatus rs : requestStatus.values()) {
            if (rs.code == statusCode) {
                return rs;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
