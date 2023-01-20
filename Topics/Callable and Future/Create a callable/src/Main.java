import java.util.Scanner;
import java.util.concurrent.Callable;

class CallableUtil {
    public static Callable<String> getCallable() {
        // implement method
        Scanner sc =new Scanner(System.in);
        String inp = sc.nextLine();

        Callable<String> o = () -> {
            return inp;
        };

        return o;
    }
}