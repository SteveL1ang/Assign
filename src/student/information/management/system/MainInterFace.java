/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.information.management.system;

/**
 *
 * @author 梁豪森
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

public class MainInterFace {
    private static final String USER_FILE = "users.txt";
    private static UserDataManager userDataManager = new UserDataManager();

    public static void main(String[] args)
    {
        ArrayList<User> list = new ArrayList<>();
        try {
            list = userDataManager.readFromFile(USER_FILE);
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
        Scanner sc = new Scanner(System.in);

        loop:
        while (true) {
            System.out.println("Welcome to Student Information Management System");
            System.out.println("Press 1 : Login, Press 2 : Register, press 3 : Forget Password, Press 4 : Exit the Program");
            String choose = sc.next();

            switch (choose) {
                case "1":
                    login(list);
                    break;

                case "2":
                    register(list);
                    break;

                case "3":
                    forgetPassword(list);
                    break;

                case "4":
                    try {
                        userDataManager.writeToFile(USER_FILE, list);
                    } catch (IOException e) {
                        System.out.println("Error saving users to file: " + e.getMessage());
                    }
                    System.out.println("Thanks and bye!");
                    break loop;

                default:
                    System.out.println("We dont have this choose");

            }
        }

    }

    private static void forgetPassword(ArrayList<User> list) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a username: ");
        String username=scanner.next();
        boolean flag=contains(list,username);

        if(!flag){
            System.out.println("Not a register username, please register first");
            return;
        }
        //though index find uer information
            int index=findIndex(list,username);
            User user=list.get(index);
        
        System.out.println("Please enter your phone number: ");
        String phoneNumber = scanner.next();
        
        if (!user.getPhoneNumber().equals(phoneNumber)) {
        System.out.println("Phone number not found, please register first");
        return;
    }


        // all information correct
        String password;
        while (true) {
            System.out.println("Please enter a new password:");
            password = scanner.next();
            System.out.println("Please enter the password again:");
            String againPassword = scanner.next();
            if(password.equals(againPassword)){
                System.out.println("edit successfully");
                break;
            }else{
                System.out.println("The password you write is not same, please write again:");
                continue;
            }
        }
        user.setPassword(password);

    }

    private static int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user=list.get(i);
            if(user.getUserName().equals(username)){
                return i;
            }
        }
        return -1;
    }

    private static void register(ArrayList<User> list) {
        // put user information to array list

        Scanner scanner = new Scanner(System.in);

        //username set
        String userName;
        while (true) {
            System.out.println("Please enter a user name (The username needs to be between 3-15 units and contain at least one letter):");
            userName = scanner.next();

            boolean flag = CheckUserName(userName);
            if (!flag) {
                System.out.println("not a valid user name need enter again");
                continue;
            }
            //check the username if used,collection if already have this username
            boolean flag2 = contains(list, userName);
            if (flag2) {
                System.out.println("This username exist, need enter again");
            } else {
                System.out.println("Username " + userName + " set success");
                break;
            }

        }

        //password set
        String password;
        while (true) {
            System.out.println("Please enter a password:");
            password = scanner.next();
            System.out.println("Recheck the password");
            String Checkpassword = scanner.next();
            if (!password.equals(Checkpassword)) {
                System.out.println("Password not same, please enter again");
                
            } else {
                System.out.println("Password set success");
                break;
            }
        }


        //set phone number
        String PhoneNumber;
        while (true) {
            System.out.println("Please enter a phone number(Start with 0 and have 9 numbers)");
            PhoneNumber= scanner.next();
            boolean flag=CheckPhoneNumber(PhoneNumber);
            if(flag){
                System.out.println("Phone number load success");
                break;
            }else{
                System.out.println("Wrong phone number,please enter again");
                
            }
        }


        User user = new User(userName,password,PhoneNumber);
        list.add(user);
        System.out.println("register success!");

       //Traverse the collection: This method was used to show the list of users to let people know that the new user is setting 
       //but it will also shows the old users' infromation so I delete it, more private
       
       // ptintList(list);  
      
    }

    /*private static void ptintList(ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            User u=list.get(i);
            System.out.println(u.getUserName()+", "+u.getPassword()+", "+u.getPhoneNumber());
        }
    }*/

    private static boolean CheckPhoneNumber(String phoneNumber) {
        if(phoneNumber.length()!=9){
            return false;
        }
        boolean b=phoneNumber.startsWith("0");
        if(!b){
            return false;
        }
        for (int i = 0; i < phoneNumber.length(); i++) {
            char a=phoneNumber.charAt(i);
            if(a<'0'||a>'9'){
                return false;
            }
        }
        return true;
    }




    private static boolean CheckUserName(String userName) {
        int len = userName.length();
        if (len < 3 || len > 15) {
            return false;
        }
        //when code comes here means the username is length is qualified
        // continue check
        for (int i = 0; i < userName.length(); i++) {
            char s = userName.charAt(i);
            if (!((s <= 'z' && s >= 'a') || (s <= 'Z' && s >= 'A') || (s <= '9' && s >= '0'))) {
                return false;
            }
        }
        //when code comes here means the username is content is qualified
        //but not be all number
        int count = 0;
        for (int i = 0; i < userName.length(); i++) {
            char s = userName.charAt(i);
            if ((s <= 'z' && s >= 'a') || (s <= 'Z' && s >= 'A')) {
                count++;
                break;//improve the efficiency
            }
        }
        return count > 0;
    }

    private static void login(ArrayList<User> list) {
        Scanner sc= new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            System.out.println("Please enter a username:");
            String username=sc.next();
            //check if username exist
            boolean flag=contains(list,username);
            if(!flag){
                System.out.println("Username "+username+" not exist,please register first.");
                return;
            }

            System.out.println("Please enter the Password:");
            String password = sc.next();

            while (true) {
                String rigHtCaptcha=getCaptcha();
                System.out.println("The captcha is "+rigHtCaptcha);
                System.out.println("Please enter the captcha:");
                String captcha = sc.next();
                if(captcha.equalsIgnoreCase(rigHtCaptcha)){
                    System.out.println("correct captcha");
                    break;
                }else{
                    System.out.println("Wrong captcha, please enter the new captcha: ");
                }
            }

            User userIfo = new User(username,password,null);
            boolean result =checkUserIfo(list,userIfo);
            if(result){
                System.out.println("Successful login");
                //user student information system
                StudentSystem ss = new StudentSystem();
                ss.StartSystem();
                break;
            }else{

                if(i== 2){
                    System.out.println("The account "+username+" be locked");
                    return;
                }else {
                    System.out.println("Wrong username or password, you have "+(2-i)+" times left");
                }
            }
        }

    }

    private static boolean checkUserIfo(ArrayList<User> list, User userIfo) {
        for (int i = 0; i < list.size(); i++) {
            User user=list.get(i);
            if(user.getPassword().equals(userIfo.getPassword())&& user.getUserName().equals(userIfo.getUserName())){
                return true;
            }
        }
        return false;
    }

    private static boolean contains(ArrayList<User> list, String userName) {
        //check if the username already exist
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            String username = user.getUserName();
            if (username.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public static String getCaptcha(){
        //create a collection contains all capital letter and small letter
        ArrayList<Character> list= new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+1));
        }
        // randomly get 4 characters
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int index= r.nextInt(list.size());
            char c=list.get(index);
            stringBuilder.append(c);
        }
        //randomly get a number
        int a =r.nextInt(10);
        stringBuilder.append(a);

        //Shuffle the string content
        char[]arr=stringBuilder.toString().toCharArray();
        int index=r.nextInt(arr.length);
        char temp = arr[index];
        arr[index]=arr[arr.length-1];
        arr[arr.length-1]=temp;
        return new String(arr);
    }
    
}
