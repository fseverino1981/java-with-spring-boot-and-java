package br.com.flavio.data.vo.v1.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCtredentialsVO implements Serializable {


    private static final long serialVersionUID = 6449642862373740342L;

    private String userName;
    private String password;

    public AccountCtredentialsVO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountCtredentialsVO that = (AccountCtredentialsVO) o;

        if (!Objects.equals(userName, that.userName)) return false;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
