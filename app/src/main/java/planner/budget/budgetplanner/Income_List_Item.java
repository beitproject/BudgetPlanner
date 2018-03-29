package planner.budget.budgetplanner;

/**
 * Created by Rohit on 10-03-2018.
 */

public class Income_List_Item {
    private String income_list_description;
    public String income_list_category;
    private String income_list_date;
    private float income_list_amount;

    public String getIncome_list_description() {
        return income_list_description;
    }

    public void setIncome_list_description(String income_list_description) {
        this.income_list_description = income_list_description;
    }

    public String getIncome_list_category() {
        return income_list_category;
    }

    public void setIncome_list_category(String income_list_category) {
        this.income_list_category = income_list_category;
    }

    public String getIncome_list_date() {
        return income_list_date;
    }

    public void setIncome_list_date(String income_list_date) {
        this.income_list_date = income_list_date;
    }

    public float getIncome_list_amount() {
        return income_list_amount;
    }

    public void setIncome_list_amount(float income_list_amount) {
        this.income_list_amount = income_list_amount;
    }

    public Income_List_Item(float income_list_amount, String income_list_description, String income_list_category, String income_list_date){
        this.income_list_amount = income_list_amount;
        this.income_list_description = income_list_description;
        this.income_list_category = income_list_category;
        this.income_list_date = income_list_date;
    }

    public String getToStringIncome_list_amount(){
        String s1 = String.valueOf(income_list_amount);
        return s1;
    }
}
