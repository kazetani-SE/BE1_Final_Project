/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Boss;

import Account.Manager;
import FileOperation.FileOperations;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Boss implements Serializable {
    private static final long serialVersionUID = 66798383L;
    private List<Manager> managerList;
    
    private transient FileOperations fileOperations;
    private transient Scanner scanner;
    private transient String loginName = "BossPro";
    private transient String bossPass = "66798383";

    public Boss() throws Exception {
        this.fileOperations = new FileOperations();
        this.scanner = new Scanner(System.in);
        this.managerList = new ArrayList<>();
        loadManagersFromFile();
    }

    private void loadManagersFromFile() throws Exception {
        List<Manager> loadedManagers = fileOperations.readFromManager();
        if (loadedManagers != null) {
            managerList.addAll(loadedManagers);
        }
    }

    public void createManager() throws IOException, Exception {
        logIn();
        Manager manager = new Manager();

        System.out.println("<= Create Manager Account =>");
        inputUserName(manager);
        inputFirstName(manager);
        inputLastName(manager);
        inputPhone(manager);
        inputEmail(manager);
        inputPassword(manager);

        System.out.println("Review your information before saving:");
        printManagerInfo(manager);

        System.out.println("Confirm to create this account? (1: Yes, 0: Cancel)");
        int confirm = scanner.nextInt();
        scanner.nextLine();

        if (confirm == 1) {
            addManagerToList(manager);
            System.out.println("Manager account created successfully!");
        } else {
            System.out.println("Account creation cancelled.");
        }
    }
    
    public void printListManager(){
        logIn();
        managerList.forEach(mng -> {
            printManagerInfo(mng);
        });
    }
    
    private void logIn(){
        String logName, pass;
        do{
            System.out.print("Eneter login name: ");
            logName = scanner.nextLine();
            System.out.print("Enter password: ");
            pass = scanner.nextLine();
            if(!logName.equals(loginName) || !pass.equals(bossPass)){
                System.out.println("Login failed, please try again!");
            }
        }while(!logName.equals(loginName) || !pass.equals(bossPass));
    }

    private void inputUserName(Manager manager) {
        String input;
        do {
            System.out.print("Enter username: ");
            input = scanner.nextLine();
            if (!manager.checkUserName(input))
                System.out.println("Invalid username. It must be at least 5 characters and contain no spaces.");
            if (manager.checkExist(managerList, Manager::getUserName, input))
                System.out.println("This username already exists. Please choose another.");
        } while (!manager.checkUserName(input) || manager.checkExist(managerList, Manager::getUserName, input));
        manager.setUserName(input);
    }

    private void inputFirstName(Manager manager) {
        System.out.print("Enter first name: ");
        manager.setFirstName(scanner.nextLine());
    }

    private void inputLastName(Manager manager) {
        System.out.print("Enter last name: ");
        manager.setLastName(scanner.nextLine());
    }

    private void inputPhone(Manager manager) {
        String input;
        do {
            System.out.print("Enter phone number: ");
            input = scanner.nextLine();
            if (!manager.checkPhone(input))
                System.out.println("Invalid phone number. It must contain exactly 10 digits and no letters.");
        } while (!manager.checkPhone(input));
        manager.setPhone(input);
    }

    private void inputEmail(Manager manager) {
        String input;
        do {
            System.out.print("Enter email: ");
            input = scanner.nextLine();
            if (!manager.checkEmailFormat(input))
                System.out.println("Invalid email format. It must contain '@' and '.' with no spaces.");
            if (manager.checkExist(managerList, Manager::getEmail, input))
                System.out.println("This email already exists. Please use another.");
        } while (!manager.checkEmailFormat(input) || manager.checkExist(managerList, Manager::getEmail, input));
        manager.setEmail(input);
    }

    private void inputPassword(Manager manager) {
        String pass, confirm;
        do {
            System.out.print("Enter password: ");
            pass = scanner.nextLine();
            if (!manager.checkPass(pass))
                System.out.println("Invalid password. It must be at least 6 characters with no spaces.");
        } while (!manager.checkPass(pass));

        do {
            System.out.print("Confirm password: ");
            confirm = scanner.nextLine();
            if (!manager.confirmPass(confirm, pass))
                System.out.println("Passwords do not match. Please try again.");
        } while (!manager.confirmPass(confirm, pass));

        manager.setPassword(pass);
    }

    private void printManagerInfo(Manager manager) {
        System.out.println("Username: " + manager.getUserName());
        System.out.println("Full Name: " + manager.getFirstName() + " " + manager.getLastName());
        System.out.println("Phone: " + manager.getPhone());
        System.out.println("Email: " + manager.getEmail());
        System.out.println("Password" + manager.getPassword());
    }

    private void addManagerToList(Manager manager) throws IOException, Exception {
        managerList.add(manager);
        managerList.sort(Comparator.comparing(Manager::getUserName));
        fileOperations.addToManager(managerList);
    }

}

