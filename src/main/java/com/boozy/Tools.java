package com.boozy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tools {
    
/* Credits for: Ivan Stin 
 * https://stackoverflow.com/questions/8911356/whats-the-best-practice-to-round-a-float-to-2-decimals
*/
    public static float round(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }
    
    public static Float calcTime(String sx, String sy){

        try {

        /* date format "yyyy/MM/dd HH:mm:ss" */
        DateFormat date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");        

        /* extract the date from the string */
        Date dx = date_format.parse(sx);
        Date dy = date_format.parse(sy);

        /* subtract dates */
        long diff = dy.getTime() - dx.getTime();

        /* convert to float */
        Float hours = round(((float) diff / 3600000), 2);

        /* return */
        return hours;

        } catch (ParseException e) {
            System.out.println("message: ");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;

        }
        
    }
    
}
