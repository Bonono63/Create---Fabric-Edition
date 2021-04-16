package com.mrbonono63.create.foundation.render.backend.gl.shader;

import java.util.ArrayList;
import java.util.Arrays;

import com.simibubi.create.Create;
import com.mrbonono63.create.foundation.render.backend.gl.attrib.IVertexAttrib;

import net.minecraft.util.ResourceLocation;

public class ProgramSpec<P extends GlProgram> {

    public final ResourceLocation name;
    public final ResourceLocation vert;
    public final ResourceLocation frag;

    public final ShaderConstants defines;

    public final GlProgram.ProgramFactory<P> factory;

    public final ArrayList<IVertexAttrib> attributes;

    public final boolean fogSensitive;

    public static <P extends GlProgram> Builder<P> builder(String name, GlProgram.ProgramFactory<P> factory) {
        return builder(new ResourceLocation(Create.ID, name), factory);
    }

    public static <P extends GlProgram> Builder<P> builder(ResourceLocation name, GlProgram.ProgramFactory<P> factory) {
        return new Builder<>(name, factory);
    }

    public ProgramSpec(ResourceLocation name, ResourceLocation vert, ResourceLocation frag, GlProgram.ProgramFactory<P> factory, ShaderConstants defines, ArrayList<IVertexAttrib> attributes, boolean fogSensitive) {
        this.name = name;
        this.vert = vert;
        this.frag = frag;
        this.defines = defines;

        this.factory = factory;
        this.attributes = attributes;
		this.fogSensitive = fogSensitive;
	}

    public ResourceLocation getVert() {
        return vert;
    }

    public ResourceLocation getFrag() {
        return frag;
    }

    public static class Builder<P extends GlProgram> {
        private ResourceLocation vert;
        private ResourceLocation frag;
        private ShaderConstants defines = ShaderConstants.EMPTY;
        private boolean fogSensitive = true;

        private final ResourceLocation name;
        private final GlProgram.ProgramFactory<P> factory;
        private final ArrayList<IVertexAttrib> attributes;

        public Builder(ResourceLocation name, GlProgram.ProgramFactory<P> factory) {
            this.name = name;
            this.factory = factory;
            attributes = new ArrayList<>();
        }

        public Builder<P> setVert(ResourceLocation vert) {
            this.vert = vert;
            return this;
        }

        public Builder<P> setFrag(ResourceLocation frag) {
            this.frag = frag;
            return this;
        }

        public Builder<P> setDefines(ShaderConstants defines) {
            this.defines = defines;
            return this;
        }

		public Builder<P> setFogSensitive(boolean fogSensitive) {
			this.fogSensitive = fogSensitive;
			return this;
		}

		public <A extends Enum<A> & IVertexAttrib> Builder<P> addAttributes(Class<A> attributeEnum) {
            attributes.addAll(Arrays.asList(attributeEnum.getEnumConstants()));
            return this;
        }

        public ProgramSpec<P> createProgramSpec() {
            return new ProgramSpec<>(name, vert, frag, factory, defines, attributes, fogSensitive);
        }
    }
}
