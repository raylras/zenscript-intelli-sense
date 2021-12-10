package raylras.zen;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import raylras.zen.lsp.Manager;

@Mod(modid = ZenServerMod.MODID, name = ZenServerMod.NAME, version = ZenServerMod.VERSION, dependencies = ZenServerMod.DEPENDENCIES)
public class ZenServerMod {

    public static final String MODID = "zenserver";
    public static final String NAME = "ZenServer";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES = "required-after:crafttweaker";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent e) {
        Thread t = new Thread(Manager::start);
        t.setName("ZenServer");
        t.start();
    }

}
