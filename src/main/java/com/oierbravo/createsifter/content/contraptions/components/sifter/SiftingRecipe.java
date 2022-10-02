package com.oierbravo.createsifter.content.contraptions.components.sifter;

import com.oierbravo.createsifter.CreateSifter;
import com.oierbravo.createsifter.ModRecipeTypes;
import com.oierbravo.createsifter.content.contraptions.components.meshes.BaseMesh;
import com.oierbravo.createsifter.content.contraptions.components.meshes.MeshTypes;
import com.oierbravo.createsifter.register.ModTags;
import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder.ProcessingRecipeParams;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
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


    public SiftingRecipe( ProcessingRecipeParams params) {
        super(ModRecipeTypes.SIFTING, params);
        this.meshStack = getMeshItemStack();
        this.siftableIngredienStack = getSiftableItemStack();

    }
    public boolean matches(RecipeWrapper    inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return siftableIngredienStack.is(inv.getItem(0).getItem()) &&
                meshStack.is(inv.getItem(1).getItem());

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
        if(itemStack.getTags().anyMatch(tag -> tag == ModTags.ModItemTags.MESHES.tag ))
            return true;
        return false;
    }
    /*@Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {

        if (inv.isEmpty())
            return false;

        return ingredients.get(0)
                .test(inv.getItem(0));
    }*/
    /*public boolean matches(RecipeWrapper pInv, Level pLevel) {
        if (pInv.isEmpty())
            return false;
        StackedContents stackedcontents = new StackedContents();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for(int j = 0; j < pInv.getContainerSize(); ++j) {
            ItemStack itemstack = pInv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                if (isSimple)
                    stackedcontents.accountStack(itemstack, 1);
                else inputs.add(itemstack);
            }
        }

        return i == this.ingredients.size() && (isSimple ? stackedcontents.canCraft(this, (IntList)null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null);
    }*/
    //From ShapelessRecipe
    //@Override
    //@Override
    /*public boolean matches(SifterInv inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return ingredients.get(0)
                .test(inv.getItem(0)) && ( inv.getMeshType() == mesh);
    }*/
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

    public static boolean canHandSift(Level world, ItemStack stack, ItemStack mesh) {
        return !getMatchingInHandRecipes(world, stack, mesh).isEmpty();
    }

    public static List<ItemStack> applyHandSift(Level world, Vec3 position, ItemStack stack, ItemStack mesh) {

        RecipeWrapper inventoryIn = new SifterInv(stack,mesh);
        Optional<SiftingRecipe> recipe = ModRecipeTypes.SIFTING.find(inventoryIn, world);

        if(!recipe.isEmpty()){
            List<ItemStack> results = recipe.get().rollResults();
            return results;
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

    public static List<Recipe<SiftingRecipe.SifterInv>> getMatchingInHandRecipes(Level world, ItemStack stack, ItemStack mesh) {
        return world.getRecipeManager()
                .getRecipesFor(ModRecipeTypes.SIFTING.getType(), new SiftingRecipe.SifterInv(stack,mesh), world);
    }



    public static class SifterInv extends RecipeWrapper {

        public SifterInv(ItemStack stack, ItemStack mesh) {
            super(new ItemStackHandler(2));
            inv.setStackInSlot(0, stack);
            inv.setStackInSlot(1,mesh);
        }
    }
}
