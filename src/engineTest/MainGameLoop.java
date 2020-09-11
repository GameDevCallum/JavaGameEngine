package engineTest;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

public class MainGameLoop
{
    public static void main(String[] args)
    {
        DisplayManager.CreateDisplay(1280, 720, "Main Game");
        DisplayManager.SetFPS(30);


        while (!Display.isCloseRequested())
        {
            DisplayManager.UpdateDisplay();
        }

        DisplayManager.CloseDisplay();
    }
}
