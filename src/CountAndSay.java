import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CountAndSay {
    public String countAndSay(int A) {
        if(A == 1) {
            return Integer.toString(1);
        }
        String prev = "1";
        for(int i=1; i<A; i++) {
            char c = prev.charAt(0);
            int count = 1;
            StringBuffer sb = new StringBuffer();
            for(int j=1; j<prev.length(); j++) {
                if(prev.charAt(j) == c) {
                    count++;
                }
                else {
                    sb.append(Integer.toString(count));
                    sb.append(Character.toString(c));
                    count = 1;
                    c = prev.charAt(j);
                }
            }
            sb.append(Integer.toString(count));
            sb.append(Character.toString(c));
            prev = sb.toString();
        }
        return prev;
    }
    public static void main(String[] args) throws Exception {
        CountAndSay countAndSay=new CountAndSay();
       // countAndSay.countAndSay(4);
        System.out.println(countAndSay.countAndSay(4));

    }
}
