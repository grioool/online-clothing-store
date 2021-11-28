package com.epam.training.jwd.online.shop.controller.handler.impl;


import com.epam.training.jwd.online.shop.controller.command.RequestContext;
import com.epam.training.jwd.online.shop.controller.constants.RequestConstant;
import com.epam.training.jwd.online.shop.controller.handler.AbstractHandler;
import com.epam.training.jwd.online.shop.controller.handler.Handler;
import com.epam.training.jwd.online.shop.util.LocalizationMessage;


import javax.servlet.http.Part;
import java.util.HashSet;
import java.util.Set;

public class ImgFileHandler extends AbstractHandler {

    public ImgFileHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public ImgFileHandler() {
    }

    @Override
    public Set<String> handleRequest(RequestContext requestContext) {
        Set<String> errorMessage = new HashSet<>();
        Part imgFile = requestContext.getRequestParts().get(RequestConstant.IMG_FILE);

        if (imgFile == null || StringUtils.isNullOrEmpty(imgFile.getSubmittedFileName())
              || (!imgFile.getSubmittedFileName().endsWith(".png") && !imgFile.getSubmittedFileName().endsWith(".jpg"))){

            errorMessage.add(LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.incorrectImg"));
        }
        if (nextHandler != null) {
            errorMessage.addAll(nextHandler.handleRequest(requestContext));
        }
        return errorMessage;
    }
}
