import java.util.Scanner;

public class Parser {

    static public int i = 0;
    static public char[] entrySeq;

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        entrySeq = scanner.next().toCharArray();

        if(S() == true && entrySeq.length <= i){
            System.out.println("");
            System.out.println("DA");
        }
        else {
            System.out.println("");
            System.out.println("NE");
        }
    }
    public static boolean S(){
        System.out.print("S");
        if(entrySeq[i] == 'a'){
            i++;
            if(A() == false)return false;
            if(B() == false)return false;
        }
        else if (entrySeq[i] == 'b'){
            i++;
            if(B() == false)return false;
            if(A() == false)return false;
        }
        return true;
    }

    public static  boolean A(){
        System.out.print("A");
        if(entrySeq.length <= i) return false;
        if(entrySeq[i] == 'b'){
            i++;
            if(C() == false) return false;
        }
        else if (entrySeq[i] == 'a'){
            i++;
            return true;
        }  else  {
            return false;
        }
        return true;
    }

    public static  boolean B(){
        System.out.print("B");
        if(entrySeq.length > i){
            if(entrySeq[i] == 'c'){
                i++;
                if(entrySeq[i] == 'c'){
                    i++;
                    if(S() == false) return false;
                    if(entrySeq[i] == 'b'){
                        i++;
                        if(entrySeq[i] == 'c'){
                            i++;
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static  boolean C(){
        System.out.print("C");
        if (A() == false)return false;
        if (A() == false)return false;
        return true;
    }
}