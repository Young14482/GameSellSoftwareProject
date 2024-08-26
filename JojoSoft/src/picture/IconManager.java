package picture;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class IconManager {
	private static IconManager instance = new IconManager();
	private Map<String, ImageIcon> iconMap;
	private PictureDAO pictureDAO;

	private IconManager() {
		iconMap = new HashMap<>();
		pictureDAO = new PictureDAO();
	}

	public static IconManager getInstance() {
		return instance;
	}

	public Picture getPicture(int key) {
		return pictureDAO.getPicture(key);
	}

	public ImageIcon getIcon(Picture picture) {
		if (iconMap.containsKey(picture.getName())) {
			return iconMap.get(picture.getName());
		} else {
			ImageIcon icon = new ImageIcon(picture.getData());
			iconMap.put(picture.getName(), icon);
			return icon;
		}
	}

	public ImageIcon getIconByKey(int key) {
		return getIcon(getPicture(key));
	}
}
