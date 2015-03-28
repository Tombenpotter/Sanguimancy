package tombenpotter.sanguimancy.api.tile;

import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.api.objects.ICustomNBTTag;
import tombenpotter.sanguimancy.api.objects.Timer;
import tombenpotter.sanguimancy.compat.lua.events.LuaEvent;
import tombenpotter.sanguimancy.compat.lua.methods.LuaMethod;
import tombenpotter.sanguimancy.util.enums.ModList;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


@Optional.InterfaceList({
        @Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = ModList.Names.COMPUTERCRAFT),
        @Optional.Interface(iface = "li.cil.oc.api.network.Environment", modid = ModList.Names.OPENCOMPUTERS),
        @Optional.Interface(iface = "li.cil.oc.api.network.ManagedPeripheral", modid = ModList.Names.OPENCOMPUTERS)
})
public abstract class TileComputerBase extends TileEntity implements ManagedPeripheral, Environment, IPeripheral, ICustomNBTTag {
    protected final String name;
    protected final Map<Integer, String> methodIDs = new LinkedHashMap<Integer, String>();
    protected final Map<String, LuaMethod> methodNames = new LinkedHashMap<String, LuaMethod>();
    protected final Map<Timer, LuaEvent> events = new LinkedHashMap<Timer, LuaEvent>();
    private final Object node = ModList.opencomputers.isLoaded() ? this.createNode() : null;
    private boolean initialize = true;
    private Set<Object> computers = new LinkedHashSet<Object>();
    private Set<Object> context = new LinkedHashSet<Object>();

    public TileComputerBase(String name) {
        this.name = name;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) serverUpdate();
        if (initialize) init();
    }

    public void init() {
        if (ModList.opencomputers.isLoaded()) {
            if (node instanceof Component && ((Component) node).network() == null)
                Network.joinOrCreateNetwork(this);
        }
        initialize = false;
    }

    public void serverUpdate() {
        for (Timer timer : events.keySet()) {
            if (timer.update()) events.get(timer).checkEvent(this);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (ModList.opencomputers.isLoaded()) {
            if (node instanceof Component)
                ((Component) node).load(compound);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (ModList.opencomputers.isLoaded()) {
            if (node instanceof Component)
                ((Component) node).save(compound);
        }
    }

    //####################Peripheral Stuff################

    public String[] getMethods() {
        return methodNames.keySet().toArray(new String[methodNames.size()]);
    }

    public void addMethod(LuaMethod method) {
        if (ModList.computercraft.isLoaded() || ModList.opencomputers.isLoaded()) {
            int num = methodIDs.size();
            if (!methodNames.containsKey(method.getMethodName())) {
                methodIDs.put(num, method.getMethodName());
                methodNames.put(method.getMethodName(), method);
            }
        }
    }

    public void addEvent(LuaEvent event, int count) {
        if (ModList.computercraft.isLoaded() || ModList.opencomputers.isLoaded())
            events.put(new Timer(count), event);
    }

    public String getType() {
        return name;
    }

    //####################ComputerCraft####################

    @Override
    @Optional.Method(modid = ModList.Names.COMPUTERCRAFT)
    public String[] getMethodNames() {
        return this.getMethods();
    }

    @Override
    @Optional.Method(modid = ModList.Names.COMPUTERCRAFT)
    public Object[] callMethod(IComputerAccess iComputerAccess, ILuaContext iLuaContext, int i, Object[] objects) throws LuaException, InterruptedException {
        try {
            return methodIDs.containsKey(i) ? methodNames.get(methodIDs.get(i)).call(this, objects) : null;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    @Optional.Method(modid = ModList.Names.COMPUTERCRAFT)
    public void attach(IComputerAccess iComputerAccess) {
        computers.add(iComputerAccess);
    }

    @Override
    @Optional.Method(modid = ModList.Names.COMPUTERCRAFT)
    public void detach(IComputerAccess iComputerAccess) {
        computers.remove(iComputerAccess);
    }

    @Override
    @Optional.Method(modid = ModList.Names.COMPUTERCRAFT)
    public boolean equals(IPeripheral iPeripheral) {
        return false;
    }

    @Optional.Method(modid = ModList.Names.COMPUTERCRAFT)
    public Set<Object> getComputers() {
        return computers;
    }

    //####################OpenComputers####################

    public String getComponentName() {
        return this.getType();
    }

    @Override
    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public String[] methods() {
        return this.getMethods();
    }

    @Override
    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public Object[] invoke(String method, Context context, Arguments args) throws Exception {
        Object[] objs = new Object[args.count()];
        for (int i = 0; i < objs.length; i++) {
            objs[i] = args.checkAny(i);
        }
        return methodNames.containsKey(method) ? methodNames.get(method).call(this, objs) : null;
    }

    @Override
    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public final void onChunkUnload() {
        super.onChunkUnload();
        if (ModList.opencomputers.isLoaded()) {
            if (node instanceof Component)
                ((Component) node).remove();
        }
        this.onInvalidateOrUnload(worldObj, xCoord, yCoord, zCoord, false);
    }

    @Override
    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public final void invalidate() {
        super.invalidate();
        if (node instanceof Component)
            ((Component) node).remove();
        this.onInvalidateOrUnload(worldObj, xCoord, yCoord, zCoord, true);
    }

    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    protected void onInvalidateOrUnload(World world, int x, int y, int z, boolean invalid) {
    }

    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    private Node createNode() {
        return Network.newNode(this, Visibility.Network).withComponent(this.getType(), this.getOCNetworkVisibility()).create();
    }

    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    protected Visibility getOCNetworkVisibility() {
        return Visibility.Network;
    }

    @Override
    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public Node node() {
        return (Node) node;
    }

    @Override
    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public void onConnect(Node node) {
        if (node.host() instanceof Context) {
            context.add(node.host());
        }
    }

    @Override
    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public void onDisconnect(Node node) {
        if (node.host() instanceof Context) {
            context.remove(node.host());
        }
    }

    @Override
    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public void onMessage(Message message) {
    }

    @Optional.Method(modid = ModList.Names.OPENCOMPUTERS)
    public Set<Object> getContext() {
        return context;
    }
}
