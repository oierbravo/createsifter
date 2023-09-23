package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.google.gson.JsonObject;
import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ModRecipeTypes;
import com.oierbravo.createsifter.content.contraptions.components.meshes.AdvancedBaseMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.BaseMesh;
import com.oierbravo.createsifter.foundation.data.recipe.SiftingRecipeBuilder.SiftingRecipeParams;
import com.oierbravo.createsifter.content.contraptions.components.meshes.IMesh;
import com.oierbravo.createsifter.foundation.data.recipe.SiftingRecipeBuilder.SiftingRecipeParams;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.minecraft.core.NonNullList;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.lang.reflect.Constructor;

@ParametersAreNonnullByDefault
public class SiftingRecipe  extends AbstractCrushingRecipe {

    public NonNullList<ProcessingOutput> results;
    ItemStack meshStack;
    ItemStack siftableIngredienStack;
    private boolean waterlogged;
    private float minimumSpeed;
    private boolean advanced;



    @Override
    public SiftingRecipeSerializer getSerializer() {
        return ModRecipeTypes.SIFTING.getSerializer();
    }

    public SiftingRecipe(SiftingRecipeParams params) {

        super(ModRecipeTypes.SIFTING, params);
        this.processingDuration = params.processingDuration;
        this.ingredients = params.ingredients;
        this.results = params.results;
        this.id = params.id;
        this.meshStack = getMeshItemStack();
        this.advanced = isAdvancedMesh(this.meshStack);
        this.siftableIngredienStack = getSiftableItemStack();
        this.waterlogged = params.waterlogged;
        this.minimumSpeed = params.minimumSpeed;

    }


    public boolean matches(RecipeWrapper    inv, Level worldIn, boolean waterlogged, float speed, boolean advanced) {
        if (inv.isEmpty())
            return false;
        if(isWaterlogged() != waterlogged)
            return false;
        if(hasSpeedRequeriment() && speed < minimumSpeed)
            return false;
        if(advanced && meshStack.getItem() instanceof BaseMesh){
            return false;
        }
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
        if(itemStack.getItem() instanceof IMesh)
            return true;
        return false;
    }
    private boolean isAdvancedMesh(ItemStack meshStack){
        return this.meshStack.getItem() instanceof AdvancedBaseMesh;
    }
    public boolean requiresAdvancedMesh(){
        return advanced;
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
        return SifterConfig.SIFTER_OUTPUT_CAPACITY.get();
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

    public List<ProcessingOutput> getRollableResults() {
        return results;
    }
    public List<ItemStack> rollResults(List<ProcessingOutput> rollableResults) {
        return super.rollResults(rollableResults);
    }
    public List<ItemStack> rollResults() {
        return rollResults(this.getRollableResults());
    }
    /*@Override
    public List<ItemStack> rollResults(List<ProcessingOutput> rollableResults) {
        List<ItemStack> results = new ArrayList<>();
        for (int i = 0; i < rollableResults.size(); i++) {
            ProcessingOutput output = rollableResults.get(i);
            ItemStack stack =  output.rollOutput();
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }*/


    @Override
    public void readAdditional(JsonObject json) {
        super.readAdditional(json);
        waterlogged = GsonHelper.getAsBoolean(json, "waterlogged", false);
        minimumSpeed = GsonHelper.getAsFloat(json, "minimumSpeed", 1);
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
        if(this.minimumSpeed > SifterBlockEntity.DEFAULT_MINIMUM_SPEED){
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
        return matches(pContainer, pLevel, false,0,false);
    }

    public float getMinimumSpeed() {
        return minimumSpeed;
    }


    public static class SifterInv extends RecipeWrapper {

        public SifterInv(ItemStack stack, ItemStack mesh) {
            super(new ItemStackHandler(2));
            inv.setStackInSlot(0, stack);
            inv.setStackInSlot(1,mesh);
        }
    }
}
