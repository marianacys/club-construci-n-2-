
package app.model;

public class Guest {
        private long invitationid;
        private long partnetid;
        private String invitationstate;

        public Guest (){}

        public long getinvitationid (){
        return invitationid;
        }

        public long getpartnetid (){
        return partnetid;
        }

        public String getinvitationstate (){
        return invitationstate;
        }

        public void setinvitationid (long invitationid){
        this.invitationid=invitationid;
        }

        public void setsocioid (long partnetid){
             this.partnetid=partnetid;
        }

        public void setinvitationstate (String invitationstate){
             this.invitationstate=invitationstate;
        }
        } 

  
