package com.example.mysoftstore.Model;

import android.widget.TextView;

public class Admin {
   private String phone;
   private String password;

   public Admin(){

   }

    public Admin(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
