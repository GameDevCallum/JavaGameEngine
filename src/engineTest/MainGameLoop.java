package engineTest;

import entities.Camera;
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

import java.util.Calendar;

public class MainGameLoop implements Runnable {

    public void start() {
        Thread gameThread = new Thread(this, "MainThread");
        gameThread.start();
    }

    @Override
    public void run() {
        DisplayManager.createDisplay(1280, 720, "Main Game");
        DisplayManager.setFPS(30);

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        float[] vertices01 = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };

        float[] vertices02 = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };

        int[] indices01 = {
                0, 1, 3,
                3, 1, 2
        };

        int[] indices02 = {
                0, 1, 3,
                3, 1, 2
        };

        float[] textureCoords01 = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        float[] textureCoords02 = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };



        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));

        RawModel model01 = loader.loadToAVO(vertices01, textureCoords01, indices01);
        TexturedModel texturedModel01 = new TexturedModel(model01, texture);
        Entity entity01 = new Entity(texturedModel01, new Vector3f(0, 0, -1), 0, 0, 0, 1);

        Camera camera = new Camera();


        while (!Display.isCloseRequested()) {

//            entity01.increasePosition(0, 0, -0.1f); // Changes position

            camera.move();

            renderer.prepare();

            shader.start(); // Start shader before rendering
            shader.loadViewMatrix(camera);

            renderer.render(entity01, shader);

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
