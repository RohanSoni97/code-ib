/*
The count-and-say sequence is the sequence of integers beginning as follows:

1, 11, 21, 1211, 111221, ...
1 is read off as one 1 or 11.
11 is read off as two 1s or 21.

21 is read off as one 2, then one 1 or 1211.

Given an integer n, generate the nth sequence.

Note: The sequence of integers will be represented as a string.

Example:

if n = 2,
the sequence is 11.
 */
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
