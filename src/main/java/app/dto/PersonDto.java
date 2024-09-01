package app.dto;

public class PersonDto {
        private long personid;
        private long document;
        private String name;
        private String phone;

        public PersonDto (){}

        public long getpersonid (){
        return personid;
        }

        public long getdocument (){
        return document;
        }

        public String getname (){
        return name;
        }

        public String getphone(){
        return phone;
        }

        public void setid (long personid){
             this.personid=personid;
        }

        public void setdocument (long document){
            this.document=document;
        }//

        public void setname(String name){
          this.name=name;
        }

        public void setphone (String phone) {
          this.phone=phone;
        }   

    public int getDocument() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
        }
