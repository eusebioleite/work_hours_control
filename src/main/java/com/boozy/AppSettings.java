package com.boozy;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.boozy.tables.Job;

public class AppSettings {
    
    private static String jdbc_string = 
    String.format(
        "jdbc:sqlite:%s\\work_hours_control\\work_hours_control.db", System.getProperty("user.home")
    );

    /* create initial settings */
    public static void create_db() {

        /* create db folder if not exister */
        new File(System.getProperty("user.home") + "/work_hours_control").mkdirs();
        
        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* create tables if not exist */
            Statement statement = connection.createStatement();

            /* rdp */
            statement.execute("CREATE TABLE IF NOT EXISTS job (id INTEGER PRIMARY KEY AUTOINCREMENT,entry TEXT,exit TEXT,description TEXT,hours REAL,total REAL)");

            statement.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    public static ArrayList<Job> get_job(){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response body */            
            ArrayList<Job> response = new ArrayList<Job>();

            /* get all rdp query */
            PreparedStatement prepared_statement = connection.prepareStatement("select * from job");
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {
                //TEXT as ISO8601 strings ("YYYY-MM-DD HH:MM:SS.SSS")
                /* assign data to response body */
                Job job = new Job(
                    result_set.getInt("id"), 
                    result_set.getString("entry"), 
                    result_set.getString("exit"), 
                    result_set.getString("description"), 
                    result_set.getFloat("hours"),
                    result_set.getFloat("total")
                );
                response.add(job);

            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }

    }

    public static void post_job(String entry, String exit, String description, Float hours, Float total) {

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* create tables if not exist */
            Statement statement = connection.createStatement();

            /* rdp */
            float hours_i = (float) hours;
            float total_i = (float) total;
            statement.execute(
                String.format(
                    "insert into job(entry, exit, description, hours, total) values (\"%s\", \"%s\", \"%s\", %s, %s)",
                    entry, exit, description, (int) hours_i, (int)total_i
                )
            );

            statement.close();

        } catch (Exception e) {
            System.out.println("sqlite error");
            e.printStackTrace();

        }

    }

    public static void delete_job(Integer id) {

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* create tables if not exist */
            Statement statement = connection.createStatement();

            /* rdp */
            statement.execute(String.format("delete from job where id=%d", id));

            statement.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void put_job(Integer id, String entry, String exit, String description, Float hours, Float total) {
        try(Connection connection = DriverManager.getConnection(jdbc_string)) {
            
            /* create tables if not exist */
            Statement statement = connection.createStatement();

            /* rdp */
            System.out.println(String.format(
                    "update job set entry=\"%s\", exit=\"%s\", description=\"%s\", hours=%s, total=%s where id=%s",
                    entry, exit, description, hours, total, id
                ));
            statement.execute(
                String.format(
                    "update job set entry=\"%s\", exit=\"%s\", description=\"%s\", hours=%s, total=%s where id=%s",
                    entry, exit, description, hours, total, id
                )
            );

            statement.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static ArrayList<Job> full_text_search(String s){

        try(Connection connection = DriverManager.getConnection(jdbc_string)) {

            /* response body */            
            ArrayList<Job> response = new ArrayList<Job>();

            /* get all rdp query */
            PreparedStatement prepared_statement = connection.prepareStatement("select * from job where description=\"%%s%\"");
            ResultSet result_set = prepared_statement.executeQuery();
            
            while (result_set.next()) {
                //TEXT as ISO8601 strings ("YYYY-MM-DD HH:MM:SS.SSS")
                /* assign data to response body */
                Job job = new Job(
                    result_set.getInt("id"), 
                    result_set.getString("entry"), 
                    result_set.getString("exit"), 
                    result_set.getString("description"), 
                    result_set.getFloat("hours"),
                    result_set.getFloat("total")
                );
                response.add(job);

            }

            /* close connection */
            result_set.close();
            prepared_statement.close();        

            /* return data */
            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;
        }

    } 
    
}
