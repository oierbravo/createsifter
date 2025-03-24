package com.oierbravo.createsifter.content.contraptions.components.meshes;

public interface IAdvancedMesh extends IMesh {
    default boolean isAdvanced(){
        return true;
    }
}
