package engineTest;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

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

        RawModel model = OBJLoader.loadObjModel("TestObject", loader);

        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);



        Camera camera = new Camera();

        // Run Loop

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);

            camera.move();

            renderer.prepare();

            shader.start(); // Start shader before rendering
            shader.loadViewMatrix(camera);

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
