package halcyonics.blocks;


import halcyonics.multiblock.AbstractMultiBlockTileEntityContainer;
import halcyonics.tileEntity.ColliderBlockControllerTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import halcyonics.enums.MultiBlockEnum;

/**
 * Created by Niel on 3/21/2016.
 */
public class ColliderBlockController extends AbstractMultiBlockTileEntityContainer {

    public ColliderBlockController(String unlocalisedName) {
        super(unlocalisedName);
    }

    @Override
    public boolean isValidForFrame() {
        return false;
    }

    @Override
    public boolean isValidForPanel() {
        return true;
    }

    @Override
    public boolean isValidForContent() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }

        if (((MultiBlockEnum.EnumType) state.getValue(STRUCTURE)).getID() == 0) {
            playerIn.addChatComponentMessage(new ChatComponentText("Last structure error: " + ((ColliderBlockControllerTileEntity) worldIn.getTileEntity(pos)).getReactor().getLastErrorMessage()));
        } else {
            playerIn.addChatComponentMessage(new ChatComponentText("---Collider Overview---"));
            playerIn.addChatComponentMessage(new ChatComponentText(""));
            playerIn.addChatComponentMessage(new ChatComponentText("Energy: " + ((ColliderBlockControllerTileEntity) worldIn.getTileEntity(pos)).getEnergyStored() + "/" + ((ColliderBlockControllerTileEntity) worldIn.getTileEntity(pos)).getMaxEnergyStored()));
            playerIn.addChatComponentMessage(new ChatComponentText(""));

        }
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        if(worldIn.getTileEntity(pos) instanceof ColliderBlockControllerTileEntity)
        {
            ((ColliderBlockControllerTileEntity)worldIn.getTileEntity(pos)).deconstructMultiBlock();
        }
        super.breakBlock(worldIn, pos, state);
    }


    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new ColliderBlockControllerTileEntity();
    }
}
