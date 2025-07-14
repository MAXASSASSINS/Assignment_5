package org.example;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main {
    private static long getTimeStamp(){
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    public static void main(String[] args) {
        NameTimestamp nameTimestamp = new NameTimestamp();
        RandomName randomName = new RandomName();

        try{
            while(true){
                String name = randomName.getRandomName();
                long timeStamp = getTimeStamp();

                nameTimestamp.update(name, timeStamp);
                System.out.println(name + "\t" + timeStamp);
                Thread.sleep(30 * 1000);
            }
        }
        catch (InterruptedException e){
            System.out.println("Interrupted");
        }
    }
}