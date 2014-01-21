package ru.nyrk.gisgmp.web.mytiles;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

public class TilesExposingBeansViewResolver extends UrlBasedViewResolver {

    private Boolean exposeContextBeansAsAttributes;
    private String[] exposedContextBeanNames;

    public void setExposeContextBeansAsAttributes(boolean exposeContextBeansAsAttributes) {
        this.exposeContextBeansAsAttributes = exposeContextBeansAsAttributes;
    }

    public void setExposedContextBeanNames(String[] exposedContextBeanNames) {
        this.exposedContextBeanNames = exposedContextBeanNames;
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AbstractUrlBasedView superView = super.buildView(viewName);
        if (superView instanceof TilesExposingBeansView) {
            TilesExposingBeansView view = (TilesExposingBeansView) superView;
            if (this.exposeContextBeansAsAttributes != null) view.setExposeContextBeansAsAttributes(this.exposeContextBeansAsAttributes);
            if (this.exposedContextBeanNames != null) view.setExposedContextBeanNames(this.exposedContextBeanNames);
        }
        return superView;
    }

}