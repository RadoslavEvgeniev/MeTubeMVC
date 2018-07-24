package app.domain.models.view;

import java.util.List;

public class UserProfileViewModel {

    private String username;
    private String email;
    private List<UserProfileTubeViewModel> tubes;

    public UserProfileViewModel() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserProfileTubeViewModel> getTubes() {
        return this.tubes;
    }

    public void setTubes(List<UserProfileTubeViewModel> tubes) {
        this.tubes = tubes;
    }
}
