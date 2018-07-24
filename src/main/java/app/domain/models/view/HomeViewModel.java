package app.domain.models.view;

import java.util.List;

public class HomeViewModel {

    private String username;
    private List<HomeTubeViewModel> tubes;

    public HomeViewModel() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<HomeTubeViewModel> getTubes() {
        return this.tubes;
    }

    public void setTubes(List<HomeTubeViewModel> tubes) {
        this.tubes = tubes;
    }
}
