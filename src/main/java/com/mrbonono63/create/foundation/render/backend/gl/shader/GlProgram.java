package com.mrbonono63.create.foundation.render.backend.gl.shader;

import org.lwjgl.opengl.GL20;

import com.mrbonono63.create.foundation.render.backend.Backend;
import com.mrbonono63.create.foundation.render.backend.gl.GlFogMode;
import com.mrbonono63.create.foundation.render.backend.gl.GlObject;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.IVertexAttrib;

import net.minecraft.util.ResourceLocation;

public abstract class GlProgram extends GlObject {

    public final ResourceLocation name;

    protected GlProgram(ResourceLocation name, int handle) {
        setHandle(handle);
        this.name = name;
    }

    public static Builder builder(ResourceLocation name, GlFogMode fogMode) {
        return new Builder(name, fogMode);
    }

    public void bind() {
        GL20.glUseProgram(handle());
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    /**
     * Retrieves the index of the uniform with the given name.
     * @param uniform The name of the uniform to find the index of
     * @return The uniform's index
     */
    public int getUniformLocation(String uniform) {
        int index = GL20.glGetUniformLocation(this.handle(), uniform);

        if (index < 0) {
            Backend.log.debug("No active uniform '{}' exists in program '{}'. Could be unused.", uniform, this.name);
        }

        return index;
    }

    /**
     * Binds a sampler uniform to the given texture unit.
     * @param name The name of the sampler uniform.
     * @param binding The index of the texture unit.
     * @return The sampler uniform's index.
     * @throws NullPointerException If no uniform exists with the given name.
     */
    public int setSamplerBinding(String name, int binding) {
        int samplerUniform = getUniformLocation(name);

        if (samplerUniform >= 0) {
            GL20.glUniform1i(samplerUniform, binding);
        }

        return samplerUniform;
    }

    @Override
    protected void deleteInternal(int handle) {
        GL20.glDeleteProgram(handle);
    }

    public static class Builder {
        private final ResourceLocation name;
        private final int program;
        private final GlFogMode fogMode;

        private int attributeIndex;

        public Builder(ResourceLocation name, GlFogMode fogMode) {
            this.name = name;
            this.program = GL20.glCreateProgram();
            this.fogMode = fogMode;
        }

        public Builder attachShader(GlShader shader) {
            GL20.glAttachShader(this.program, shader.handle());

            return this;
        }

        public <A extends IVertexAttrib> Builder addAttribute(A attrib) {
            GL20.glBindAttribLocation(this.program, attributeIndex, attrib.attribName());
            attributeIndex += attrib.attribSpec().getAttributeCount();
            return this;
        }

        /**
         * Links the attached shaders to this program and returns a user-defined container which wraps the shader
         * program. This container can, for example, provide methods for updating the specific uniforms of that shader
         * set.
         *
         * @param factory The factory which will create the shader program's container
         * @param <P> The type which should be instantiated with the new program's handle
         * @return An instantiated shader container as provided by the factory
         */
        public <P extends GlProgram> P build(ProgramFactory<P> factory) {
            GL20.glLinkProgram(this.program);

            String log = GL20.glGetProgramInfoLog(this.program);

            if (!log.isEmpty()) {
                Backend.log.debug("Program link log for " + this.name + ": " + log);
            }

            int result = GL20.glGetProgrami(this.program, GL20.GL_LINK_STATUS);

            if (result != GL20.GL_TRUE) {
                throw new RuntimeException("Shader program linking failed, see log for details");
            }

            return factory.create(this.name, this.program, this.fogMode.getFogFactory());
        }
    }

    @FunctionalInterface
    public interface ProgramFactory<P extends GlProgram> {
        P create(ResourceLocation name, int handle, ProgramFogMode.Factory fogFactory);
    }
}
