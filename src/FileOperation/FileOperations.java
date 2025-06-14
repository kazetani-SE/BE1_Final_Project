/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileOperation;

import Account.Manager;
import Account.User;
import java.io.*;
import java.util.*;
/**
 *
 * @author User
 */
public class FileOperations {
    private final String UserAccount = "User_account.dat";
    private final String ManagerAccount = "Manager_account.dat";
    
    public FileOperations() {
        try {
            
            File userFile = new File(UserAccount);
            if (!userFile.exists() || userFile.length() == 0) {
                List<User> emptyList = new ArrayList<>();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile));
                oos.writeObject(emptyList);
                oos.close();
            }

            File managerFile = new File(ManagerAccount);
            if (!managerFile.exists() || managerFile.length() == 0) {
                List<Manager> emptyList = new ArrayList<>();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(managerFile));
                oos.writeObject(emptyList);
                oos.close();
            }
        } catch (IOException e) {
            System.err.println("Error during file creation: " + e.getMessage());
        }
    }
    
    public void addToUser(List<User> user) throws IOException{
        ObjectOutputStream writeDate = new ObjectOutputStream(new FileOutputStream(UserAccount));
        writeDate.writeObject(user);
        writeDate.close();
    }
    
    public void addToManager(List<Manager> manager) throws IOException{
        ObjectOutputStream writeDate = new ObjectOutputStream(new FileOutputStream(ManagerAccount));
        writeDate.writeObject(manager);
        writeDate.close();
    }
    
    public List<User> readFromUser() throws Exception{        
        ObjectInputStream readDate = new ObjectInputStream(new FileInputStream(UserAccount));
        List<User> userList = (List<User>) readDate.readObject();
        return userList;
    }
    
    public List<Manager> readFromManager() throws Exception{        
        ObjectInputStream readDate = new ObjectInputStream(new FileInputStream(ManagerAccount));
        List<Manager> managerList = (List<Manager>) readDate.readObject();
        return managerList;
    }
}
