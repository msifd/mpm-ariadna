package noppes.mpm.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import noppes.mpm.client.MpmClientProxy;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.ModelPartData;
import org.lwjgl.opengl.GL11;

public abstract class ModelPartInterface extends ModelRenderer {
    public ModelData data;
    public ModelMPM base;
    public float scale = 1.0F;
    public int color = 16777215;
    protected ResourceLocation location;
    private EntityLivingBase entity;

    public ModelPartInterface(ModelMPM baseModel) {
        super(baseModel);
        base = baseModel;
        setTextureSize(0, 0);
    }

    public void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
    }
    
    public void setLivingAnimations(ModelPartData data, EntityLivingBase entityliving, float f, float f1, float f2) {
    }

    public void setData(ModelData newData, EntityLivingBase newEntity) {
        data = newData;
        entity = newEntity;
        initData(data);
    }

    public void render(float par1) {
        if ((isHidden) || (!showModel))
            return;
        if (!base.isArmor) {
            if (location != null) {
                MpmClientProxy.bindTexture(location);
                base.currentlyPlayerTexture = false;
            } else {
                base.bindPlayerTexture();
            }
        }
        boolean bo = (entity.hurtTime <= 0) && (entity.deathTime <= 0) && (!base.isArmor);
        if (bo) {
            float red = (color >> 16 & 0xFF) / 255.0F;
            float green = (color >> 8 & 0xFF) / 255.0F;
            float blue = (color & 0xFF) / 255.0F;
            GL11.glColor4f(red, green, blue, 1.0F);
        }
        super.render(par1);
        renderParts(par1);
        if (bo) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
        base.bindPlayerTexture();
    }

    public void renderParts(float par1) {
    }

    public abstract void initData(ModelData paramModelData);
}
