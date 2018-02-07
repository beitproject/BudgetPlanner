package planner.budget.budgetplanner;

/**
 * Created by Rohit on 04-02-2018.
 */

public class SpinnerItem {
    private String mCategoryName;
    private int mCategoryImage;

    public SpinnerItem(String CategoryName, int CategoryImage){
        mCategoryName=CategoryName;
        mCategoryImage=CategoryImage;
    }

    public String getCategoryName (){
        return mCategoryName;
    }

    public int getCategoryImage(){
        return mCategoryImage;
    }
}
