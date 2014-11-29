package tombenpotter.sanguimancy.compat.lua.methods;

public abstract class LuaMethod implements ILuaMethod{

    private final String methodName;

    public LuaMethod(String methodName){
        this.methodName = methodName;
    }

    @Override
    public String getMethodName(){
        return methodName;
    }
    
    @Override
    public String getArgs() {
    	return "()";
    }
}
