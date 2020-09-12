package engineTest;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop implements Runnable
{
    private Thread thread;

    public void start()
    {
        thread = new Thread(this, "MainThread");
        thread.start();
    }

    @Override
    public void run() {
        DisplayManager.createDisplay(1280, 720, "Main Game");
        DisplayManager.setFPS(30);

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };



        RawModel model = loader.loadToAVO(vertices, indices);

        while (!Display.isCloseRequested())
        {
            renderer.prepare();

            shader.start(); // Start shader before rendering

            renderer.render(model);

            shader.start(); // Stop shader after rendering
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    public static void main(String[] args)
    {
        new MainGameLoop().start();
    }
}
