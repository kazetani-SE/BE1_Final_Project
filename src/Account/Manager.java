/*
 * To change manager license header, choose License Headers in Project Properties.
 * To change manager template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Account;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
//import FileOperation.FileOperations;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Scanner;
/**
 *
 * @author User
 */
public class Manager extends Account implements Serializable{
    private static final long serialVersionUID = 777182L;

    public boolean checkExist(List<Manager> manager, Function<Manager, String> getter, String exist){
        return manager.stream().anyMatch(u -> getter.apply(u).equals(exist));
    }
}
