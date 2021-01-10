package noppes.mpm.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import noppes.mpm.client.MpmClientProxy;
import noppes.mpm.client.animation.AniCrawling;
import noppes.mpm.client.animation.AniHug;
import noppes.mpm.client.model.part.*;
import noppes.mpm.client.model.part.hands.ModelClaws;
import noppes.mpm.client.model.part.hands.ModelHandWings;
import noppes.mpm.constants.EnumAnimation;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.ModelPartConfig;
import noppes.mpm.data.ModelPartData;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class ModelMPM extends ModelBiped {
    public ModelData data;
    public ModelBase entityModel;
    public EntityLivingBase entity;
    public boolean currentlyPlayerTexture;
    public boolean isArmor;

    private ModelPartInterface wings;
    private ModelPartInterface mohawk;
    private ModelPartInterface hair;
    private ModelPartInterface beard;
    private ModelPartInterface breasts;
    private ModelPartInterface snout;
    private ModelPartInterface ears;
    private ModelPartInterface fin;
    private ModelPartInterface skirt;
    private ModelPartInterface horns;
    private ModelPartInterface clawsL;
    private ModelPartInterface clawsR;
    private ModelPartInterface handWingsL;
    private ModelPartInterface handWingsR;
    private ModelLegs legs;
    private ModelScaleRenderer headwear;
    private ModelTail tail;

    private float z;

    public ModelMPM(float z) {
        this(z, 32);
    }

    public ModelMPM(float z, int textureHeight) {
        super(z, 0, 64, textureHeight);
        this.z = z;
        isArmor = (z > 0.0F);
        reloadBoxes();
    }

    public void reloadBoxes() {
        bipedCloak = new ModelRenderer(this, 0, 0);
        bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, z);

        bipedEars = new ModelRenderer(this, 24, 0);
        bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, z);

        final float headZ = isArmor ? z + 0.3f : z;
        bipedHead = new ModelScaleRenderer(this, 0, 0);
        bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, headZ);
        bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);

        bipedHeadwear = new ModelScaleRenderer(this, 32, 0);
        bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, headZ + 0.25F);
        bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);

        bipedBody = new ModelScaleRenderer(this, 16, 16);
        bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, z);
        bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);

        bipedRightArm = new ModelScaleRenderer(this, 40, 16);
        bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, z);
        bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);

        bipedLeftArm = new ModelScaleRenderer(this, 40, 16);
        bipedLeftArm.mirror = true;
        bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, z);
        bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);

        bipedRightLeg = new ModelScaleRenderer(this, 0, 16);
        bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, z);
        bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);

        bipedLeftLeg = new ModelScaleRenderer(this, 0, 16);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, z);
        bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);

        reloadExtraPartBoxes();
    }

    protected void reloadExtraPartBoxes() {
        legs = new ModelLegs(this, (ModelScaleRenderer) bipedRightLeg, (ModelScaleRenderer) bipedLeftLeg);
        headwear = new ModelHeadwear(this);

        bipedBody.addChild(breasts = new ModelBreasts(this));

        if (!isArmor) {
            bipedHead.addChild(ears = new ModelEars(this));
            bipedHead.addChild(mohawk = new ModelMohawk(this));
            bipedHead.addChild(hair = new ModelHair(this));
            bipedHead.addChild(beard = new ModelBeard(this));
            bipedHead.addChild(snout = new ModelSnout(this));
            bipedHead.addChild(horns = new ModelHorns(this));

            bipedBody.addChild(tail = new ModelTail(this));
            bipedBody.addChild(wings = new ModelWings(this));
            bipedBody.addChild(skirt = new ModelSkirt(this));
            bipedBody.addChild(fin = new ModelFin(this));

            bipedLeftArm.addChild(clawsL = new ModelClaws(this, false));
            bipedRightArm.addChild(clawsR = new ModelClaws(this, true));
            bipedLeftArm.addChild(handWingsL = new ModelHandWings(this, false));
            bipedRightArm.addChild(handWingsR = new ModelHandWings(this, true));
        }
    }

    public void setPlayerData(ModelData newdata, EntityLivingBase entity) {
        boolean justLoaded = data == null;
        data = newdata;
        if (justLoaded) reloadBoxes();

        if (!isArmor) {
            mohawk.setData(data, entity);
            beard.setData(data, entity);
            hair.setData(data, entity);
            snout.setData(data, entity);
            tail.setData(data, entity);
            fin.setData(data, entity);
            wings.setData(data, entity);
            ears.setData(data, entity);
            clawsL.setData(data, entity);
            clawsR.setData(data, entity);
            handWingsL.setData(data, entity);
            handWingsR.setData(data, entity);
            skirt.setData(data, entity);
            horns.setData(data, entity);
        }

        breasts.setData(data, entity);
        legs.setData(data, entity);
    }

    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        if (entityModel != null) {
            if (!isArmor) {
                entityModel.isChild = entity.isChild();
                entityModel.onGround = onGround;
                entityModel.isRiding = isRiding;
                if ((entityModel instanceof ModelBiped)) {
                    ModelBiped biped = (ModelBiped) entityModel;
                    biped.aimedBow = aimedBow;
                    biped.heldItemLeft = heldItemLeft;
                    biped.heldItemRight = heldItemRight;
                    biped.isSneak = isSneak;
                }
                entityModel.render(entity, par2, par3, par4, par5, par6, par7);
            }
            return;
        }

        currentlyPlayerTexture = true;
        setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

        if (data.animation == EnumAnimation.BOW) {
            GL11.glPushMatrix();
            float ticks = (par1Entity.ticksExisted - data.animationStart) / 10.0F;
            if (ticks > 1.0F)
                ticks = 1.0F;
            float scale = 2.0F - data.body.scaleY + data.getLegsY();
            GL11.glTranslatef(0.0F, 12.0F * scale * par7, 0.0F);
            GL11.glRotatef(60.0F * ticks, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -12.0F * scale * par7, 0.0F);
        }

        renderHead(par1Entity, par7);
        renderArms(par1Entity, par7, false);
        renderBody(par1Entity, par7);
        if (data.animation == EnumAnimation.BOW) {
            GL11.glPopMatrix();
        }
        renderLegs(par7);
    }


    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
        if (!isRiding) {
            isRiding = (data.animation == EnumAnimation.SITTING);
        }
        if ((isSneak) && ((data.animation == EnumAnimation.CRAWLING) || (data.isSleeping())))
            isSneak = false;
        bipedBody.rotationPointZ = 0.0F;
        bipedBody.rotationPointY = 0.0F;
        bipedHead.rotateAngleZ = 0.0F;
        bipedHeadwear.rotateAngleZ = 0.0F;
        bipedLeftLeg.rotateAngleX = 0.0F;
        bipedLeftLeg.rotateAngleY = 0.0F;
        bipedLeftLeg.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleX = 0.0F;
        bipedRightLeg.rotateAngleY = 0.0F;
        bipedRightLeg.rotateAngleZ = 0.0F;
        bipedLeftArm.rotationPointY = 2.0F;
        bipedLeftArm.rotationPointZ = 0.0F;
        bipedRightArm.rotationPointY = 2.0F;
        bipedRightArm.rotationPointZ = 0.0F;

        super.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);

        if (!isArmor) {
            hair.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            beard.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            wings.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            tail.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
            skirt.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);
        }
        legs.setRotationAngles(par1, par2, par3, par4, par5, par6, entity);

        if (isSleeping(entity)) {
            if (bipedHead.rotateAngleX < 0.0F) {
                bipedHead.rotateAngleX = 0.0F;
                bipedHeadwear.rotateAngleX = 0.0F;
            }
        } else if (data.animation == EnumAnimation.CRY) {
            bipedHeadwear.rotateAngleX = (bipedHead.rotateAngleX = 0.7F);
        } else if (data.animation == EnumAnimation.HUG) {
            AniHug.setRotationAngles(par1, par2, par3, par4, par5, par6, entity, this);
        } else if (data.animation == EnumAnimation.CRAWLING) {
            AniCrawling.setRotationAngles(par1, par2, par3, par4, par5, par6, entity, this);
        } else if (data.animation == EnumAnimation.WAVING) {
            bipedRightArm.rotateAngleX = -0.1F;
            bipedRightArm.rotateAngleY = 0.0F;
            bipedRightArm.rotateAngleZ = ((float) (2.141592653589793D - Math.sin(entity.ticksExisted * 0.27F) * 0.5D));
        } else if (isSneak) {
            bipedBody.rotateAngleX = (0.5F / data.body.scaleY);
        }
    }


    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
        if (entityModel != null) {
            entityModel.setLivingAnimations(entity, par2, par3, par4);
        } else if (!isArmor) {
            ModelPartData partData = data.getPartData("tail");
            if (partData != null)
                tail.setLivingAnimations(partData, par1EntityLivingBase, par2, par3, par4);
        }
    }

    public void bindPlayerTexture() {
        if (!isArmor && !currentlyPlayerTexture) {
            MpmClientProxy.bindTexture(data.playerResource);
            currentlyPlayerTexture = true;
        }
    }

    private void renderHead(Entity entity, float f) {
        if (data.getHeadCount() == 0)
            return;

        bindPlayerTexture();

        float y = data.getBodyY();

        final int headCount = data.getHeadCount();
        final float headWidth = data.head.scaleX * 0.6f;
        final float headOffset = (headCount - 1) * headWidth * 0.5f;

        GL11.glPushMatrix();

        if (data.animation == EnumAnimation.DANCING) {
            float dancing = entity.ticksExisted / 4.0F;
            GL11.glTranslatef((float) Math.sin(dancing) * 0.075F, (float) Math.abs(Math.cos(dancing)) * 0.125F - 0.02F, (float) -Math.abs(Math.cos(dancing)) * 0.075F);
        }

        if ((bipedHeadwear.showModel) && (!bipedHeadwear.isHidden)) {
            if ((data.headwear == 1) || (isArmor)) {
                for (int i = 0; i < headCount; i++) {
                    ((ModelScaleRenderer) bipedHeadwear).setConfig(data.head, -headOffset + i * headWidth, y, 0);
                    ((ModelScaleRenderer) bipedHeadwear).render(f);
                }
            } else if (data.headwear == 2) {
                headwear.rotateAngleX = bipedHeadwear.rotateAngleX;
                headwear.rotateAngleY = bipedHeadwear.rotateAngleY;
                headwear.rotateAngleZ = bipedHeadwear.rotateAngleZ;
                headwear.rotationPointX = bipedHeadwear.rotationPointX;
                headwear.rotationPointY = bipedHeadwear.rotationPointY;
                headwear.rotationPointZ = bipedHeadwear.rotationPointZ;

                for (int i = 0; i < headCount; i++) {
                    headwear.setConfig(data.head, -headOffset + i * headWidth, y, 0);
                    headwear.render(f);
                }
            }
        }

        for (int i = 0; i < headCount; i++) {
            ((ModelScaleRenderer) bipedHead).setConfig(data.head, -headOffset + i * headWidth, y, 0);
            ((ModelScaleRenderer) bipedHead).render(f);
        }

        GL11.glPopMatrix();
    }

    private void renderBody(Entity entity, float f) {
        bindPlayerTexture();
        float x = 0.0F;
        float y = data.getBodyY();
        float z = 0.0F;
        GL11.glPushMatrix();

        if (data.animation == EnumAnimation.DANCING) {
            float dancing = entity.ticksExisted / 4.0F;
            GL11.glTranslatef((float) Math.sin(dancing) * 0.015F, 0.0F, 0.0F);
        }

        ((ModelScaleRenderer) bipedBody).setConfig(data.body, x, y, z);
        ((ModelScaleRenderer) bipedBody).render(f);
        GL11.glPopMatrix();
    }

    public void renderArms(Entity entity, float f, boolean firstPerson) {
        bindPlayerTexture();

        float x = (1.0F - data.body.scaleX) * 0.25F + (1.0F - data.arms.scaleX) * 0.075F;
        float y = data.getBodyY() + (1.0F - data.arms.scaleY) * -0.1F;
        float z = 0.0F;

        GL11.glPushMatrix();

        if (data.animation == EnumAnimation.DANCING) {
            float dancing = entity.ticksExisted / 4.0F;
            GL11.glTranslatef((float) Math.sin(dancing) * 0.025F, (float) Math.abs(Math.cos(dancing)) * 0.125F - 0.02F, 0.0F);
        }

        if (!firstPerson) {
            byte amputee = data.armsAmputee;
            if (amputee == 0 || amputee == 3) {
                ((ModelScaleRenderer) bipedLeftArm).setConfig(data.arms, -x, y, z);
                ((ModelScaleRenderer) bipedLeftArm).render(f);
            }
            if (amputee == 0 || amputee == 2) {
                ((ModelScaleRenderer) bipedRightArm).setConfig(data.arms, x, y, z);
                ((ModelScaleRenderer) bipedRightArm).render(f);
            }
        } else {
            ((ModelScaleRenderer) bipedRightArm).setConfig(data.arms, 0.0F, 0.0F, 0.0F);
            ((ModelScaleRenderer) bipedRightArm).render(f);
        }

        GL11.glPopMatrix();
    }

    private void renderLegs(float f) {
        bindPlayerTexture();

        float x = (1.0F - data.legs.scaleX) * 0.125F;
        float y = data.getLegsY();
        float z = 0.0F;

        GL11.glPushMatrix();
        legs.setConfig(data.legs, x, y, z);
        legs.render(f);
//        if (!isArmor) {
//            tail.setConfig(legs, 0.0F, y, z);
//            tail.render(f);
//        }
        GL11.glPopMatrix();
    }

    public ModelRenderer getRandomModelBox(Random par1Random) {
        int random = par1Random.nextInt(5);
        switch (random) {
            case 0:
                return bipedRightLeg;
            case 1:
                return bipedHead;
            case 2:
                return bipedLeftArm;
            case 3:
                return bipedRightArm;
            case 4:
                return bipedLeftLeg;
        }

        return bipedBody;
    }

    public boolean isSleeping(Entity entity) {
        return ((entity instanceof EntityPlayer)) && (((EntityPlayer) entity).isPlayerSleeping()) || data.isSleeping();
    }
}
