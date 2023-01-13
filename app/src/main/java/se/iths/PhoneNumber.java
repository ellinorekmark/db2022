package se.iths;

public class PhoneNumber {
    final int numberId;
    String type;
    String number;

    public PhoneNumber(int numberId, String type, String number) {
        this.numberId = numberId;
        this.type = type;
        this.number = number;
    }

    public int getNumberId() {
        return numberId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String toString(){
        return getNumber() +" ("+ getType() +")";
    }
}
