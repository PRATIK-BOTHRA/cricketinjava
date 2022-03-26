package Menu;

import javafx.geometry.Rectangle2D;

public class Screen {

    private static Rectangle2D screenBounds;

   static
   {
        screenBounds = javafx.stage.Screen.getPrimary().getBounds();
   }

    public static double getWidth()
    {
        return screenBounds.getWidth();
    }

    public static double getHeight()
    {
        return screenBounds.getHeight();
    }

    public static double getSceneWidth()
    {
        return screenBounds.getWidth();
    }

    public static double getSceneHeight()
    {
        return screenBounds.getHeight() - 80;
    }

    public static double getFullScreenHeight()
    {
        return screenBounds.getHeight() + 500;
    }

    public static double getScreenCenter()
    {
        return screenBounds.getWidth() / 2;
    }

    public static double getNotiWidth() {return 300;}

    public static double getNotiHeight() {return 300;}
}
