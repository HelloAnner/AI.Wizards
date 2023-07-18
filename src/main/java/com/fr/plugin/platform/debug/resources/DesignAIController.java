package com.fr.plugin.platform.debug.resources;

import com.fr.ai.wizards.AIWizardsContext;
import com.fr.decision.webservice.annotation.LoginStatusChecker;
import com.fr.decision.webservice.v10.login.TokenResource;
import com.fr.third.fasterxml.jackson.core.JsonProcessingException;
import com.fr.third.springframework.web.bind.annotation.RequestMapping;
import com.fr.third.springframework.web.bind.annotation.RequestMethod;
import com.fr.third.springframework.web.bind.annotation.ResponseBody;
import com.fr.third.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/17
 */
@RestController
@ResponseBody
@LoginStatusChecker(tokenResource = TokenResource.COOKIE)
@RequestMapping("/ai/wizards")
public class DesignAIController {


    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public String testExit(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        return AIWizardsContext.print();
    }

    @RequestMapping(value = "/cache/clean", method = RequestMethod.GET)
    public void cleanAll(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
    }

}
