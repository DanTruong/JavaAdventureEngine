package com.dmt.java_adventure_engine;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

@Controller
public class WebController {

    @GetMapping("/")
    public String showTerminal(Model model, HttpSession session) {

        GameWorld gw = (GameWorld) session.getAttribute("gameWorld");
        String output = (String) session.getAttribute("output");

        if (gw == null) {
            gw = loadGameWorld();
            session.setAttribute("gameWorld", gw);
            output = "Loading game.xml...\n"
                    + "Type in a command > ";
            session.setAttribute("output", output);
        }

        model.addAttribute("output", output);
        return "index";
    }

    @PostMapping("/")
    public String processInput(
            @RequestParam("input") String input,
            Model model,
            HttpSession session) {

        GameWorld gw = (GameWorld) session.getAttribute("gameWorld");
        String output = (String) session.getAttribute("output");

        if (gw == null) {
            gw = loadGameWorld();
            session.setAttribute("gameWorld", gw);
            output = "Loading game.xml...\n"
                    + "Type in a command > ";
        }

        output += input + "\n";

        String commandOutput = gw.getPlayer().processInput(input);

        if (!commandOutput.isEmpty()) {
            output += commandOutput + "\n";
        }

        if (!input.equalsIgnoreCase("exit")) {
            output += "Type in a command > ";
        }

        session.setAttribute("output", output);
        model.addAttribute("output", output);
        return "index";
    }

    private GameWorld loadGameWorld() {
        GameWorld gw = new GameWorld();

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            InputStream xmlFile = getClass().getClassLoader().getResourceAsStream("game.xml");

            if (xmlFile == null) {
                throw new RuntimeException("game.xml could not be found.");
            }
            sp.parse(xmlFile, gw);
        } catch (IOException ioe) {
            throw new RuntimeException("Error loading game.xml.", ioe);
        } catch (SAXException | ParserConfigurationException e) {
            throw new RuntimeException("Error parsing game.xml.", e);
        }
        return gw;
    }
}
