package com.baway_04.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

public class UserInfo extends BaseObservable{

    @NonNull
    private String name;
    @NonNull
    private String phone;

    @Bindable
    public String getName() {
        return name;
    }
    @Bindable
    public void setName(String name) {
        this.name = name;
    }
    @Bindable
    public String getPhone() {
        return phone;  
    }
    @Bindable
    public void setPhone(String phone) {
        this.phone = phone;
//        notifyPropertyChanged(com.baway_04.bean.UserInfo.);
    }
    @Bindable
    public void setOnClick(){
        this.name ="clicklist";
    }

}
