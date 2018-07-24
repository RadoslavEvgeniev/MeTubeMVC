package app.web.controllers;

import app.domain.models.service.TubeServiceModel;
import app.domain.models.view.HomeTubeViewModel;
import app.domain.models.view.HomeViewModel;
import app.service.services.TubeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController extends BaseController {

    private final TubeService tubeService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(TubeService tubeService, ModelMapper modelMapper) {
        this.tubeService = tubeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(Principal principal, @ModelAttribute("homeViewModel")HomeViewModel homeViewModel) {
        homeViewModel.setUsername(principal.getName());
        homeViewModel.setTubes(this.mapTubeModels(principal.getName()));

        return super.view("home", "homeViewModel", homeViewModel);
    }

    private List<HomeTubeViewModel> mapTubeModels(String username) {
        List<TubeServiceModel> tubeServiceModels = this.tubeService.extractAllTubes(username);
        List<HomeTubeViewModel> tubeViewModels = new ArrayList<>();
        for (TubeServiceModel tubeServiceModel : tubeServiceModels) {
            tubeViewModels.add(this.modelMapper.map(tubeServiceModel, HomeTubeViewModel.class));
        }

        return tubeViewModels;
    }
}
