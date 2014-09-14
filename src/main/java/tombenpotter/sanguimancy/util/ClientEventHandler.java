package tombenpotter.sanguimancy.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;

public class ClientEventHandler {

    @SubscribeEvent
    public void prePlayerRender(RenderLivingEvent.Pre event) {
        NBTTagCompound tag = SoulCorruptionHelper.getModTag(Minecraft.getMinecraft().thePlayer, Sanguimancy.modid);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 15)) {
            GL11.glPushMatrix();
            GL11.glDisable(2929);
            GL11.glColor3f(125, 0, 0);
            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public void postPlayerRender(RenderLivingEvent.Post event) {
        NBTTagCompound tag = SoulCorruptionHelper.getModTag(Minecraft.getMinecraft().thePlayer, Sanguimancy.modid);
        if (SoulCorruptionHelper.isCorruptionOver(tag, 15)) {
            GL11.glPushMatrix();
            GL11.glEnable(2929);
            GL11.glPopMatrix();
        }
    }
}
