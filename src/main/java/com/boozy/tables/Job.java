package com.boozy.tables;

public class Job {
    
    private Integer id;
    private String entry;
    private String exit;
    private String description;
    private Float hours;
    private Float total;

    public Job(Integer id, String entry, String exit, String description, Float hours, Float total) {

        this.id = id;
        this.entry = entry;
        this.exit = exit;
        this.description = description;
        this.hours = hours;
        this.total = total;
        
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the entry
     */
    public String getEntry() {
        return entry;
    }

    /**
     * @param entry the entry to set
     */
    public void setEntry(String entry) {
        this.entry = entry;
    }

    /**
     * @return the exit
     */
    public String getExit() {
        return exit;
    }

    /**
     * @param exit the exit to set
     */
    public void setExit(String exit) {
        this.exit = exit;
    }

    /**
     * @return the hours
     */
    public Float getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(Float hours) {
        this.hours = hours;
    }

    /**
     * @return the total
     */
    public Float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Float total) {
        this.total = total;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

   

}
