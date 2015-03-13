package org.xiaoniu.suafe.frames.panes;

import org.xiaoniu.suafe.resources.ResourceUtil;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class BaseSplitPane extends JSplitPane {

    private static final long serialVersionUID = 6274498016581207351L;

    /**
     * Creates a button using the specified bundle key and action code.
     *
     * @param key      Key used to lookup button text in resource bundle
     * @param action   Action code to associate with the button
     * @param listener Action listener
     * @return Newly created button
     */
    protected JButton createButton(String key, String action, ActionListener listener) {
        JButton button = new JButton();

        button.addActionListener(listener);

        if (action != null) {
            button.setActionCommand(action);
        }

        button.setText(ResourceUtil.getString(key));

        return button;
    }

    /**
     * Creates a button using the values.
     *
     * @param key        Key used to lookup button text in resource bundle
     * @param tooltipKey Key used to lookup tooltip text in resource bundle
     * @param icon       ImageIcon
     * @param action     Action code to associate with the button
     * @param listener   Action listener
     * @return Newly created button
     */
    protected JButton createButton(String key, String tooltipKey, ImageIcon icon, String action, ActionListener listener) {
        JButton button = new JButton();

        button.addActionListener(listener);
        button.setActionCommand(action);
        button.setText(ResourceUtil.getString(key));
        button.setIcon(icon);
        button.setToolTipText(ResourceUtil.getString(tooltipKey));

        return button;
    }

    /**
     * Create a label using the specified bundle key.
     *
     * @param key Key used to lookup label test in resource bundle
     * @return Newly created label
     */
    protected JLabel createLabel(String key) {
        return new JLabel(ResourceUtil.getString(key));
    }
}
