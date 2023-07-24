package com.fr.ai.wizards;

import com.fr.web.struct.Component;
import com.fr.web.struct.browser.RequestClient;
import com.fr.web.struct.category.ScriptPath;
import com.fr.web.struct.category.StylePath;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/24
 */
public class AIWizardsComponent extends Component {

    private AIWizardsComponent() {
    }

    public final static AIWizardsComponent KEY = new AIWizardsComponent();

    @Override
    public ScriptPath script(RequestClient req) {
        return ScriptPath.build("com/fr/ai/wizards/web/resources/export.js");
    }

    @Override
    public StylePath style() {
        return StylePath.build("com/fr/ai/wizards/web/resources/export.css");
    }
}
