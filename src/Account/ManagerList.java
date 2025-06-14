/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Account;

import FileOperation.FileOperations;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class ManagerList extends ArrayList<Manager>{
    private static final long serialVersionUID = 77787176L;
    private FileOperations list = new FileOperations();
    transient private int posi;
    transient private List<User> UserList;
   
    transient Scanner scn = new Scanner(System.in);

    public void loadUser() throws Exception {
        UserList = list.readFromUser();
        this.clear();
        this.addAll(list.readFromManager());
        Collections.sort(this, Comparator.comparing(Manager::getUserName));
    }
    
    public void printUser() throws Exception{
        logIn();
        loadUser();
        System.out.println("<== User list ==>");
        for(User n : UserList){
            if(n.getUserName().equals(n.deleted)) break;
            printList(n);
        }
    }
    
    public void checkExistUser() throws Exception{
        logIn();
        loadUser();
        System.out.print("Enter user name you want to check: ");
        String username = scn.nextLine();
        User user = new User();
        String message = user.checkExist(UserList, User::getUserName, username)? "User exist!"
                                                                              : "No user found!";
        System.out.println(message);
    }
    
    public void findUserByUserName() throws Exception{
        logIn();
        loadUser();
        User user = new User();
        System.out.print("Enter the user name: ");
        String username = scn.nextLine();
        user.setUserName(username);
        int userPosi = Collections.binarySearch(UserList, user
                                            , Comparator.comparing(User::getUserName));
        if(userPosi >= 0){
            printList(UserList.get(userPosi));
        }else{
            System.out.println("User does not exist");
        }
    }
    
    private void logIn() throws Exception{
        loadUser();
        Collections.sort(this, Comparator.comparing(Manager::getUserName));
        String username, pass;
        do{
            System.out.print("Enter user name and pass word"
                                + "\nUser name: ");
            username = scn.nextLine();
            System.out.print("Password: ");
            pass = scn.nextLine();
            findPosition(username ,this);
            if(posi >= 0){
                break;
            }else
                System.out.println("Failed, please try again!");
        }while(true);
    }
    
    private void findPosition(String userName, List<Manager> manager) throws Exception{
        Manager login = new Manager();
        login.setUserName(userName);
        posi = Collections.binarySearch(this, login
                                            , Comparator.comparing(Manager::getUserName));                                  
    }
    
    private void printList(User n){
        System.out.println("Name: " + n.lastName + " " + n.firstName
                                    + "\nUser name: " + n.userName
                                    + "\nPhone number: " + n.phone
                                    + "\nEmail: " + n.email
                                    + "\n----------");
    }
}
