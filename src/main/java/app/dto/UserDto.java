package app.dto;

public class UserDto {
        private long userid;
        private String username;
        private String password;
        private String role;

        public UserDto (){}

        public long getuserid () {
         return userid;
        }

        public String getusername () {
        return username;
        }

        public String getpassword () {
        return password;
        }

        public String getrole () {
        return role;    
        }

        public void setuserid (long userid) {
        this.userid=userid;   
        }

        public void setusername (String username){
        this.username=username;  
        }

        public void setpassword (String password) {
        this.password=password;
        }

        public void setrole (String role) {
        this.role=role;
        }   
        
        }
