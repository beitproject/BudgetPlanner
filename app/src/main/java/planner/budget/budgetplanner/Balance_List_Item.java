package planner.budget.budgetplanner;

/**
 * Created by Rohit on 24-03-2018.
 */

public class Balance_List_Item {
    String bal_txntype;
    float bal_txnamt;
    String bal_category;
    String bal_date;
    float balance;

    public String getBal_txntype() {
        return bal_txntype;
    }

    public void setBal_txntype(String bal_txntype) {
        this.bal_txntype = bal_txntype;
    }

    public float getBal_txnamt() {
        return bal_txnamt;
    }

    public void setBal_txnamt(float bal_txnamt) {
        this.bal_txnamt = bal_txnamt;
    }

    public String getBal_category() {
        return bal_category;
    }

    public void setBal_category(String bal_category) {
        this.bal_category = bal_category;
    }

    public String getBal_date() {
        return bal_date;
    }

    public void setBal_date(String bal_date) {
        this.bal_date = bal_date;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Balance_List_Item(String bal_txntype, float bal_txnamt, String bal_category, String bal_date, float balance) {
        this.bal_txntype = bal_txntype;
        this.bal_txnamt = bal_txnamt;
        this.bal_category = bal_category;
        this.bal_date = bal_date;
        this.balance = balance;
    }

    //**for txnamt to String***
    public String getToString_txnamt(){
        String s1 = String.valueOf(bal_txnamt);
        return s1;
    }

    //***for balance to String ***
    public String getToString_balance(){
        String s2 = String.valueOf(balance);
        return s2;
    }
}
