package ys09.model;

public class SignIn {
    private String mail;
    private String password;
    private int id;

    public int getId() {return id; }

    public String getEmail() {
        return mail;
    }

    public void setEmail(String mail) {
      this.mail = mail;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

}
