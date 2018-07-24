package app.web.controllers;

import app.common.UploaderPropertyEditor;
import app.domain.models.binding.TubeUploadBindingModel;
import app.domain.models.service.TubeServiceModel;
import app.domain.models.service.UserServiceModel;
import app.domain.models.view.TubeDetailsViewModel;
import app.service.services.TubeService;
import app.service.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/tube")
public class TubeController extends BaseController {

    private final TubeService tubeService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UploaderPropertyEditor uploaderPropertyEditor;

    public TubeController(TubeService tubeService, UserService userService, ModelMapper modelMapper, UploaderPropertyEditor uploaderPropertyEditor) {
        this.tubeService = tubeService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.uploaderPropertyEditor = uploaderPropertyEditor;
    }

    @GetMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView uploadTube(@ModelAttribute("tubeUploadBindingModel") TubeUploadBindingModel tubeUploadBindingModel) {

        return super.view("tubes/upload", "tubeUploadBindingModel", tubeUploadBindingModel);
    }

    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView uploadTubeConfirm(@ModelAttribute("tubeUploadBindingModel") TubeUploadBindingModel tubeUploadBindingModel,
                                          BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return super.view("tubes/upload", "tubeUploadBindingModel", tubeUploadBindingModel);
        }

        TubeServiceModel tubeServiceModel = this.modelMapper.map(tubeUploadBindingModel, TubeServiceModel.class);
        UserServiceModel userServiceModel = this.userService.extractUserByUsername(principal.getName());
        tubeServiceModel.setUploader(userServiceModel);
        tubeServiceModel.setYoutubeId(tubeUploadBindingModel.getYoutubeLink().split("\\?v=")[1]);
        this.tubeService.uploadTube(tubeServiceModel);

        return super.redirect("/home");
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView details(@PathVariable("id") String id) {
        TubeDetailsViewModel tubeDetailsViewModel = this.modelMapper.map(this.tubeService.extractTubeById(id), TubeDetailsViewModel.class);
        this.incrementViews(id);

        return super.view("tubes/details", "tubeDetailsViewModel", tubeDetailsViewModel);
    }

    private void incrementViews(String id) {
        TubeServiceModel tubeServiceModel = this.tubeService.extractTubeById(id);

        tubeServiceModel.setViews(tubeServiceModel.getViews() + 1);

        this.tubeService.uploadTube(tubeServiceModel);
    }
//
//    @InitBinder
//    public void uploaderBindingConfiguration(WebDataBinder binder) {
//        binder.registerCustomEditor(UserServiceModel.class, "uploader", this.uploaderPropertyEditor);
//    }
}
