package decorator.version2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


/**
 * Created by tisong on 1/22/17.
 */
public class IOTest {

    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader(args[0]));
    }
}
