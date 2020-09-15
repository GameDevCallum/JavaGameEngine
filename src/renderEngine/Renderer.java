package renderEngine;

//import models.RawModel;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import shaders.StaticShader;
import toolbox.Maths;

public class Renderer
{
    private static final float POV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private  static final float FAR_PLANE = 10;

    private Matrix4f projectionMatrix;

    public Renderer(StaticShader shader) {
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.0f, 0.0f, 0.5f, 0.0f);
    }

//    public void renderRawModel(RawModel model) {
//        GL30.glBindVertexArray(model.getVaoID());
//        GL20.glEnableVertexAttribArray(0);
//        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//        GL20.glDisableVertexAttribArray(0);
//
//        GL30.glBindVertexArray(0);
//    }

    public void render(Entity entity, StaticShader shader) {
        TexturedModel model = entity.getModel();
        GL30.glBindVertexArray(model.getRawModel().getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1); // FOR IMAGE SHADER ONLY
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(0); // FOR IMAGE SHADER ONLY
        GL30.glBindVertexArray(0);
    }

    private void createProjectionMatrix() {
        float aspectRadio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) (1f / Math.tan(Math.toRadians(POV / 2f))) * aspectRadio;
        float x_scale = y_scale / aspectRadio;
        float frustum_len = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_len);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_len);
        projectionMatrix.m33 = 0;
    }
}
