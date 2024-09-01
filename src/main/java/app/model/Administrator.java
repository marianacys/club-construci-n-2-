
package app.model;

public class Administrator {
        private long administratorid;
        private String fullname;
        private String email;
  
        public Administrator (){}
        public long getadministratorid (){
        return administratorid;
        }

        public String getfullname (){
        return fullname;
        }

        public String getemail (){
        return email;
        }

        public void setadministratorid (long administratorid){
        this.administratorid=administratorid;
        }

        public void setfullname (String fullname){
        this.fullname=fullname;
        }

        public void setemail (String email){
             this.email=email;
        }
        }

   
