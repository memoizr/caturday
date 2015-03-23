package greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table USER.
 */
public class User {

    private Long id;
    private String serverId;
    private String username;
    private String image_url;
    private String authToken;
    private String email;
    private String info;
    private String favorites;
    private String firstName;
    private String lastName;
    private String description;
    private Boolean loggedIn;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String serverId, String username, String image_url, String authToken, String email, String info, String favorites, String firstName, String lastName, String description, Boolean loggedIn) {
        this.id = id;
        this.serverId = serverId;
        this.username = username;
        this.image_url = image_url;
        this.authToken = authToken;
        this.email = email;
        this.info = info;
        this.favorites = favorites;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.loggedIn = loggedIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}
