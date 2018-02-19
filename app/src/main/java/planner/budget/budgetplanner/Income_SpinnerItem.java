package planner.budget.budgetplanner;

/**
 * Created by Rohit on 17-02-2018.
 */

public class Income_SpinnerItem {
    private String nCategoryName;
    private int nCategoryImage;

    public Income_SpinnerItem(String categoryName, int categoryImage){
        nCategoryName= categoryName;
        nCategoryImage= categoryImage;
    }

    public String getnCategoryName(){   return nCategoryName;  }

    public int getnCategoryImage(){ return nCategoryImage;}
}
