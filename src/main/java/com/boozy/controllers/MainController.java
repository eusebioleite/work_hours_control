package com.boozy.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.boozy.AppSettings;
import com.boozy.Tools;
import com.boozy.tables.Job;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MainController {
    
    /* search bar */
    @FXML
    private TextField search_bar = new TextField();

    /* buttons */
    @FXML
    private Button start_btn = new Button("start");

    @FXML
    private Button stop_btn = new Button("stop");

    @FXML
    private Button del_btn = new Button("del");

    /* combo box */
    @FXML
    private ComboBox<String> filter = new ComboBox<String>();
    
    /* table */
    @FXML
    private TableView<Job> data_table = new TableView<Job>();

    @FXML
    private TableColumn<Job, Integer> id_column = new TableColumn<>();

    @FXML
    private TableColumn<Job, String> entry_column = new TableColumn<>();

    @FXML
    private TableColumn<Job, String> exit_column = new TableColumn<>();

    @FXML
    private TableColumn<Job, String> description_column = new TableColumn<>();

    @FXML
    private TableColumn<Job, Float> hours_column = new TableColumn<>();

    @FXML
    private TableColumn<Job, Float> total_column = new TableColumn<>();

    @FXML
    private void initialize(){
        
        /* set icons */
        start_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.CLOCK_ALT));
        stop_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.BAN));
        del_btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TIMES));

        /* load table */
        loadTable();

        /* load combo box */
        loadFilter();

        /* search_bar listener */
        search_bar.textProperty().addListener((object, oldValue, newValue) -> {
            if (newValue.length() > oldValue.length()) {

                /* clear table */
                data_table.getItems().clear();
                
                /* read data from db and insert into tableview */
                ArrayList<Job> job = AppSettings.full_text_search(object.getValue());

                for(Job job_row : job){

                    data_table.getItems().add(
                        new Job(
                            job_row.getId(), 
                            job_row.getEntry(), 
                            job_row.getExit(), 
                            job_row.getDescription(), 
                            job_row.getHours(),
                            job_row.getTotal()
                        )
                    );

                }

            } else {

                /* clear table */
                data_table.getItems().clear();
                
                /* read data from db and insert into tableview */
                ArrayList<Job> job = AppSettings.full_text_search(object.getValue());

                for(Job job_row : job){

                    data_table.getItems().add(
                        new Job(
                            job_row.getId(), 
                            job_row.getEntry(), 
                            job_row.getExit(), 
                            job_row.getDescription(), 
                            job_row.getHours(),
                            job_row.getTotal()
                        )
                    );

                }
                
            }

        });
    }

    private void loadFilter(){

        filter.getItems().add("Jan");
        filter.getItems().add("Feb");
        filter.getItems().add("Mar");
        filter.getItems().add("Apr");
        filter.getItems().add("May");
        filter.getItems().add("Jun");
        filter.getItems().add("Jul");
        filter.getItems().add("Aug");
        filter.getItems().add("Sep");
        filter.getItems().add("Oct");
        filter.getItems().add("Nov");
        filter.getItems().add("Dec");

    }

    @FXML
    private void actionFilter(ActionEvent e){
        
        switch(filter.getSelectionModel().getSelectedItem().toString()){
            
            case "Jan":
                loadTableWhereDate(0);
                break;

            case "Feb":
                loadTableWhereDate(1);
                break;

            case "Mar":
                loadTableWhereDate(2);
                break;

            case "Apr":
                loadTableWhereDate(3);
                break;

            case "May":
                loadTableWhereDate(4);
                break;

            case "Jun":
                loadTableWhereDate(5);
                break;

            case "Jul":
                loadTableWhereDate(6);
                break;

            case "Aug":
                loadTableWhereDate(7);
                break;

            case "Sep":
                loadTableWhereDate(8);
                break;

            case "Oct":
                loadTableWhereDate(9);
                break;

            case "Nov":
                loadTableWhereDate(10);
                break;

            case "Dec":
                loadTableWhereDate(11);
                break;
        }

    }

    private void loadTableWhereDate(Integer month_id){
        
        try {
            /* clear table before filtering */
            data_table.getItems().clear();
            /* data format */
           DateFormat date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
            /* read data from db and insert into tableview */
            ArrayList<Job> job = AppSettings.get_job();

            for(Job job_row : job){
                
                /* initialize date */
                Calendar c = Calendar.getInstance();
                c.setTime(date_format.parse(job_row.getEntry()));

                /* filter based on month */
                if(c.get(Calendar.MONTH) == month_id){

                    data_table.getItems().add(
                    new Job(
                        job_row.getId(), 
                        job_row.getEntry(), 
                        job_row.getExit(), 
                        job_row.getDescription(), 
                        job_row.getHours(),
                        job_row.getTotal()
                    )
                );

                }

            } 

        } catch (Exception e) {

            e.printStackTrace();
            
        }
        
    }
    /* CRUD */
    @FXML
    private void actionStart(){

        /* date format "yyyy/MM/dd HH:mm:ss" */
        DateFormat date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        /* Insert empty row */
        AppSettings.post_job(date_format.format(Calendar.getInstance().getTime()), "", "", 0f, 0f);
        
        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Job> job = AppSettings.get_job();

        for(Job job_row : job){

            data_table.getItems().add(
                new Job(
                    job_row.getId(), 
                    job_row.getEntry(), 
                    job_row.getExit(), 
                    job_row.getDescription(), 
                    job_row.getHours(),
                    job_row.getTotal()
                )
            );

        }
    }

    @FXML
    private void actionStop(){
        
        /* date format "yyyy/MM/dd HH:mm:ss" */
        DateFormat date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        /* update selected row */
        AppSettings.put_job(
            data_table.getSelectionModel().getSelectedItem().getId(),
            data_table.getSelectionModel().getSelectedItem().getEntry(),
            date_format.format(Calendar.getInstance().getTime()),
            data_table.getSelectionModel().getSelectedItem().getDescription(),
            Tools.calcTime(data_table.getSelectionModel().getSelectedItem().getEntry(), date_format.format(Calendar.getInstance().getTime())),
            (Tools.calcTime(data_table.getSelectionModel().getSelectedItem().getEntry(), date_format.format(Calendar.getInstance().getTime())) * 8)
        );
        
        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Job> job = AppSettings.get_job();

        for(Job job_row : job){

            data_table.getItems().add(
                new Job(
                    job_row.getId(), 
                    job_row.getEntry(), 
                    job_row.getExit(), 
                    job_row.getDescription(), 
                    job_row.getHours(),
                    job_row.getTotal()
                )
            );

        }
    }

    @FXML
    private void actionDel(){

        /* delete record */
        AppSettings.delete_job(data_table.getSelectionModel().getSelectedItem().getId());

        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Job> job = AppSettings.get_job();

        for(Job job_row : job){

            data_table.getItems().add(
                new Job(
                    job_row.getId(), 
                    job_row.getEntry(), 
                    job_row.getExit(), 
                    job_row.getDescription(), 
                    job_row.getHours(),
                    job_row.getTotal()
                )
            );

        }

    }

    /* Table */
    @FXML
    public void onEditCommitData_Table(CellEditEvent<?,?> event){

        /* update selected row */
        AppSettings.put_job(
            data_table.getSelectionModel().getSelectedItem().getId(),
            data_table.getSelectionModel().getSelectedItem().getEntry(),
            data_table.getSelectionModel().getSelectedItem().getExit(),
            event.getNewValue().toString(),
            data_table.getSelectionModel().getSelectedItem().getHours(),
            data_table.getSelectionModel().getSelectedItem().getTotal()
        );
        
        /* clear data_table */
        data_table.getItems().clear();

        /* read data from db and insert into tableview */
        ArrayList<Job> job = AppSettings.get_job();

        for(Job job_row : job){

            data_table.getItems().add(
                new Job(
                    job_row.getId(), 
                    job_row.getEntry(), 
                    job_row.getExit(), 
                    job_row.getDescription(), 
                    job_row.getHours(),
                    job_row.getTotal()
                )
            );

        }
    }

    private void loadTable(){

        /* load id column */
        id_column.setCellValueFactory(new PropertyValueFactory<>("Id"));
        id_column.setCellFactory(TextFieldTableCell.<Job, Integer>forTableColumn(new IntegerStringConverter()));

        /* load entry column */
        entry_column.setCellValueFactory(new PropertyValueFactory<>("Entry"));
        entry_column.setCellFactory(TextFieldTableCell.<Job>forTableColumn());

        /* load exit column */
        exit_column.setCellValueFactory(new PropertyValueFactory<>("Exit"));
        exit_column.setCellFactory(TextFieldTableCell.<Job>forTableColumn());

        /* load description column */
        description_column.setCellValueFactory(new PropertyValueFactory<>("Description"));
        description_column.setCellFactory(TextFieldTableCell.<Job>forTableColumn());

        /* load hours column */
        hours_column.setCellValueFactory(new PropertyValueFactory<>("Hours"));
        hours_column.setCellFactory(TextFieldTableCell.<Job, Float>forTableColumn(new FloatStringConverter()));

        /* load total column */
        total_column.setCellValueFactory(new PropertyValueFactory<>("Total"));
        total_column.setCellFactory(TextFieldTableCell.<Job, Float>forTableColumn(new FloatStringConverter()));

        /* read data from db and insert into tableview */
        ArrayList<Job> job = AppSettings.get_job();

        for(Job job_row : job){

            data_table.getItems().add(
                new Job(
                    job_row.getId(), 
                    job_row.getEntry(), 
                    job_row.getExit(), 
                    job_row.getDescription(), 
                    job_row.getHours(),
                    job_row.getTotal()
                )
            );

        }
    }

}
