package app.model;

import java.sql.Date;

public class Invoice {
        private long invoiceid;
        private long personid;
        private long partnetid;
        private int affiliationdate;
        private float totalvalue;
        private String state;

        public Invoice (){}

        public long getinvoiceid (){
        return invoiceid;
        }

        public long getpersonid () {
        return personid;
        }

        public long getpartnetid () {
        return partnetid;
        }
        public int getaffiliationdate () {
        return affiliationdate;
        }

        public float gettotalvalue () {
        return totalvalue;
        }

        public String getstate () {
        return state;
        }

        public void setinvoiceid (long invoiceid) {
        this.invoiceid=invoiceid;
        }

        public void setpersonid (long personid) {
        this.personid=personid;
        }

        public void setpartnetid (long partnetid) {
        this.partnetid=partnetid;
        }

        public void setmaffiliationdate (int affiliationdate) {
        this.affiliationdate=affiliationdate;
        }

        public void settotalvalue (float totalvalue) {
        this.totalvalue=totalvalue;
        }

        public void setstate (String state) {
        this.state=state;
        }
        }
     
