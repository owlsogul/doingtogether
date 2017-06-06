package kr.ac.cau.jomingyu.doingtogether.ui;

import java.util.HashMap;

import javax.swing.ImageIcon;

public class ResourceManager {
	
	public static final String TAB_ICON_HOME = "home.png";
	public static final String TAB_ICON_TIMELINE = "timeline.png";
	
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
			ImageIcon icon = getIconFromResouce(ResourceManager.TAB_ICON_HOME);
			if (icon == null){
				return false;
			}
			iconMap.put(ResourceManager.TAB_ICON_HOME, icon);
			
			// timeline icon
			icon = getIconFromResouce(ResourceManager.TAB_ICON_TIMELINE);
			if (icon == null){
				return false;
			}
			iconMap.put(ResourceManager.TAB_ICON_TIMELINE, icon);
			
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