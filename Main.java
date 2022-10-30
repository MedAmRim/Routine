import java.util.Arrays;
import java.util.List;
import java.util.stream.*;


public class Main {
    public static void main(String [] args){
        List<String> list = Arrays.asList("ab1", "abc5", "abc3");

       boolean b = list.stream().anyMatch(str -> str.contains("abc6"));
        System.out.println(b);
       // list.stream().skip(2).forEach(System.out::println);
        List<String> l ;

        /*long size = list.stream().skip(1).map(element -> element.substring(0, 4))
        .sorted().count();
        System.out.println(size);*/

    

       /*boolean b = list.stream().skip(2).allMatch(str -> str.equals("abc3"));
       System.out.println(b);  */  

        /*int counter = 0;
        Stream<String> stream = list.stream().filter(element -> {
            wasCalled();
            return element.contains("2");*/
};
    }

