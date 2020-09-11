package engineTest;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop
{
    public static void main(String[] args)
    {
        DisplayManager.CreateDisplay(1280, 720, "Main Game");
        DisplayManager.SetFPS(30);

        Loader loader = new Loader();

        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0,

                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f

        };

        RawModel model = loader.loadToAVO(vertices);

        while (!Display.isCloseRequested())
        {
            renderer.prepare();

            renderer.render(model);

            DisplayManager.UpdateDisplay();
        }

        loader.cleanUp();
        DisplayManager.CloseDisplay();
    }
}
