package model;

import service.RecordDiffLogger;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record implements Comparable<Record> {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Date entryDate;
    private String _id;
    private int recordNumber;

    public Record(String _id, String email, String firstName, String lastName, String address, Date entryDate, int recordNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.entryDate = entryDate;
        this._id = _id;
        this.recordNumber = recordNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public String get_id() {
        return _id;
    }

    public int getRecordNumber() {
        return recordNumber;
    }


    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public int compareTo(Record o){
        int isDateGreater = this.entryDate.compareTo(o.entryDate);
        boolean hasHigherRecordNumber = this.recordNumber > o.recordNumber;

        if(isDateGreater > 0 || (isDateGreater == 0 && hasHigherRecordNumber)){
            return 1;
        }

        return -1;
    }

    public int compareAndAudit(Record o) {

        int res = this.compareTo(o);

        if(res > 0){
            RecordDiffLogger.logFieldDifferences(this, o);
        }else{
            RecordDiffLogger.logFieldDifferences(o, this);
        }

        return res;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Record)
        {
            Record c = (Record) o;
            SimpleDateFormat sdt = new SimpleDateFormat("EEEEE MMMMM yyyy HH:mm:ss");
            Date thisTime = null, cTime = null;
            try {
                thisTime = sdt.parse(this.entryDate.toString());
                cTime = sdt.parse(c.entryDate.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return this._id.equals(c._id) && this.address.equals(c.address) && thisTime.equals(cTime) && this.firstName.equals(c.firstName) && this.lastName.equals(c.lastName) && this.email.equalsIgnoreCase(c.email);
        }
        return false;
    }

    public String populateFieldDifferences(Record duplicate) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder("");
        for(Field f : Record.class.getDeclaredFields()){
            if(f.getName().equals("recordNumber")) {
                continue;
            }
            Object dValue = f.get(duplicate);
            Object rValue = f.get(this);
            if(!dValue.equals(rValue)) {
                sb.append("\"");
                sb.append(f.getName());
                sb.append("\" : \"");
                sb.append(dValue);
                sb.append(" => ");
                sb.append(rValue);
                sb.append("\",");
            }
        }
        return sb.toString();
    }
}
