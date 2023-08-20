package register;

import utils.Utils;

public class RegisterApplication {

    public static void main(String[] args) {
        Utils u = new Utils();
        System.out.println("The secret register string is: " + u.helper());
    }

}
