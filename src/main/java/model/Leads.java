package model;

import java.util.ArrayList;

public class Leads {

    private ArrayList<Record> leads;

    public Leads(){
        leads = new ArrayList<>();
    }

    public Leads(ArrayList<Record> leads) {
        this.leads = leads;
    }

    public Iterable<Record> getLeads() {
        return leads;
    }

    public void setLeads(ArrayList<Record> leads) {
        this.leads = leads;
    }


}
