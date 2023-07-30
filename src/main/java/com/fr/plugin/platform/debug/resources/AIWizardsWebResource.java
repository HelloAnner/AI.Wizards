package com.fr.plugin.platform.debug.resources;

import com.fr.ai.wizards.AIWizardsComponent;
import com.fr.decision.fun.impl.AbstractWebResourceProvider;
import com.fr.decision.web.MainComponent;
import com.fr.web.struct.Atom;

/**
 * @author anner
 * @version 11.0
 * Created on 2023/7/24
 */
public class AIWizardsWebResource extends AbstractWebResourceProvider {
    @Override
    public Atom attach() {
        return MainComponent.KEY;
    }

    @Override
    public Atom client() {
        return AIWizardsComponent.KEY;
    }
}
