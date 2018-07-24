package app.web.controllers;

import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {

//    protected ModelAndView view(String view, ModelAndView modelAndView) {
//        modelAndView.setViewName("fragments/layout");
//
//        modelAndView.addObject("view", view);
//
//        return modelAndView;
//    }
//
//    protected ModelAndView view(String view) {
//        return this.view(view, new ModelAndView());
//    }
//
//    protected ModelAndView redirect(String url) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        modelAndView.setViewName("redirect:" + url);
//        return modelAndView;
//    }

    protected ModelAndView view(String view, String objectName, Object object) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fragments/layout");
        modelAndView.addObject("view", view);
        modelAndView.addObject(objectName, object);

        return modelAndView;
    }

    protected ModelAndView view(String view) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fragments/layout");
        modelAndView.addObject("view", view);

        return modelAndView;
    }

    protected ModelAndView redirect(String url) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("redirect:" + url);
        return modelAndView;
    }
}
