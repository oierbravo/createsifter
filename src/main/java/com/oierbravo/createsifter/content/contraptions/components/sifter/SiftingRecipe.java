package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.google.gson.JsonObject;
import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ModRecipeTypes;
import com.oierbravo.createsifter.content.contraptions.components.meshes.BaseMesh;
import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder.ProcessingRecipeParams;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class SiftingRecipe  extends AbstractCrushingRecipe {

    ItemStack meshStack;
    ItemStack siftableIngredienStack;
    private boolean waterlogged;


    public SiftingRecipe( ProcessingRecipeParams params) {
        super(ModRecipeTypes.SIFTING, params);
        this.meshStack = getMeshItemStack();
        this.siftableIngredienStack = getSiftableItemStack();
        this.waterlogged = false;


    }
    public boolean matches(RecipeWrapper    inv, Level worldIn, boolean waterlogged) {
        if (inv.isEmpty())
            return false;
        if(isWaterlogged() != waterlogged)
            return false;
        return getSiftableIngredient().test(inv.getItem(0)) && getMeshIngredient().test(inv.getItem(1));

    }
    public ItemStack getMeshItemStack(){
        for(int i = 0; i < ingredients.size();i++){
            ItemStack itemStack = ingredients.get(i).getItems()[0];
            if(SiftingRecipe.isMeshItemStack(itemStack))
                return itemStack;
        }
        return ItemStack.EMPTY;

    }
    public ItemStack getSiftableItemStack(){
        for(int i = 0; i < ingredients.size();i++){
            ItemStack itemStack = ingredients.get(i).getItems()[0];
            if(!SiftingRecipe.isMeshItemStack(itemStack))
                return itemStack;
        }
        return ItemStack.EMPTY;

    }
    public Ingredient getSiftableIngredient(){
        for(int i = 0; i < ingredients.size();i++){
            ItemStack itemStack = ingredients.get(i).getItems()[0];
            if(!SiftingRecipe.isMeshItemStack(itemStack))
                return ingredients.get(i);
        }
        return Ingredient.EMPTY;

    }
    public Ingredient getMeshIngredient(){
        for(int i = 0; i < ingredients.size();i++){
            ItemStack itemStack = ingredients.get(i).getItems()[0];
            if(SiftingRecipe.isMeshItemStack(itemStack))
                return ingredients.get(i);
        }
        return Ingredient.EMPTY;

    }
    public static boolean isMeshItemStack(ItemStack itemStack){
        //if(itemStack.getTags().anyMatch(tag -> tag == ModTags.ModItemTags.MESHES.tag ))
        if(itemStack.getItem() instanceof BaseMesh)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return CreateSifter.MODID+":sifting";
    }
    @Override
    protected int getMaxInputCount() {
        return 4;
    }

    @Override
    protected int getMaxOutputCount() {
        return 16;
    }

    public static boolean canHandSift(Level world, ItemStack stack, ItemStack mesh, boolean waterlogged) {
        return getMatchingInHandRecipes(world, stack, mesh, waterlogged);
    }

    public static List<ItemStack> applyHandSift(Level world, Vec3 position, ItemStack stack, ItemStack mesh, boolean waterlogged) {

        RecipeWrapper inventoryIn = new SifterInv(stack,mesh);
        Optional<SiftingRecipe> recipe = ModRecipeTypes.SIFTING.find(inventoryIn, world,waterlogged);

        if(recipe.isPresent()){
            return recipe.get().rollResults();
        }
        return Collections.singletonList(stack);
    }

    @Override
    public List<ItemStack> rollResults(List<ProcessingOutput> rollableResults) {
        List<ItemStack> results = new ArrayList<>();
        for (int i = 0; i < rollableResults.size(); i++) {
            ProcessingOutput output = rollableResults.get(i);
            ItemStack stack =  output.rollOutput();
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }

    @Override
    public void readAdditional(JsonObject json) {
        super.readAdditional(json);
        waterlogged = GsonHelper.getAsBoolean(json, "waterlogged", false);
    }

    @Override
    public void writeAdditional(JsonObject json) {
        super.writeAdditional(json);
        if (waterlogged)
            json.addProperty("waterlogged", waterlogged);
    }

    @Override
    public void readAdditional(FriendlyByteBuf buffer) {
        super.readAdditional(buffer);
        waterlogged = buffer.readBoolean();
    }

    @Override
    public void writeAdditional(FriendlyByteBuf buffer) {
        super.writeAdditional(buffer);
        buffer.writeBoolean(waterlogged);
    }

    public boolean isWaterlogged() {
        return waterlogged;
    }

    public static boolean getMatchingInHandRecipes(Level world, ItemStack stack, ItemStack mesh, boolean waterlogged) {
        return ModRecipeTypes.SIFTING.find( new SiftingRecipe.SifterInv(stack,mesh), world, waterlogged).isPresent();
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        return matches(pContainer, pLevel, false);
    }


    public static class SifterInv extends RecipeWrapper {

        public SifterInv(ItemStack stack, ItemStack mesh) {
            super(new ItemStackHandler(2));
            inv.setStackInSlot(0, stack);
            inv.setStackInSlot(1,mesh);
        }
    }
}
