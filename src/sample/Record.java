package sample;

import javafx.beans.property.SimpleStringProperty;

public class Record {
    //Assume each record have 6 elements, all String

    public SimpleStringProperty f1, f2, f3, f4, f5, f6, f7, f8, f9;

    public String getF1() {
        return f1.get();
    }

    public String getF2() {
        return f2.get();
    }

    public String getF3() {
        return f3.get();
    }

    public String getF4() {
        return f4.get();
    }

    public String getF5() {
        return f5.get();
    }

    public String getF6() {
        return f6.get();
    }

    public String getF7() {
        return f7.get();
    }

    public String getF8() {
        return f8.get();
    }

    public String getF9() {
        return f9.get();
    }

    Record(String f1, String f2, String f3, String f4,
           String f5, String f6, String f7, String f8, String f9) {
        this.f1 = new SimpleStringProperty(f1);
        this.f2 = new SimpleStringProperty(f2);
        this.f3 = new SimpleStringProperty(f3);
        this.f4 = new SimpleStringProperty(f4);
        this.f5 = new SimpleStringProperty(f5);
        this.f6 = new SimpleStringProperty(f6);
        this.f7 = new SimpleStringProperty(f7);
        this.f8 = new SimpleStringProperty(f8);
        this.f9 = new SimpleStringProperty(f9);
    }
}
