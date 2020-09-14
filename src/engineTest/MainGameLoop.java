package engineTest;

import entities.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;


/*

        VIDEO: https://youtu.be/50Y9u7K0PZo?list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP&t=417

* */






public class MainGameLoop implements Runnable
{

    public void start() {
        Thread gameThread = new Thread(this, "MainThread");
        gameThread.start();
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

        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };



        RawModel model = loader.loadToAVO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
        TexturedModel texturedModel = new TexturedModel(model, texture);
        Entity entity = new Entity(texturedModel, new Vector3f(-1, 0, 0), 0, 0, 0, 1);

        while (!Display.isCloseRequested()) {
            entity.increasePosition(0.005f, 0, 0); // Changes position
            entity.increaseRotation(0, 1, 0); // Changes rotation

            renderer.prepare();

            shader.start(); // Start shader before rendering

            renderer.render(entity, shader);

            shader.start(); // Stop shader after rendering
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    public static void main(String[] args) {
        new MainGameLoop().start();
    }
}
