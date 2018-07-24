package app.common;

import app.domain.models.service.UserServiceModel;
import app.service.services.UserService;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class UploaderPropertyEditor extends PropertyEditorSupport {

    private final UserService userService;

    public UploaderPropertyEditor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setValue(Object value) {
        String unparsedValue = value.toString();
        UserServiceModel parsedValue = this.userService.extractUserByUsername(unparsedValue);

        super.setValue(parsedValue);
    }
}
