package org.am.utils;

import java.net.URL;
import java.awt.Image;

import javax.swing.*;

/**
 * The IconLoader. Loads the icons.
 *
 * @author Alexander Maximenya
 * @version $Revision: 1.1 $
 */
public class IconLoader {
    /**
     * Gets an icon.
     *
     * @param iconPath an icon path (/org/project/icons/image16.png)
     * @return         an icon  
     */
    public ImageIcon getIcon(String iconPath) {
         URL iconUrl = getClass().getResource(iconPath);
        if (iconUrl != null) {
            return new ImageIcon(iconUrl);
        }
        return null;
    }

    /**
     * Gets an image.
     *
     * @param imagePath an image path (/org/project/icons/image16.png)
     * @return an image
     */
    public Image getImage(String imagePath) {
        return getIcon(imagePath).getImage();
    }
}
