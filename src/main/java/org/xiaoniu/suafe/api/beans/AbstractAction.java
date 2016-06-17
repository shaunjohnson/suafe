package org.xiaoniu.suafe.api.beans;

public abstract class AbstractAction {
    protected String action = null;

    public AbstractAction(final String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }
}
