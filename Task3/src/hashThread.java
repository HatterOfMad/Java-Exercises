
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tim
 */
class hashThread implements Runnable {

    Thread mythread;
    protected BlockingQueue<String> myQueue = null;
    ConcurrentHashMap<String, List<String>> myMap;

    public static String sortString(String inputString) {
        char[] stringCharArray = inputString.toCharArray();
        Arrays.sort(stringCharArray);
        return (new String(stringCharArray));
    }

    hashThread(BlockingQueue<String> lexQueue, ConcurrentHashMap<String, List<String>> lexMap) {
        this.myQueue = lexQueue;
        this.myMap = lexMap;
    }

    @Override
    public void run() {
        while (true) {
            //   System.out.println("Waiting for element");
            String currLine = "hi";
            //   System.out.println("Got element");
            String strKey = sortString(currLine);
            if (!myMap.containsKey(strKey)) {
                myMap.put(strKey, Collections.synchronizedList(new ArrayList<>()));
            }
            myMap.get(strKey).add(currLine);
            // System.out.print(currLine);
        }
    }
}
