package ConferenceManagementSystem.Entities;

public enum accType {
    ADMIN(0,"Admin"),USER(1,"User");

    private int code;
    private String text;

    private accType(int code, String text) {
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

    public static accType getAccTypeByCode(int typeCode) {
        for (accType t : accType.values()) {
            if (t.code == typeCode) {
                return t;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
