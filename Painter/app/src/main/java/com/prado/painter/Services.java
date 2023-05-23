package com.prado.painter;

public class Services {
    private static  int sequence = 0;
    private int id;
    String NameClient;
    float Value;
    String Is_Budget;
    String Has_discount;
    String Value_discount;
    EType Type;

    public Services(String name,float value, String budget,String has_discount,String value_discount,int type) {
        this.id = sequence++;
        this.setNameClient(name);
        this.setValue(value);
        this.setIs_Budget(budget);
        this.setHas_discount(has_discount);
        this.setValue_discount("0");
        this.setType(GetTypeById(type));


    }

    public int getId() {
        return id;
    }

    public EType GetTypeById(int number) {
        switch (number) {
            case 0:
                return EType.house;
            case 1:
                return EType.building;
            case 2:
                return EType.business;
            default:
                return null;
        }
    }

    public String getType() {
        return Type.toString();
    }

    public void setType(EType type) {
        Type = type;
    }

    public String getNameClient() {
        return NameClient;
    }
    public void setNameClient(String nameClient) {
        NameClient = nameClient;
    }

    public float getValue() {return Value;}
    public void setValue(float value) {
        Value = value;
    }

    public String Is_Budget() {
        return Is_Budget;
    }
    public void setIs_Budget(String is_Budget) {
        Is_Budget = is_Budget;
    }


    public String getHas_discount() {return Has_discount;}
    public void setHas_discount(String has_discount) {Has_discount = has_discount;}


    public String getValue_discount() {return Value_discount;}
    public void setValue_discount(String value_discount) {Value_discount = value_discount;}



}