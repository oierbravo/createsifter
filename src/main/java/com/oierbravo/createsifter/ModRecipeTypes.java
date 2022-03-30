package com.oierbravo.createsifter;

import com.oierbravo.createsifter.content.contraptions.components.sifter.SiftingRecipe;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder.ProcessingRecipeFactory;

import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegistryEvent;

import java.util.Optional;
import java.util.function.Supplier;

public enum ModRecipeTypes implements IRecipeTypeInfo {
    SIFTING(SiftingRecipe::new);


    private ResourceLocation id;
    private Supplier<RecipeSerializer<?>> serializerSupplier;
    private Supplier<RecipeType<?>> typeSupplier;
    private RecipeSerializer<?> serializer;
    private RecipeType<?> type;

    ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier) {
        this.id = CreateSifter.asResource(Lang.asId(name()));
        this.serializerSupplier = serializerSupplier;
        this.typeSupplier = typeSupplier;
    }

    ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, RecipeType<?> existingType) {
        this(serializerSupplier, () -> existingType);
    }

    ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        this.id = CreateSifter.asResource(Lang.asId(name()));
        this.serializerSupplier = serializerSupplier;
        this.typeSupplier = () -> simpleType(id);
    }

    ModRecipeTypes(ProcessingRecipeFactory<?> processingFactory) {
        this(processingSerializer(processingFactory));
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializer;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T) type;
    }

    public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
        return world.getRecipeManager()
                .getRecipeFor(getType(), inv, world);
    }

    public static void register(RegistryEvent.Register<RecipeSerializer<?>> event) {
        ShapedRecipe.setCraftingSize(9, 9);

        for (ModRecipeTypes r : ModRecipeTypes.values()) {
            r.serializer = r.serializerSupplier.get();
            r.type = r.typeSupplier.get();
            r.serializer.setRegistryName(r.id);
            event.getRegistry()
                    .register(r.serializer);
        }
    }

    private static Supplier<RecipeSerializer<?>> processingSerializer(ProcessingRecipeFactory<?> factory) {
        return () -> new ProcessingRecipeSerializer<>(factory);
    }

    public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
        String stringId = id.toString();
        return Registry.register(Registry.RECIPE_TYPE, id, new RecipeType<T>() {
            @Override
            public String toString() {
                return stringId;
            }
        });
    }

}
