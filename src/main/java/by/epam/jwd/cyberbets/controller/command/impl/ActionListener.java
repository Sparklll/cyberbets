package by.epam.jwd.cyberbets.controller.command.impl;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.controller.command.ActionProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.ACTION;
import static by.epam.jwd.cyberbets.controller.Parameters.JSON_MAP;

public class ActionListener implements Action {
    private static final Logger logger = LoggerFactory.getLogger(ActionListener.class);

    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(request.getReader());
        Map<String, Object> jsonMap = gson.fromJson(jsonReader, new TypeToken<Map<String, Object>>(){}.getType());

        String actionName = null;
        if(jsonMap != null) {
            actionName = (String) jsonMap.get(ACTION);
            logger.info(actionName);
            request.setAttribute(JSON_MAP, jsonMap);
        }
        Action action = ActionProvider.INSTANCE.getAction(actionName);
        action.perform(request, response);
    }
}
