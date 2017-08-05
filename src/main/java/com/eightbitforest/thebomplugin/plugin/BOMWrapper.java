package com.eightbitforest.thebomplugin.plugin;

import com.eightbitforest.thebomplugin.TheBOMPlugin;
import com.eightbitforest.thebomplugin.gui.button.GuiIconButton;
import com.eightbitforest.thebomplugin.jei.ingredients.Ingredients;
import com.eightbitforest.thebomplugin.util.BOMCalculator;
import com.eightbitforest.thebomplugin.gui.ItemListGui;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class BOMWrapper implements ICraftingRecipeWrapper {

    private BOMRecipe recipe;
    private IJeiHelpers jeiHelpers;

    private GuiButton hudListButton;
    private GuiButton increaseOutputButton;
    private GuiButton decreaseOutputButton;

    public BOMWrapper(BOMRecipe recipe, IJeiHelpers helpers) {
        this.recipe = recipe;
        this.jeiHelpers = helpers;
        this.hudListButton = new GuiButton(0, 109, 90, 55, 20, "Track");
        this.increaseOutputButton = new GuiIconButton(1, 95, 90, 10, 20, TheBOMPlugin.getInstance().getGuiDrawables().getArrowNext());
        this.decreaseOutputButton = new GuiIconButton(2, 57, 90, 10, 20, TheBOMPlugin.getInstance().getGuiDrawables().getArrowPrevious());
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, recipe.inputs);
        ingredients.setOutput(ItemStack.class, recipe.output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (ItemListGui.isGuiOpen()) {
            hudListButton.displayString = "Untrack";
        }
        else {
            hudListButton.displayString = "Track";
        }
        hudListButton.drawButton(minecraft, mouseX, mouseY, 1f);
        increaseOutputButton.drawButton(minecraft, mouseX, mouseY, 2f);
        decreaseOutputButton.drawButton(minecraft, mouseX, mouseY, 3f);
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        if (hudListButton.mousePressed(minecraft, mouseX, mouseY)) {
            hudListButton.playPressSound(minecraft.getSoundHandler());
            if (ItemListGui.isGuiOpen()) {
                ItemListGui.dismissItems();
            }
            else {
                Ingredients i = new Ingredients();
                getIngredients(i);
                ItemListGui.showItems(BOMCalculator.getBaseIngredients(i));
            }
            return true;
        }
        if (increaseOutputButton.mousePressed(minecraft, mouseX, mouseY)) {
            increaseOutputButton.playPressSound(minecraft.getSoundHandler());
            return true;
        }
        if (decreaseOutputButton.mousePressed(minecraft, mouseX, mouseY)) {
            decreaseOutputButton.playPressSound(minecraft.getSoundHandler());
            return true;
        }

        return false;
    }
}
