package noppes.mpm.client.render;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import noppes.mpm.data.ModelData;
import noppes.mpm.data.PlayerDataController;
import noppes.mpm.client.SkinLoader;
import noppes.mpm.client.model.ModelMPM;
import noppes.mpm.client.model.ModelMpmNewFormat;
import noppes.mpm.client.model.ModelRenderPassHelper;
import noppes.mpm.constants.EnumAnimation;
import org.lwjgl.opengl.GL11;

import java.util.UUID;

public class RenderMPM extends RenderPlayer {
    ModelMPM modelBipedMain;
    ModelMPM modelArmorChestplate;
    ModelMPM modelArmor;

    private ModelData data;
    private RendererLivingEntity renderEntity;
    private EntityLivingBase entity;
    private ModelRenderPassHelper renderpass = new ModelRenderPassHelper();

    public RenderMPM() {
        setRenderManager(RenderManager.instance);
        this.modelBipedMain = new ModelMpmNewFormat();
        this.modelArmor = new ModelMPM(0.3F);
        this.modelArmorChestplate = new ModelMPM(0.4F);
    }

    public void setModelData(ModelData newData, EntityLivingBase entity) {
        data = newData;

        if (data.reloadBoxes) {
            this.modelBipedMain.reloadBoxes();
            data.reloadBoxes = false;
        }

        this.modelBipedMain.setPlayerData(data, entity);
        this.modelArmorChestplate.setPlayerData(data, entity);
        this.modelArmor.setPlayerData(data, entity);
    }

    void checkSkinState(EntityPlayer player) {
        if (!data.loaded) {
            if (player.ticksExisted > 20) {
                data.playerResource = SkinLoader.loadSkin((AbstractClientPlayer) player, data);
            } else
                data.playerResource = ((AbstractClientPlayer) player).getLocationSkin();
        }
    }

    @Override
    protected void passSpecialRender(EntityLivingBase base, double x, double y, double z) {
        if ((data.isSleeping()) || (data.animation == EnumAnimation.CRAWLING)) {
            y -= 1.5D;
        } else if (data != null)
            y -= data.getBodyY();
        base.isSneaking();
        if (data.animation == EnumAnimation.SITTING)
            y -= 0.6D;
        super.passSpecialRender(base, x, y, z);
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer player) {
        data = PlayerDataController.instance.getPlayerData(player);

        setModelData(data, player);
        checkSkinState(player);

        GL11.glColor3f(1, 1, 1);
        modelBipedMain.onGround = 0.0F;
        modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, player);
        modelBipedMain.renderArms(player, 0.0625F, true);
    }

    public void renderItem(EntityPlayer player) {
        ItemStack itemStack = player.inventory.getCurrentItem();

        if (itemStack != null) {
            GL11.glPushMatrix();
            float y = (data.arms.scaleY - 1.0F) * 0.7F;

            float x = (1.0F - data.body.scaleX) * 0.25F + (1.0F - data.arms.scaleX) * 0.075F;
            GL11.glTranslatef(x, data.getBodyY(), 0.0F);

            modelBipedMain.bipedRightArm.postRender(0.0625F);

            GL11.glTranslatef(-0.0625F, 0.4375F + y, 0.0625F);

            if (player.fishEntity != null) {
                itemStack = new ItemStack(Items.stick);
            }

            EnumAction enumaction = null;

            if (player.getItemInUseCount() > 0) {
                enumaction = itemStack.getItemUseAction();
            }

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemStack, IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null) && (customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemStack, IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if ((is3D) || (((itemStack.getItem() instanceof ItemBlock)) && (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemStack.getItem()).getRenderType())))) {
                float f3 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f3 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
            } else if (itemStack.getItem() == Items.bow) {
                float f3 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f3, -f3, f3);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else if (itemStack.getItem().isFull3D()) {
                float f3 = 0.625F;

                if (itemStack.getItem().shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                if ((player.getItemInUseCount() > 0) && (enumaction == EnumAction.block)) {
                    GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                }

                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(f3, -f3, f3);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
                float f3 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f3, f3, f3);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            if (itemStack.getItem().requiresMultipleRenderPasses()) {
                for (int k = 0; k <= itemStack.getItem().getRenderPasses(itemStack.getItemDamage()); k++) {
                    int i = itemStack.getItem().getColorFromItemStack(itemStack, k);
                    float f12 = (i >> 16 & 0xFF) / 255.0F;
                    float f4 = (i >> 8 & 0xFF) / 255.0F;
                    float f5 = (i & 0xFF) / 255.0F;
                    GL11.glColor4f(f12, f4, f5, 1.0F);
                    this.renderManager.itemRenderer.renderItem(player, itemStack, k);
                }
            }

            int k = itemStack.getItem().getColorFromItemStack(itemStack, 0);
            float f11 = (k >> 16 & 0xFF) / 255.0F;
            float f12 = (k >> 8 & 0xFF) / 255.0F;
            float f4 = (k & 0xFF) / 255.0F;
            GL11.glColor4f(f11, f12, f4, 1.0F);
            this.renderManager.itemRenderer.renderItem(player, itemStack, 0);

            GL11.glPopMatrix();
        }
    }

    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLiving, float par2, float par3, float par4) {
        EntityPlayer player = (EntityPlayer) par1EntityLiving;
        if (!player.isEntityAlive()) {
            super.rotateCorpse(par1EntityLiving, par2, par3, par4);
            return;
        }

        if (player.ridingEntity != null) {
            GL11.glTranslatef(0.0F, data.getLegsY(), 0.0F);
        }

        if (data.animation == EnumAnimation.SITTING) {
            GL11.glTranslatef(0.0F, -0.6F + data.getLegsY(), 0.0F);
        }
        if (data.animation == EnumAnimation.SLEEPING_EAST) {
            GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(1.6F + data.offsetY(), 0.05F, 0.0F);
            GL11.glRotatef(getDeathMaxRotation(player), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        } else if (data.animation == EnumAnimation.SLEEPING_NORTH) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(1.6F + data.offsetY(), 0.05F, 0.0F);
            GL11.glRotatef(getDeathMaxRotation(player), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        } else if (data.animation == EnumAnimation.SLEEPING_WEST) {
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(1.6F + data.offsetY(), 0.05F, 0.0F);
            GL11.glRotatef(getDeathMaxRotation(player), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        } else if (data.animation == EnumAnimation.SLEEPING_SOUTH) {
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(1.6F + data.offsetY(), 0.05F, 0.0F);
            GL11.glRotatef(getDeathMaxRotation(player), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        } else if (data.animation == EnumAnimation.CRAWLING) {
            GL11.glTranslatef(0.0F, 0.2F, 0.0F);
            super.rotateCorpse(par1EntityLiving, par2, par3, par4);
            GL11.glTranslatef(0.0F, 0.0F, 1.5F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        } else {
            super.rotateCorpse(par1EntityLiving, par2, par3, par4);
        }
    }

    public void renderHelmet(EntityPlayer entityPlayer) {
        ItemStack itemstack = entityPlayer.inventory.armorItemInSlot(3);
        if (itemstack == null)
            return;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, data.getBodyY(), 0.0F);

        modelBipedMain.bipedHead.postRender(0.0625F);

        GL11.glScalef(data.head.scaleX, data.head.scaleY, data.head.scaleZ);

        if ((itemstack.getItem() instanceof ItemBlock)) {
            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null) && (customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D));

            if ((is3D) || (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType()))) {
                float f1 = 0.625F;
                GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f1, -f1, -f1);
            }

            this.renderManager.itemRenderer.renderItem(entityPlayer, itemstack, 0);
        } else if (itemstack.getItem() == Items.skull) {
            float f1 = 1.0625F;
            GL11.glScalef(f1, -f1, -f1);
            GameProfile gameprofile = null;

            if (itemstack.hasTagCompound()) {
                NBTTagCompound nbttagcompound = itemstack.getTagCompound();

                if (nbttagcompound.hasKey("SkullOwner", 10)) {
                    gameprofile = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("SkullOwner"));
                } else if ((nbttagcompound.hasKey("SkullOwner", 8)) && (!StringUtils.isNullOrEmpty(nbttagcompound.getString("SkullOwner")))) {
                    gameprofile = new GameProfile((UUID) null, nbttagcompound.getString("SkullOwner"));
                }
            }

            TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack.getItemDamage(), gameprofile);
        }

        GL11.glPopMatrix();
    }

    public void renderBackitem(EntityPlayer player) {
        ItemStack itemstack = data.backItem;
        if ((itemstack == null) || (ItemStack.areItemStacksEqual(itemstack, player.inventory.getCurrentItem())))
            return;
        Block block = null;
        if ((itemstack.getItem() instanceof ItemBlock)) {
            block = Block.getBlockFromItem(itemstack.getItem());
        }
        if ((itemstack.getItemSpriteNumber() == 0) && (block != null) && (RenderBlocks.renderItemIn3d(block.getRenderType())))
            return;
        GL11.glPushMatrix();
        EntityItem entity = new EntityItem(player.worldObj);
        entity.hoverStart = 0.0F;
        entity.rotationYaw = 0.0F;
        entity.setEntityItemStack(itemstack);

        if (data.animation == EnumAnimation.DANCING) {
            float dancing = player.ticksExisted / 4.0F;
            GL11.glTranslatef((float) Math.sin(dancing) * 0.015F, 0.0F, 0.0F);
        }

        GL11.glTranslatef(0.0F, data.getBodyY(), 0.14299999F * data.body.scaleZ);

        modelBipedMain.bipedBody.postRender(0.065F);

        if (itemstack.getItem() == Items.bow) {
            GL11.glTranslatef(0.0F, -0.195F, 0.0F);
            GL11.glScalef(1.7F, 1.7F, 1.7F);
        }
        if (itemstack.getItem().isFull3D()) {
            GL11.glScalef(1.7F, 1.7F, 1.7F);
        } else {
            GL11.glTranslatef(0.0F, 0.45499998F, 0.0F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        }
        boolean isFancy = this.renderManager.options.fancyGraphics;
        this.renderManager.options.fancyGraphics = true;

        int stack = itemstack.stackSize;
        itemstack.stackSize = 1;

        RenderItem render = (RenderItem) RenderManager.instance.getEntityRenderObject(entity);
        render.doRender(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        GL11.glPopMatrix();

        this.renderManager.options.fancyGraphics = isFancy;
        itemstack.stackSize = stack;
    }

    public void setEntity(EntityLivingBase entity) {
        ModelBase model = null;
        this.renderEntity = null;
        this.entity = entity;
        if (entity != null) {
            this.renderEntity = ((RendererLivingEntity) RenderManager.instance.getEntityRenderObject(entity));
            model = MPMRendererHelper.getMainModel(this.renderEntity);
            this.renderPassModel = this.renderpass;
            this.renderpass.renderer = this.renderEntity;
            this.renderpass.entity = entity;
        }

        modelBipedMain.entityModel = (this.modelArmorChestplate.entityModel = this.modelArmor.entityModel = model);
        modelBipedMain.entity = (this.modelArmorChestplate.entity = this.modelArmor.entity = entity);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entityliving, float f) {
        if (this.renderEntity != null) {
            MPMRendererHelper.renderEquippedItems(this.entity, f, this.renderEntity);
        } else {
            super.renderEquippedItems(entityliving, f);
        }
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        if (this.renderEntity != null) {
            if (this.renderPassModel != null)
                this.renderPassModel.isChild = this.entity.isChild();
            return MPMRendererHelper.shouldRenderPass(this.entity, par2, par3, this.renderEntity);
        }
        return shouldRenderPass((AbstractClientPlayer) par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        if (this.renderEntity != null) {
            MPMRendererHelper.preRenderCallback(this.entity, f, this.renderEntity);
        } else {
            super.preRenderCallback(entityliving, f);
        }
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase par1EntityLivingBase, float par2) {
        if (this.renderEntity != null) {
            return MPMRendererHelper.handleRotationFloat(this.entity, par2, this.renderEntity);
        }
        return super.handleRotationFloat(par1EntityLivingBase, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(AbstractClientPlayer player) {
        if (data.url != null && !data.url.isEmpty())
            return player.getLocationSkin();
        else
            return MPMRendererHelper.getResource(player, this.renderEntity, this.entity);
    }
}
