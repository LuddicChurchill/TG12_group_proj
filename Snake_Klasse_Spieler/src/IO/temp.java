package IO;

import java.util.ArrayList;
import java.util.Scanner;

public class temp {
    public static void main(String args[]) {
        ArrayList<Character> name = new ArrayList<>();

        name.add('a');
        name.add('b');
        name.add('c');
        name.add('d');

        name.remove(3);

        System.out.println(name.get(3));
    }
}
