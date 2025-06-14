/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Account;

import java.io.Serializable;
/**
 *
 * @author User
 */
public abstract class Account implements Serializable{
    private static final long serialVersionUID = 65677884L;
    
    String userName;
    String firstName;
    String lastName;
    String password;
    String phone;
    String email;
    
    public boolean confirmPass(String pass, String password){
        return pass.equals(password);
    }
    
    public boolean checkEmailFormat(String email){
        return email.contains("@") && email.contains(".") && !email.contains(" ");
    }
    
    public boolean checkUserName(String userName){
        return userName.length() >= 5 && !userName.contains(" ");
    }
    
    public boolean checkPass(String password){
        return password.length() >= 6 && !password.contains(" ");
    }
    
    public boolean checkPhone(String phone){
        if(phone.length() != 10) return false;
        
        try{
            Long.parseLong(phone);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    String encrypt(String pass){
        String passEncrypted = "";
        for(int i = 0; i < pass.length(); i++){
            char temp = pass.charAt(i);
            temp = (char) ((19*temp + 5)%128);
            passEncrypted += temp;
        }
        return passEncrypted;
    }
    
    String decrypt(String pass){
        String passEDecrypted = "";
        for(int i = 0; i < pass.length(); i++){
           char temp = pass.charAt(i);
            temp = (char) ((temp-5 + 128)*27%128);
            passEDecrypted += temp;
        }
        return passEDecrypted;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = encrypt(password);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return decrypt(password);
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }
    
    
}
