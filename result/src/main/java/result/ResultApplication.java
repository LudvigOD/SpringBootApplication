package result;

import utils.Utils;

public class ResultApplication {

    public static void main(String[] args) {
        Utils u = new Utils();
        String h = u.helper();

        System.out.println("The helper string is: " + h);
    }

}
