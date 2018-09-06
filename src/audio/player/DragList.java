
package audio.player;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

public class DragList implements DropTargetListener
{
    JList theList = new JList();
    DefaultListModel defaultMood = new DefaultListModel();
    
    public DragList(JList jList)
    {  
        this.theList = jList;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) 
    {       
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) 
    {       
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde)
    {        
    }

    @Override
    public void dragExit(DropTargetEvent dte) 
    {       
    }

    @Override
    public void drop(DropTargetDropEvent dtde) 
    {
        dtde.acceptDrop(DnDConstants.ACTION_COPY);
        
        Transferable transferable=dtde.getTransferable();
        DataFlavor[] dataFlavors=transferable.getTransferDataFlavors();
        
        for(DataFlavor df : dataFlavors)
        {
            try 
            {
                if(df.isFlavorJavaFileListType())
                {
                    List<File> files=(List<File>) transferable.getTransferData(df);
                    for(File file:files){
                        String path=file.getPath();
                        String Name=file.getName();
                        defaultMood.addElement(path);
                        theList.setModel(defaultMood);        
                    }
                }               
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }     
        }    
    }   
}
