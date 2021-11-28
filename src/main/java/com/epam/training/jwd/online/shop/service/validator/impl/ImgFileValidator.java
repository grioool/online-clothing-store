package com.epam.training.jwd.online.shop.service.validator.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.service.validator.AbstractValidator;
import com.epam.training.jwd.online.shop.service.validator.Validator;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;


import javax.servlet.http.Part;
import java.util.HashSet;
import java.util.Set;

public class ImgFileValidator extends AbstractValidator {

    public ImgFileValidator(Validator nextValidator) {
        super(nextValidator);
    }

    public ImgFileValidator() {
    }

    @Override
    public Set<String> validateRequest(RequestContext requestContext) {
        Set<String> errorMessage = new HashSet<>();
        Part imgFile = requestContext.getRequestParts().get(RequestConstant.IMG_FILE);

        if (imgFile == null || imgFile.getSubmittedFileName() == null || imgFile.getSubmittedFileName().isEmpty()
              || (!imgFile.getSubmittedFileName().endsWith(".png") && !imgFile.getSubmittedFileName().endsWith(".jpg"))){

            errorMessage.add(LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.incorrectImg"));
        }
        if (nextValidator != null) {
            errorMessage.addAll(nextValidator.validateRequest(requestContext));
        }
        return errorMessage;
    }
}
