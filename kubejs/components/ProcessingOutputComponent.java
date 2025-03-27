package com.oierbravo.createsifter.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.ArrayList;
import java.util.List;

public record ProcessingOutputComponent() implements RecipeComponent<ProcessingOutput> {
    public static final RecipeComponent<ProcessingOutput> OUTPUT = new ProcessingOutputComponent();

    @Override
    public Codec<ProcessingOutput> codec() {
        return ProcessingOutput.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(ProcessingOutput.class).or(ItemStackJS.TYPE_INFO);
    }

    /*public static final RecipeComponent<List<ProcessingOutput>> UNWRAPPED_OUTPUT_LIST = RecipeComponentType.unit(KubeJS.id("unwrapped_processing_output_list"), new RecipeComponentWithParent<>() {
        private static final RecipeComponent<List<ProcessingOutput>> PARENT = OUTPUT.instance().asList();
        private static final TypeInfo WRAP_TYPE = TypeInfo.RAW_LIST.withParams(TypeInfo.of(ProcessingOutput.class));



        @Override
        public RecipeComponentType<?> type() {
            return UNWRAPPED_OUTPUT_LIST;
        }

        @Override
        public RecipeComponent<List<ProcessingOutput>> parentComponent() {
            return PARENT;
        }

        @Override
        public List<ProcessingOutput> wrap(Context cx, KubeRecipe recipe, Object from) {
            if (from instanceof ProcessingOutput o) {
                return List.of(o);
            }
            if (from instanceof ItemStack o) {
                return List.of(new ProcessingOutput(o,1));
            }
            var list = new ArrayList<ProcessingOutput>();

            //for (var in : (Iterable<ProcessingOutput>) cx.jsToJava(from, WRAP_TYPE)) {
            for (var in : (List<ProcessingOutput>) cx.jsToJava(from, WRAP_TYPE)) {
                int a= 0;
                *//*for (int i = 0; i < in.size(); i++) {
                    list.add((ProcessingOutput) in.get(i));
                }*//*
            }

            return list;
        }
    });*/

    /*public ProcessingOutput wrap(Context cx, KubeRecipe recipe, Object from) {
        if (from instanceof ProcessingOutput o) {
            return o;
        }

    }*/
    /*@Override
    public ProcessingOutput wrap(Context cx, KubeRecipe recipe, Object from) {
        if (from instanceof ProcessingOutput o) {
            return o;
        }*/

        /*RegistryAccessContainer registryAccess = ((KubeJSContext) cx).getRegistries();

        SizedIngredient sizedIngredient = SizedIngredientWrapper.wrap(registryAccess, from);
        var ingredientValues = ((IngredientAccessor) (Object) sizedIngredient.ingredient()).kubeio$getValues();
        if (ingredientValues.length > 1) {
            throw new IllegalArgumentException("compound ingredients not supported in sag mill output: " + from);
        }

        var ingredientValue = ingredientValues[0];
        if (ingredientValue instanceof Ingredient.TagValue tagValue) {
            var tag = ((TagValueAccessor) (Object) tagValue).kubeio$getTag();
            return SagMillOutputItem.kubeio$ofTag(tag, sizedIngredient.count());
        }

        if (ingredientValue instanceof Ingredient.ItemValue itemValue) {
            var items = itemValue.getItems();
            if (items.size() > 1) {
                throw new IllegalArgumentException("compound ingredients not supported in sag mill output: " + from);
            }

            ItemStack itemStack = new ItemStack(items.iterator().next().getItem(), sizedIngredient.count());
            return SagMillOutputItem.kubeio$of(itemStack);
        }

        ItemStack itemStack = ItemStackJS.wrap(registryAccess, from);
        if (itemStack.isEmpty()) {
            throw new IllegalArgumentException("empty sag mill output: " + from);
        }

        return SagMillOutputItem.kubeio$of(itemStack);
    }*/
}