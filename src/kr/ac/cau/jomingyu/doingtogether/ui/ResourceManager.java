package kr.ac.cau.jomingyu.doingtogether.ui;

import java.util.HashMap;

import javax.swing.ImageIcon;

public class ResourceManager {
	
	public static final String 
		ICON_TAB_TIMELINE   = "timeline.png",
		ICON_TAB_HOME       = "home.png",
		ICON_CELL_DELETE    = "cell_delete.png",
		ICON_CELL_EDIT      = "cell_edit.png",
		ICON_CELL_SHARE     = "cell_share.png"
		;
	
	private IconManager iconManager;
	
	public ResourceManager (){
		iconManager = new IconManager();
	}
	
	public boolean loadResources(){
		return iconManager.loadIcon() /* && */;
	}
	
	public ImageIcon getIcon(String key){
		return iconManager.getIcon(key);
	}

}
class IconManager {
	
	private HashMap<String, ImageIcon> iconMap;
	
	public IconManager() {
		iconMap = new HashMap<>();
	}
	
	public ImageIcon getIcon(String key){
		return iconMap.get(key);
	}
	
	public boolean loadIcon(){
		iconMap.clear();
		try {
			// home icon
			ImageIcon icon = getIconFromResouce(ResourceManager.ICON_TAB_HOME);
			if (icon == null){
				return false;
			}
			iconMap.put(ResourceManager.ICON_TAB_HOME, icon);
			
			// timeline icon
			icon = getIconFromResouce(ResourceManager.ICON_TAB_TIMELINE);
			if (icon == null){
				return false;
			}
			iconMap.put(ResourceManager.ICON_TAB_TIMELINE, icon);
			
			// cell delete icon
			icon = getIconFromResouce(ResourceManager.ICON_CELL_DELETE);
			if (icon == null){
				return false;
			}
			iconMap.put(ResourceManager.ICON_CELL_DELETE, icon);
			
			// cell edit icon
			icon = getIconFromResouce(ResourceManager.ICON_CELL_EDIT);
			if (icon == null){
				return false;
			}
			iconMap.put(ResourceManager.ICON_CELL_EDIT, icon);
			
			// cell share icon
			icon = getIconFromResouce(ResourceManager.ICON_CELL_SHARE);
			if (icon == null){
				return false;
			}
			iconMap.put(ResourceManager.ICON_CELL_SHARE, icon);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private ImageIcon getIconFromResouce(String filename){
		return new ImageIcon(ResourceManager.class.getClass().getResource("/resource/icon/" + filename));
	}
}