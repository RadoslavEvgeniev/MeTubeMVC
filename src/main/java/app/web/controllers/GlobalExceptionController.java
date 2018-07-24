package app.web.controllers;

import app.domain.models.view.ErrorViewModel;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController extends BaseController {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView getException(RuntimeException e) {
        String exceptionMessage = e.getClass().isAnnotationPresent(ResponseStatus.class)
                ? e.getClass().getAnnotation(ResponseStatus.class).reason()
                : "Something went wrong :(";

        int statusCode = e.getClass().isAnnotationPresent(ResponseStatus.class)
                ? e.getClass().getAnnotation(ResponseStatus.class).code().value()
                : 400;

        ErrorViewModel errorViewModel = new ErrorViewModel();
        errorViewModel.setMessage(exceptionMessage);
        errorViewModel.setStatusCode(statusCode);

        return super.view("errors/error-template", "errorViewModel", errorViewModel);
    }
}
