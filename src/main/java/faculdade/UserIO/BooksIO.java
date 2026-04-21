package UserIO;

import java.util.Map;
import java.util.Scanner;

public class BooksIO{
    static Scanner scan = new Scanner(System.in);

    // TODO: Might be changed by a better algorithm or sth like lensKit
    static Map<Integer, String> interestsMap = Map.of(
        1, "Senhor dos Pastéis",
        2, "Jogos Ferozes",
        3, "Mistborn"
    );


    // i might ask about gender instead
    public static String askInterests(){
        System.out.println("wa' are your interests?");
        System.out.println("1. Senhor dos Pastéis");
        System.out.println("2. Jogos Ferozes");
        System.out.println("3. Mistborn");
        int resp = scan.nextInt();

        // handle errors
        if (resp > 3){
        }
        return interestsMap.get(resp);
    }

}
