package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager
{
    private static int FPS_CAP = 0;

    public static void setFPS(final int fpsCap) {
        FPS_CAP = fpsCap;
    }

    public static void createDisplay(final int width, final int height, final String title) {
        ContextAttribs attribs = new ContextAttribs(3, 2)
        .withForwardCompatible(true)
        .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle(title);
        }
        catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, width, height);
    }

    public static void updateDisplay() {

        if (FPS_CAP == 0)
        {
            Display.sync(30);
        }
        else
        {
            Display.sync(FPS_CAP);
        }

        Display.update();
    }

    public static void closeDisplay() {
        Display.destroy();
    }
}
