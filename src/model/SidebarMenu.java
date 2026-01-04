package model;

import constant.PageMenu;

/**
 * Model untuk representasi menu item di sidebar dashboard
 * 
 * @author Leonovo
 */
public class SidebarMenu {
    
    private final String icon;
    private final String label;
    private final PageMenu pageMenu;
    private final Runnable action;
    private boolean isHeader;
    
    /**
     * Constructor untuk menu item biasa
     */
    public SidebarMenu(String icon, String label, PageMenu pageMenu, Runnable action) {
        this.icon = icon;
        this.label = label;
        this.pageMenu = pageMenu;
        this.action = action;
        this.isHeader = false;
    }
    
    /**
     * Constructor untuk header (section title)
     */
    public SidebarMenu(String label) {
        this.label = label;
        this.icon = null;
        this.pageMenu = null;
        this.action = null;
        this.isHeader = true;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String getFullText() {
        if (isHeader) {
            return label;
        }
        return icon != null ? icon + " " + label : label;
    }
    
    public PageMenu getPageMenu() {
        return pageMenu;
    }
    
    public Runnable getAction() {
        return action;
    }
    
    public boolean isHeader() {
        return isHeader;
    }
    
    public boolean isClickable() {
        return !isHeader && action != null;
    }
}
