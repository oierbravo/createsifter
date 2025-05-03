package com.oierbravo.createsifter.content.contraptions.components.sifter.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecBuilders;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SiftingRecipeSerializer implements RecipeSerializer<SiftingRecipe> {
    public static final SiftingRecipeSerializer INSTANCE = new SiftingRecipeSerializer();

    public final StreamCodec<RegistryFriendlyByteBuf, SiftingRecipe> STREAM_CODEC = StreamCodec.of(this::toNetwork, this::fromNetwork);

    private SiftingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        ItemStack mesh = ItemStack.STREAM_CODEC.decode(buffer);
        NonNullList<ProcessingOutput> result = CatnipStreamCodecBuilders.nonNullList(ProcessingOutput.STREAM_CODEC).decode(buffer);
        int processingTime = ByteBufCodecs.VAR_INT.decode(buffer);
        boolean advancedSifter = ByteBufCodecs.BOOL.decode(buffer);
        boolean waterlogged = ByteBufCodecs.BOOL.decode(buffer);
        List<IRecipeRequirement> recipeRequirements = IRecipeRequirement.LIST_STREAM_CODEC.decode(buffer);

        return new SiftingRecipeBuilder()
                .require(input)
                .requiredMesh(mesh)
                .output(result)
                .processingTime(processingTime)
                .requiresAdvancedSifter(advancedSifter)
                .waterlogged(waterlogged)
                .withRequirements(recipeRequirements)
                .build();
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, SiftingRecipe siftingRecipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, siftingRecipe.getInput());
        ItemStack.STREAM_CODEC.encode(buffer, siftingRecipe.getMesh());
        CatnipStreamCodecBuilders.nonNullList(ProcessingOutput.STREAM_CODEC).encode(buffer, siftingRecipe.getResults());
        ByteBufCodecs.VAR_INT.encode(buffer, siftingRecipe.getProcessingTime());
        ByteBufCodecs.BOOL.encode(buffer, siftingRecipe.advancedSifter());
        ByteBufCodecs.BOOL.encode(buffer, siftingRecipe.isWaterlogged());
        IRecipeRequirement.LIST_STREAM_CODEC.encode(buffer, siftingRecipe.getRecipeRequirements());
    }

    public static final MapCodec<SiftingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance
                    .group(
                            Ingredient.CODEC.fieldOf("input").forGetter(SiftingRecipe::getInput),
                            ProcessingOutput.CODEC.listOf().fieldOf("results").forGetter(SiftingRecipe::getResults),
                            ItemStack.CODEC.fieldOf("mesh").forGetter(SiftingRecipe::getMesh),
                            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("processingTime", 0).forGetter(SiftingRecipe::getProcessingTime),
                            Codec.BOOL.optionalFieldOf("advancedSifter", false).forGetter(SiftingRecipe::advancedSifter),
                            Codec.BOOL.optionalFieldOf("waterlogged", false).forGetter(SiftingRecipe::isWaterlogged),
                            IRecipeRequirement.LIST_CODEC.optionalFieldOf("requirements", List.of()).forGetter(SiftingRecipe::getRecipeRequirements),
                            ICondition.LIST_CODEC.optionalFieldOf(ConditionalOps.DEFAULT_CONDITIONS_KEY, List.of()).forGetter(SiftingRecipe::getConditions)
                    ).apply(instance, (input, processingOutput, mesh, processingTime,advancedSifter, waterlogged, requirements, iConditions) -> new SiftingRecipeBuilder()
                            .require(input)
                            .output(processingOutput)
                            .requiredMesh(mesh)
                            .processingTime(processingTime)
                            .requiresAdvancedSifter(advancedSifter)
                            .waterlogged(waterlogged)
                            .withRequirements(requirements)
                            .withConditions(iConditions)
                            .build())
    );


    @Override
    public @NotNull MapCodec<SiftingRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, SiftingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}