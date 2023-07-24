package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.google.gson.JsonObject;
import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ModRecipeTypes;
import com.oierbravo.createsifter.content.contraptions.components.meshes.BaseMesh;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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
    private float minimumSpeed;

    public SiftingRecipe( ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(ModRecipeTypes.SIFTING, params);
        this.meshStack = getMeshItemStack();
        this.siftableIngredienStack = getSiftableItemStack();
        this.waterlogged = false;
        this.minimumSpeed = SifterBlockEntity.DEFAULT_MINIMUM_SPEED;

    }
    public boolean matches(RecipeWrapper    inv, Level worldIn, boolean waterlogged, float speed) {
        if (inv.isEmpty())
            return false;
        if(isWaterlogged() != waterlogged)
            return false;
        if(hasSpeedRequeriment() && speed < minimumSpeed)
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
        return getMatchingInHandRecipes(world, stack, mesh, waterlogged,0);
    }

    public static List<ItemStack> applyHandSift(Level world, Vec3 position, ItemStack stack, ItemStack mesh, boolean waterlogged) {

        RecipeWrapper inventoryIn = new SifterInv(stack,mesh);
        Optional<SiftingRecipe> recipe = ModRecipeTypes.SIFTING.find(inventoryIn, world,waterlogged,0);

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
        minimumSpeed = GsonHelper.getAsFloat(json, "minimumSpeed", SifterBlockEntity.DEFAULT_MINIMUM_SPEED);
    }

    @Override
    public void writeAdditional(JsonObject json) {
        super.writeAdditional(json);
        if (waterlogged)
            json.addProperty("waterlogged", waterlogged);
        if(hasSpeedRequeriment()){
            json.addProperty("minimumSpeed", minimumSpeed);
        }
    }

    @Override
    public void readAdditional(FriendlyByteBuf buffer) {
        super.readAdditional(buffer);
        waterlogged = buffer.readBoolean();
        minimumSpeed = buffer.readFloat();
    }

    @Override
    public void writeAdditional(FriendlyByteBuf buffer) {
        super.writeAdditional(buffer);
        buffer.writeBoolean(waterlogged);
        buffer.writeFloat(minimumSpeed);
    }

    public boolean isWaterlogged() {
        return waterlogged;
    }

    public boolean hasSpeedRequeriment(){
        if(this.minimumSpeed > 0){
            return true;
        }
        return false;
    }
    public float getSpeedRequeriment(){
        return this.minimumSpeed;
    }

    public static boolean getMatchingInHandRecipes(Level world, ItemStack stack, ItemStack mesh, boolean waterlogged, float speed) {
        return ModRecipeTypes.SIFTING.find( new SiftingRecipe.SifterInv(stack,mesh), world, waterlogged, speed).isPresent();
    }

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        return matches(pContainer, pLevel, false,0);
    }


    public static class SifterInv extends RecipeWrapper {

        public SifterInv(ItemStack stack, ItemStack mesh) {
            super(new ItemStackHandler(2));
            inv.setStackInSlot(0, stack);
            inv.setStackInSlot(1,mesh);
        }
    }
}
