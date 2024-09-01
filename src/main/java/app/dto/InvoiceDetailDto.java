package app.dto;

public class InvoiceDetailDto{
        private long detailid;
        private long invoiceid;
        private int itemnumber;
        private float itemvalue;
        private String concept;

        public InvoiceDetailDto (){}

        public long getdetailid () {
        return detailid;
        }

        public long getinvoiceid () {
        return invoiceid;
        }

        public int getitemnumber () {
        return itemnumber;
        }

        public float getitemvalue () {
        return itemvalue;
        }

        public String getconcept () {
        return concept;
        }

        public void setdetailid (long detailid) {
        this.detailid=detailid;
        }

        public void setinvoiceid (long invoiceid) {
        this.invoiceid=invoiceid;
        }

        public void setitemnumber (int itemnumber) {
        this.itemnumber=itemnumber;
        }

        public void setitemvalue (float itemvalue) {
        this.itemvalue=itemvalue;
        }

        public void setcaoncept (String concept) {
        this.concept=concept;
        }
        }  

