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

public final class LoadCoefficients implements Action {
    @Override
    public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject jsonResponse = new JsonObject();
        PrintWriter out = response.getWriter();
        response.setContentType(JSON_UTF8_CONTENT_TYPE);

        Map<Integer, List<CoefficientsDto>> coefficients = LoadCoefficientsJob.cachedCoefficients;
        String jsonStrCoefficients = new Gson().toJson(coefficients);
        JsonElement jsonElementCoefficients = JsonParser.parseString(jsonStrCoefficients);

        jsonResponse.add(DATA_PROPERTY, jsonElementCoefficients);
        jsonResponse.addProperty(STATUS_PARAM, STATUS_OK);

        out.write(jsonResponse.toString());
    }
}
