package com.fr.plugin.platform.debug.resources;

import com.fr.ai.wizards.AIWizards;
import com.fr.ai.wizards.AIWizardsContext;
import com.fr.ai.wizards.gpt.CombinePromptInfo;
import com.fr.ai.wizards.third.ThirdProxy;
import com.fr.decision.webservice.annotation.LoginStatusChecker;
import com.fr.decision.webservice.v10.login.TokenResource;
import com.fr.third.springframework.web.bind.annotation.PostMapping;
import com.fr.third.springframework.web.bind.annotation.RequestBody;
import com.fr.third.springframework.web.bind.annotation.RequestMapping;
import com.fr.third.springframework.web.bind.annotation.RequestParam;
import com.fr.third.springframework.web.bind.annotation.ResponseBody;
import com.fr.third.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/25
 */
@RestController
@ResponseBody
@LoginStatusChecker(tokenResource = TokenResource.COOKIE)
@RequestMapping("/ai/wizards/third")
public class ThirdProxyController {

    @PostMapping(value = "/ocr")
    public String baiduOCR(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(name = "image") String imageData,
                           @RequestParam(name = "access_token", required = false) String token) {
        return ThirdProxy.ocr(token, imageData);
    }

    @PostMapping(value = "/glm/sync")
    public String chatGLM(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(name = "id") String id,
                          @RequestParam(name = "access_token", required = false) String token,
                          @RequestBody CombinePromptInfo promptInfos) {
        AIWizardsContext.appendPrompt(id, promptInfos.getPrompt());
        AIWizards.log(AIWizards.toJson(AIWizardsContext.getPrompt(id)));
        return ThirdProxy.glm(AIWizards.toJson(AIWizardsContext.getPrompt(id)));
    }
}
