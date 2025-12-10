package changpic;

import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Scanner;
//import javax.swing.DefaultListModel;

public class clients implements Serializable {

    private String userNameAccount;
    private String userPasswordAccount;
    private ArrayList<order> orderClientList  = new ArrayList();

    public String getUserNameAccount() {
        return userNameAccount;
    }

    public void setUserNameAccount(String userNameAccount) {
        this.userNameAccount = userNameAccount;
    }

    public String getUserPasswordAccount() {
        return userPasswordAccount;
    }

    public void setUserPasswordAccount(String userPasswordAccount) {
        this.userPasswordAccount = userPasswordAccount;
    }
/*
    public ArrayList<meals> getMealsClientList() {
        return mealsClientList;
    }

    public void setMealsClientList(ArrayList<meals> mealsClientList) {
        this.mealsClientList = mealsClientList;
    }*/

    public ArrayList<order> getOrderClientList() {
        return orderClientList;
    }

    public void setOrderClientList(ArrayList<order> orderClientList) {
        this.orderClientList = orderClientList;
    }
    
    

    @Override
    public String toString() {
        return "clients{" + "userNameAccount=" + userNameAccount + ", userPasswordAccount=" + userPasswordAccount + ", orderClientList=" + orderClientList + '}';
    }

    

}
