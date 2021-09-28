package br.com.codenation.CentralDeErros.enums;

public enum Roles {

    ADMIN("ADMIN"), DEVELOPER("DEVELOPER"), USER("USER");

    private String roles;

    Roles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }

    public static Roles find(String value) {
        for (Roles role : Roles.values()) {
            if(value.equalsIgnoreCase((role.roles)))
                return role;
        }
        return null;
    }

}
