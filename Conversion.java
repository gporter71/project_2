import java.util.ArrayList;
import java.util.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Conversion{
    static String word;
    static int lowestMinimum = 999;
    static int eDis;
    static int c;
    static int d;
    static int i;
    static int s;
    static StringBuffer body = new StringBuffer("");
    
    static int recN = 0;
    
    
    
    public static void main(String args[]) throws FileNotFoundException
    {
        System.out.println("test");
        
        if(args.length > 0){
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            readData(reader);
            
        }
        else{
            fillDefault();
        }
        
        
        
        ArrayList<Integer> weights = new ArrayList<>();
        ArrayList<String> weightStrings = new ArrayList<>();
        

        
        StringBuffer str = new StringBuffer("L");
        StringBuffer str2 = new StringBuffer("A");
        StringBuffer str0 = new StringBuffer("");
        StringBuffer strM = new StringBuffer("");
        

        
        lowestMinimum = getADistance(str,str2,str0,0,0);
        
        //System.out.println(str2.replace(2,3, "R"));
        
        lowestMinimum = minEditDistance(str,str2,strM,0,0,999,999,999,999);
        
        
        
        System.out.println("The minimum edit Distance is: " + lowestMinimum);
        
        //System.out.println(stringEquals(str,str2));
        System.out.println(str.charAt(0));
        
        
        
        
    }
    
    //read in input from a file
    public static void fillDefault(){
        //given default values given in assignment
        word = "lovely";
        eDis = 2;
        c = 0;
        d = 1;
        i = 1;
        s = 1;
        
        body.append("You have always been my love my love, for the love I. love is lovely as love itself.  Love? did you hang up?");

    }
    
    
    //The purpose of this method is to get an edit distance, not necesarily the smallest
    //str is the string i will convert,
    //str2 is the string i will try to convert str to
    //str0 is my string i will populate
    //currentI is the current index going through my strings
    //dis is what the edit distance is
    public static int getADistance(StringBuffer str, StringBuffer str2, StringBuffer str0, int currentI, int dis){
        //returns -1 if str is shorter
        // 0 if equal or 1 if str is shorter
        int longer = whichIsLonger(str, str2);
        
        //Three scenarios::: str is shorter ::: str2 is shorter ::: They are Equal
        if(longer == 0){
            while(currentI < str.length()){
                if(str.charAt(currentI) == str2.charAt(currentI)){
                    str0.append(str2.charAt(currentI));
                    currentI++;
                    dis = dis + c;
                }
                else{
                    str0.append(str2.charAt(currentI));
                    currentI++;
                    dis = dis + s;
                }
            }
        }
        else if(longer == -1){
            while(currentI < str.length()){
                if(str.charAt(currentI) == str2.charAt(currentI)){
                    str0.append(str2.charAt(currentI));
                    currentI++;
                    dis = dis + c;
                }
                else{
                    str0.append(str2.charAt(currentI));
                    currentI++;
                    dis = dis + s;
                }
            }
            while(currentI < str2.length()){
                str0.append(str2.charAt(currentI));
                str2.deleteCharAt(currentI);
                dis = dis + i;
            }
        }
        else{
            while(currentI < str2.length()){
                if(str.charAt(currentI) == str2.charAt(currentI)){
                    str0.append(str2.charAt(currentI));
                    currentI++;
                    dis = dis + c;
                }
                else{
                    str0.append(str2.charAt(currentI));
                    currentI++;
                    dis = dis + s;
                }
            }
            while(currentI < str.length()){
                    str.deleteCharAt(currentI);
                    dis = dis + d;
            }
        }
        return dis;
    }
        
     
    
    
    
    
    
    //find the minimum edit distance between two strings
    public static int minEditDistance(StringBuffer str, StringBuffer str2, StringBuffer str0, int currentIndex, int currentDis,int cb,int db, int ib, int sb){
        recN++;
        System.out.println("\nthis is recursion is number: " + recN + " with string: " + str0 + " at index: " + currentIndex);
        
        int dif = whichIsLonger(str, str2);
        
        int copyBranch = cb;
        int deleteBranch = db;
        int insertBranch = ib;
        int substituteBranch = sb;
        
        
        
        
        if(currentDis > lowestMinimum ){
            return -1;
        }
        if(stringEquals(str0,str2) && currentIndex > str2.length()){
            System.out.println("they are equal");
            return currentDis;
        }
        
        //copy the value
        if(currentIndex < str.length() && currentIndex < str2.length()){
            if(Character.toLowerCase(str.charAt(currentIndex)) == Character.toLowerCase(str2.charAt(currentIndex))){
                System.out.println("\nentering copy branch");
                System.out.println("current index: " + currentIndex + " str length " + str.length() + " str2 length " + str2.length());
                copyBranch = minEditDistance(str,str2,str0.append(str2.charAt(currentIndex)),currentIndex+1,currentDis + c,cb,db,ib,sb);
            }
            
        }
        //System.out.println(str + " " + str2 + " "  + str0 + " " + currentIndex + " " + currentDis);
        //delete the value
        if(str2.length() < str.length()){
            System.out.println("\nentering delete branch");
            StringBuffer strAux = new StringBuffer((str.deleteCharAt(currentIndex).toString()));
            deleteBranch = minEditDistance(strAux,str2,str0,currentIndex,currentDis + d,cb,db,ib,sb);
            
        }
        
        if(str0.length() <= str2.length()){
            //insert value
            System.out.println("\nentering insert branch");
            System.out.println("current index: " + currentIndex + " str length " + str.length() + " str2 length " + str2.length());
            if(currentIndex < str.length() && currentIndex < str2.length()){
                StringBuffer strAux = new StringBuffer((str0.insert(currentIndex,str2.charAt(currentIndex))).toString());
                insertBranch = minEditDistance(str,str2,strAux,currentIndex + 1, currentDis + i,cb,db,ib,sb);
            }
        }
        System.out.println(currentIndex);
        System.out.println(str2.length());
        if(currentIndex < str2.length()){
            //substitute value
            System.out.println("entering substitute branch");
            StringBuffer strAux = new StringBuffer((str0.replace(currentIndex,currentIndex+1,Character.toString((str2.charAt(currentIndex))))).toString());
            substituteBranch = minEditDistance(str,str2,strAux,currentIndex + 1, currentDis + s,cb,db,ib,sb);
        }
        System.out.println("find the smallest among: " + copyBranch + " " + deleteBranch + " " + insertBranch + " " + substituteBranch);
        int x = findSmallestIgnoreInvalid(copyBranch,deleteBranch,insertBranch,substituteBranch);
        
        
        return x;
        
    }
    
    
    public static int whichIsLonger(StringBuffer str, StringBuffer str2){
        int x = -2;
        if(str.length() < str2.length()){
            x = -1;
        }
        if(str.length() == str2.length()){
            x = 0;
        }
        if(str.length() > str2.length()){
            x = 1;
        }
        return x;
    }
           
    public static boolean stringEquals(StringBuffer str, StringBuffer str2){
        
        //System.out.println(str + " " + str2);
    
            int x = whichIsLonger(str,str2);
            if(x != 0){
                return false;
            }
            for(int i =0; i<str.length();i++){
                if(Character.toLowerCase(str.charAt(i)) != Character.toLowerCase(str2.charAt(i))){
                    return false;
                }
            }
            return true;
    }
    
    public static int findSmallestIgnoreInvalid(int a, int b, int c, int d){
        ArrayList<Integer> distances = new ArrayList<>();
        distances.add(a);
        distances.add(b);
        distances.add(c);
        distances.add(d);
        Collections.sort(distances);
        int i =0;
        while(i < distances.size()){
            if (distances.get(i) == -1 ||distances.get(i) == 0){
                i++;
            }
            else{
                break;
            }
            
        }
        return distances.get(i);
    }
    
    public static void readData(Scanner reader){
        
        try{
            word = reader.nextLine();
            eDis = reader.nextInt();
            c = reader.nextInt();
            d = reader.nextInt();
            i = reader.nextInt();
            s = reader.nextInt();
            System.out.println(word);
            System.out.println(eDis);
            while(reader.hasNext()){
                //System.out.println(reader.next());
                body.append(reader.next());
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return;
    
    }
    
}
