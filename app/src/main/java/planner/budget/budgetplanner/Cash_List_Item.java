package planner.budget.budgetplanner;

import android.widget.ImageView;

/**
 * Created by Rohit on 03-03-2018.
 */

public class Cash_List_Item {
    private String list_description;
    public String list_category;
    private String list_date;
    private float list_amount;
    private int list_image;

    public String getList_description() {
        return list_description;
    }

    public void setList_description(String list_description) {
        this.list_description = list_description;
    }

    public String getList_category() {
        return list_category;
    }

    public void setList_category(String list_category) {
        this.list_category = list_category;
    }

    public String getList_date() {
        return list_date;
    }

    public void setList_date(String list_date) {
        this.list_date = list_date;
    }

    public float getList_amount() {
        return list_amount;
    }

    public void setList_amount(float list_amount) {
        this.list_amount = list_amount;
    }

    public Cash_List_Item(float list_amount, String list_description, String list_category, String list_date){
        this.list_amount=list_amount;
        this.list_description=list_description;
        this.list_category=list_category;
        this.list_date=list_date;
    }

    public int getList_image() {
        return list_image;
    }

    public void setList_image(int list_image) {
        this.list_image = list_image;
    }

    public String getToStringList_amount(){
        String s=String.valueOf(list_amount);
        return s;
    }

  /*  public ImageView getImageView(){
        ImageView imageView=null;
        switch(list_category){
            case "Acc to Acc":
                            if(list_category =="Acc to Acc"){
                            imageView.setImageResource(R.drawable.vc_acc_to_acc);
                             }
            case "Air Tickets":
                            if(list_category == "Air Tickets"){
                                imageView.setImageResource(R.drawable.vc_air_tickets);
                            }
            case "Beauty":
                            if(list_category == "Beauty"){
                                imageView.setImageResource(R.drawable.vc_beauty);
                            }
            case "Bike":
                            if(list_category == "Bike"){
                                imageView.setImageResource(R.drawable.vc_bike);
                            }
            case "Bills/Utilities":
                            if(list_category == "Bills/Utilities"){
                                imageView.setImageResource(R.drawable.vc_bills_uilities);
                            }
            case "Books":
                            if(list_category == "Books"){
                                imageView.setImageResource(R.drawable.vc_books);
                            }
            case "Bus Fare":
                            if(list_category == "Bus Fare"){
                                imageView.setImageResource(R.drawable.vc_bus_fare);
                            }
            case "Business":
                            if(list_category == "Business"){
                                imageView.setImageResource(R.drawable.vc_business);
                            }
            case "Cable":
                            if(list_category == "Cable"){
                                imageView.setImageResource(R.drawable.vc_cable);
                            }
            case "Car":
                            if(list_category == "Car"){
                                imageView.setImageResource(R.drawable.vc_car);
                            }
            case "Cash":
                            if(list_category == "Cash"){
                                imageView.setImageResource(R.drawable.vc_cash);
                            }
            case "CC Bill Payment":
                            if(list_category == "CC Bill Payment"){
                                imageView.setImageResource(R.drawable.vc_credit_card);
                            }
            case "Cigarettes":
                            if(list_category == "Cigarettes"){
                                imageView.setImageResource(R.drawable.vc_cigarettes);
                            }
            case "Coffee":
                            if(list_category == "Coffee"){
                                imageView.setImageResource(R.drawable.vc_coffee);
                            }
            case "Courier":
                            if(list_category == "Courier"){
                                imageView.setImageResource(R.drawable.vc_courier);
                            }
            case "Daily Care":
                            if(list_category == "Daily Care"){
                                imageView.setImageResource(R.drawable.vc_daily_care);
                            }
            case "Dining":
                            if(list_category == "Dining"){
                                imageView.setImageResource(R.drawable.vc_dinner);
                            }
            case "Drinks":
                            if(list_category == "Drinks"){
                                imageView.setImageResource(R.drawable.vc_drinks);
                            }
            case "Education":
                            if(list_category == "Education"){
                                imageView.setImageResource(R.drawable.vc_education);
                            }
            case "Electricity":
                            if(list_category == "Electricity"){
                                imageView.setImageResource(R.drawable.vc_electricity);
                            }
            case "Electronics":
                            if(list_category == "Electronics"){
                                imageView.setImageResource(R.drawable.vc_electronics);
                            }
            case "EMI":
                            if(list_category == "EMI"){
                                imageView.setImageResource(R.drawable.vc_emi);
                            }
            case "Entertainment":
                            if(list_category == "Entertainment"){
                                imageView.setImageResource(R.drawable.vc_entertainment);
                            }
            case "Finance":
                            if(list_category == "Finance"){
                                imageView.setImageResource(R.drawable.vc_finance);
                            }
            case "Fuel":
                            if(list_category == "Fuel"){
                                imageView.setImageResource(R.drawable.vc_fuel);
                            }
            case "Games":
                            if(list_category == "Games"){
                                imageView.setImageResource(R.drawable.vc_games);
                            }
            case "Gift":
                            if(list_category == "Gift"){
                                imageView.setImageResource(R.drawable.vc_gift);
                            }
            case "Gym":
                            if(list_category == "Gym"){
                                imageView.setImageResource((R.drawable.vc_gym));
                            }
            case "Health":
                            if(list_category == "Health"){
                                imageView.setImageResource(R.drawable.vc_health);
                            }
            case "Hotel":
                            if(list_category == "Hotel"){
                                imageView.setImageResource(R.drawable.vc_hotel);
                            }
            case "Household":
                            if(list_category == "Household"){
                                imageView.setImageResource(R.drawable.vc_household);
                            }
            case "Insurance":
                            if(list_category == "Insurance"){
                                imageView.setImageResource(R.drawable.vc_insurance);
                            }
            case "Investments":
                            if (list_category == "Investments") {
                                imageView.setImageResource(R.drawable.vc_investments);
                            }
            case "Kids":
                            if(list_category == "Kids"){
                                imageView.setImageResource(R.drawable.vc_kids);
                            }
            case "Loan":
                            if(list_category == "Loan"){
                                imageView.setImageResource(R.drawable.vc_loan);
                            }
            case "Maintenance":
                            if(list_category == "Maintenance"){
                                imageView.setImageResource(R.drawable.vc_maintenance);
                            }
            case "Mobile":
                            if(list_category == "Mobile"){
                                imageView.setImageResource(R.drawable.vc_mobile);
                            }
            case "Music":
                            if(list_category == "Music"){
                                imageView.setImageResource(R.drawable.vc_music);
                            }
            case "Office":
                            if(list_category == "Office"){
                                imageView.setImageResource(R.drawable.vc_office);
                            }
            case "Rent":
                            if(list_category == "Rent"){
                                imageView.setImageResource(R.drawable.vc_rent);
                            }
            case "Salon":
                            if(list_category == "Salon"){
                                imageView.setImageResource(R.drawable.vc_salon);
                            }
            case "Savings":
                            if(list_category == "Savings"){
                                imageView.setImageResource(R.drawable.vc_savings);
                            }
            case "Shopping":
                            if(list_category == "Shopping"){
                                imageView.setImageResource(R.drawable.vc_shopping);
                            }
            case "Tax":
                            if(list_category == "Tax"){
                                imageView.setImageResource(R.drawable.vc_tax);
                            }
            case "Train":
                            if(list_category == "Train"){
                                imageView.setImageResource(R.drawable.vc_train);
                            }
            case "Travel":
                            if(list_category == "Travel"){
                                imageView.setImageResource(R.drawable.vc_travel);
                            }
            case "Vacation":
                            if(list_category == "Vacation"){
                                imageView.setImageResource(R.drawable.vc_vacation);
                            }
            case "Water Bill":
                            if(list_category == "Water Bill"){
                                imageView.setImageResource(R.drawable.vc_water_bill);
                            }
            case "Others":
                            if(list_category == "Others"){
                                imageView.setImageResource(R.drawable.vc_others);
                            }
             default:   imageView.setImageResource(R.drawable.vc_others);

        }

            return imageView;
    }*/
}
