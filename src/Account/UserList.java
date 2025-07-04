/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Account;

import FakeData.FakeData;
import java.util.ArrayList;
import java.util.Scanner;
import FileOperation.FileOperations;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collector;
/**
 *
 * @author User
 */
public class UserList extends ArrayList<User>{
    //private User user = new User();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    transient private FileOperations acting = new FileOperations();
    private static final long serialVersionUID = 85837684L;
    transient private int posi;
   
    transient Scanner scn = new Scanner(System.in);

    public UserList() throws Exception{
        this.clear();
        this.addAll(acting.readFromUser());
        if(this.isEmpty()){
            FakeData fakeData = new FakeData();
            for(int i = 0; i < 10; i++){
                String fakeFistName = fakeData.fakeFistName();
                String fakeLastName = fakeData.fakeLastName();
                autoFakeUser(fakeData.fakeUserName(), fakeFistName, fakeLastName,
                             fakeData.fakePass(), fakeData.fakePhone(),
                             fakeData.fakeEmail(fakeLastName, fakeFistName));
            }
        }
    }

    public void autoFakeUser(String userName, String firstName, String lastName, 
                            String password, String phone, String email) throws Exception{
        User user = new User(userName, firstName, lastName, password, phone, email);
        addUserToUser(user);
    }
    
    public void addUser() throws IOException, Exception{
        User user = new User();
        System.out.println("<=Create your account=>");
        inputUserName(user); inputFirstName(user); inputLastName(user);
        inputPhone(user); inputEmail(user); inputPassword(user);
        System.out.println("Exit if you are sure of your information.");
        chooseAction(user);
        
        Thread inputThread = new Thread(() -> {
            try {
                if(this.size() > 0 && this.get(this.size()-1).getUserName().equals(user.deleted)) {
                    replace(this.size()-1, user);
                }
                else {
                    addUserToUser(user);
                }
            } catch (Exception ex) {
                System.out.println(RED + "\nAccount created failed!" + ANSI_RESET);
                ex.printStackTrace();
            }
        });
        inputThread.start();
    }  
    
    public void addUserToUser(User user) throws IOException, Exception{
        this.add(user);
        Collections.sort(this, Comparator.comparing(User::getUserName));
        acting.addToUser(this);
    }
    
    public void updateUserInfor() throws Exception{
        User user = logIn();
        
        System.out.println("What information do you want to change?");
        chooseAction(user);
        
        Thread updateThread = new Thread(() -> {
            try {
                replace(posi, user);
            } catch (Exception ex) {
                System.out.println(RED + "\nAccount update failed!" + ANSI_RESET);
            }
        });
        updateThread.start();
    }
    
    public void userDeleteUser() throws Exception{
        User user = logIn();
        
        System.out.println("Are you sure to delete your account!"
                           + "(Enter 1 to confirm)");
        short sure;
        sure = scn.nextShort();
        scn.nextLine();
        switch (sure) {
            case 1:
                try {
                    user.setUserName(user.deleted);
                    replace(posi, user);
                    System.out.println(GREEN + "\nAccount deleted successfully." + ANSI_RESET);
                } catch (Exception ex) {
                    System.out.println(RED + "\nAccount deletion failed!" + ANSI_RESET);
                }
                break;
            default:
                System.out.println(RED + "Deletion request has been canceled." + ANSI_RESET);
        }
    }
    
    protected void inputUserName(User user){
        String medium;
        do{
            System.out.print("Enter the user name: ");
            medium = scn.nextLine();
            if(!user.checkUserName(medium)) System.out.println("Invalid user name, please try again!");
            if(user.checkExist(this, User::getUserName, medium)) 
               System.out.println(RED + "This user name has been used, please use another!" + ANSI_RESET);
        }while(!user.checkUserName(medium) || user.checkExist(this, User::getUserName, medium));
        user.setUserName(medium);
    }
    
    protected void inputFirstName(User user){
        System.out.print("Enter your first name: ");
        user.setFirstName(scn.nextLine());
    }
    
    protected void inputLastName(User user){
        System.out.print("Enter your last name: ");
        user.setLastName(scn.nextLine());
    }
    
    protected void inputPassword(User user) throws Exception{
        String medium;
        String mediumCheck;
        do{
            System.out.print("Enter the password: ");
            medium = scn.nextLine();
            if(!user.checkPass(medium)) System.out.println(RED + "Invalid password, please try again!" + ANSI_RESET);
        }while(!user.checkPass(medium));
        
        do{
            System.out.print("Please confirm your password: ");
            mediumCheck = scn.nextLine();
            if(!user.confirmPass(mediumCheck, medium)) System.out.println(RED + "Confirm failed, please try again!" + ANSI_RESET);
            
        }while(!user.confirmPass(mediumCheck, medium));
        
        user.setPassword(medium);
    }
    
    protected void inputPhone(User user){
        String medium;
        do{
            System.out.print("Enter your phone number: ");
            medium = scn.nextLine();
            if(!user.checkPhone(medium)) System.out.println(RED + "Invalid phone number, please try again!" + ANSI_RESET);
        }while(!user.checkPhone(medium));
        user.setPhone(medium);
    }
    
    protected void inputEmail(User user) throws Exception{
        String medium;
        do{
            System.out.print("Enter your email: ");
            medium = scn.nextLine();
            if(!user.checkEmailFormat(medium)) System.out.println("Invalid email, please try again!");
            if(user.checkExist(this, User::getEmail, medium))
                System.out.println(RED + "This email has been used, please use another!" + ANSI_RESET);
        }while(!user.checkEmailFormat(medium) || user.checkExist(this, User::getEmail, medium));
        user.setEmail(medium);
    }
    
    private void chooseAction(User user) throws Exception{
        short choice;
        do{
            System.out.println("Choose an option:");
            System.out.println("1. Change User Name");
            System.out.println("2. Change First Name");
            System.out.println("3. Change Last Name");
            System.out.println("4. Change Password");
            System.out.println("5. Change Phone Number");
            System.out.println("6. Change Email");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            choice = scn.nextShort();
            scn.nextLine(); 

            switch (choice) {
                case 1:
                    inputUserName(user);
                    break;
                case 2:
                    inputFirstName(user);
                    break;
                case 3:
                    inputLastName(user);
                    break;
                case 4:
                    inputPassword(user);
                    break;
                case 5:
                    inputPhone(user);
                    break;
                case 6:
                    inputEmail(user);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println(RED + "Invalid choice, please try again." + ANSI_RESET);
                    break;
                }
            
        }while (choice != 0);
    }
    
    private User logIn() throws Exception{
        User user = null;
        String userName, pass;
        do{
            System.out.print("Enter user name and pass word"
                                + "\nUser name: ");
            userName = scn.nextLine();
            System.out.print("Password: ");
            pass = scn.nextLine();
            findPosition(userName);
            if(posi >= 0){
                user = this.get(posi);
                if(user.getPassword().equals(pass)){
                    System.out.println(GREEN + "Login successfull!" + ANSI_RESET);
                }
            }else
                System.out.println(RED + "Failed, please try again!" + ANSI_RESET);
        }while(posi<0);
        return user;
    }
    
    private void findPosition(String userName) throws Exception{
        User login = new User();
        login.setUserName(userName);
        posi = Collections.binarySearch(this, login
                                            , Comparator.comparing(User::getUserName));
    }
    
    private void replace(final int posi, User user) throws Exception{
        //List<User> usersChange = this;
        this.set(posi, user);
        Collections.sort(this, Comparator.comparing(User::getUserName));
        acting.addToUser(this);  
    }
}
