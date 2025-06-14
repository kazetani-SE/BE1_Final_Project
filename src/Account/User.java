/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Account;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
/**
 *
 * @author User
 */

public class User extends Account implements Serializable{
    transient static final String deleted = "zzzzzzzzzzdeleted";
    private static final long serialVersionUID = 85836982L;
    
    public User() {
    }
    
    public boolean checkExist(List<User> user, Function<User, String> getter, String exist){
        return user.stream().anyMatch(u -> getter.apply(u).equals(exist));
    }

}
