/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Account.Manager;
import Account.ManagerList;
import Account.UserList;
import Boss.Boss;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class MainMenu {

    
    public static void main(String[] args) throws IOException, Exception {
        try {
            ManagerList managerList = new ManagerList();
            UserList userList = new UserList();
            Boss boss = new Boss();
            Scanner scn = new Scanner(System.in);
            int choice;
            
            do {
                displayMenu();
                choice = getValidInput(scn);
                
                switch (choice) {
                    case 1:
                        userList.addUser();
                        break;
                    case 2:
                        userList.updateUserInfor();
                        break;
                    case 3:
                        userList.userDeleteUser();
                        break;
                    case 4:
                        managerList.printUser();
                        break;
                    case 5:
                        managerList.checkExistUser();
                        break;
                    case 6:
                        managerList.findUserByUserName();
                        break;
                    case 7:
                        boss.createManager();
                        break;
                    case 8:
                        boss.printListManager();
                        break;
                    case 0:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                
                System.out.println();
                
            } while (choice != 0);
            
            scn.close();
            
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    
    private static void displayMenu() {
        System.out.println("===== USER MANAGEMENT SYSTEM =====");
        System.out.println("--For User--");
        System.out.println("1. Create new account");
        System.out.println("2. Update account information");
        System.out.println("3. Delete account");
        System.out.println("--For Manager--");
        System.out.println("4. Print user list");
        System.out.println("5. Check user exist");
        System.out.println("6. Find user by name");
        System.out.println("--For boss--");
        System.out.println("7. Add manager to list");
        System.out.println("8. Print manager list");
        System.out.println("0. Exit");
        System.out.print("Enter your choice (0-8): ");
    }
    
    private static int getValidInput(Scanner scn) {
        while (true) {
            try {
                return scn.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scn.nextLine();
            }
        }
        
    }
}
