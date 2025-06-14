/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FakeData;
import java.util.Random;
/**
 *
 * @author User
 */
public class FakeData {
    private Random rand = new Random();
    
    private String[] userName = {"Congchuabongbong", "Hiepsymongmo", "Hoangtuech", 
                                "Sieunhanbanhmi", "Echnuplum", "Anhdepzai", "Lukglobal", 
                                "!ToiyeuFPT", "Wibuchua", "F-Codeisdabest", "Domdom", 
                                "Mitomhaitrung", "Hoahauthanthien", "Tongtaibadao", 
                                "Kecapmattrang", "Cogiaothao"};
    
    private String[] firstName = { "An", "Binh", "Giang", "Huy", "Khoa", "Lam", "Minh",
                                  "Nhi","Bao An", "Cong Hieu", "Dinh Loc", "Hoang Anh", 
                                  "Ngoc Linh", "Minh Duc", "Do Thanh Tung", "Hoang Tuan Khang"};
    
    private String[] lastName = {"Nguyen", "Tran", "Le", "Pham", "Huynh", "Hoang", "Vo"};
    
    public String fakeUserName(){
        int userNamePosi;
        do{
            userNamePosi = rand.nextInt(16);
        }while(userName[userNamePosi].isEmpty());
        String temp = userName[userNamePosi];
        userName[userNamePosi] = "";
        return temp;
    }
    
    public String fakeFistName(){
        return firstName[rand.nextInt(16)];
    }
    
    public String fakeLastName(){
        return lastName[rand.nextInt(16)%7];
    }
    
    public String fakeEmail(String fakeLastName, String fakeFistName){
        String subName = fakeLastName + " " + fakeFistName;
        String[] subMail = subName.split(" ");
        String fakeMail = subMail[subMail.length - 1];
        for(int i = 0; i < subMail.length - 1; i++){
            fakeMail += subMail[i].charAt(0);
        }
        fakeMail = fakeMail + "@gmail.com";
        return fakeMail;
    }
    
    public String fakePhone(){
        String phone;
        int randPhone = (rand.nextInt(9) + 1)*100000000 + rand.nextInt(100000000);
        phone = Integer.toString(randPhone);
        phone = "0" + phone;
        return phone;
    }
    
    public String fakePass(){
        int fakePass = rand.nextInt(1000000);
        return Integer.toString(fakePass);
    }
}
