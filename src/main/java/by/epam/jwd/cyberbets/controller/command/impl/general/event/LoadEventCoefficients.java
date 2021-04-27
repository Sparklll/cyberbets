package by.epam.jwd.cyberbets.controller.command.impl.general.event;

import by.epam.jwd.cyberbets.controller.command.Action;
import by.epam.jwd.cyberbets.domain.dto.CoefficientsDto;
import by.epam.jwd.cyberbets.service.job.LoadCoefficientsJob;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static by.epam.jwd.cyberbets.controller.Parameters.*;

public final class LoadEventCoefficients implements Action {
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> jsonMap = (Map<String, Object>) request.getAttribute(JSON_MAP);

        if (jsonMap != null) {
            JsonObject jsonResponse = new JsonObject();
            PrintWriter out = response.getWriter();
            response.setContentType(JSON_UTF8_CONTENT_TYPE);

            Double eventId = (Double) jsonMap.get(ID_PARAM);

            if (eventId != null) {
                Map<Integer, List<CoefficientsDto>> coefficients = LoadCoefficientsJob.cachedCoefficients;
                List<CoefficientsDto> eventCoefficients = coefficients.get(eventId.intValue());

                String jsonStrEventCoefficients = new Gson().toJson(eventCoefficients);
                JsonElement jsonElementEventCoefficients = JsonParser.parseString(jsonStrEventCoefficients);

                jsonResponse.add(DATA_PROPERTY, jsonElementEventCoefficients);
                jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);
            } else {
                jsonResponse.addProperty(STATUS_PARAM, STATUS_DENY);
            }
            out.write(jsonResponse.toString());
        }
    }
}

