package com.fr.plugin.platform.debug.resources;

import com.fr.ai.wizards.AIWizards;
import com.fr.ai.wizards.AIWizardsContext;
import com.fr.ai.wizards.gpt.AIBox;
import com.fr.ai.wizards.gpt.CombineTplDataInfo;
import com.fr.decision.webservice.Response;
import com.fr.decision.webservice.annotation.LoginStatusChecker;
import com.fr.third.fasterxml.jackson.core.JsonProcessingException;
import com.fr.third.springframework.web.bind.annotation.RequestMapping;
import com.fr.third.springframework.web.bind.annotation.RequestMethod;
import com.fr.third.springframework.web.bind.annotation.RequestParam;
import com.fr.third.springframework.web.bind.annotation.ResponseBody;
import com.fr.third.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/17
 * <p>
 * 对外的 rest 接口
 */
@RestController
@ResponseBody
@LoginStatusChecker(required = false)
@RequestMapping("/ai/wizards/data")
public class AIWizardController {


    @RequestMapping(method = RequestMethod.GET)
    public String getTmpData(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(name = "session_id") String sessionID) throws JsonProcessingException {
        CombineTplDataInfo combineTplDataInfo = AIWizardsContext.getSessionIDInfoIfPresent(sessionID);
        if (combineTplDataInfo != null) {
            // 转换拦截信息为可以理解的形式
            return AIWizards.toJson(AIBox.oneway(combineTplDataInfo));
        }
        return "";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Response clean(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(name = "session_id") String sessionID) throws JsonProcessingException {
        AIWizardsContext.cleanSessionIDInfo(sessionID);
        return Response.success();
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String printAllData(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        return AIWizardsContext.print();
    }

}
