
package audio.player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JProgressBar;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class MusicClass 
{
    FileInputStream FIS;
    BufferedInputStream BIS;
    
    public AdvancedPlayer player;
    
    public long pauseLocation;
    long songTotal;
    int avilabe;
    
    Thread theThread;
    boolean Paused;

    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
    
    public void Stop()
    {
        if(player!=null)
        {
            Paused=false;
            player.close(); 
        }
    }
    
    //////////////////////////////////////////////////////////
    
     public void Play(String path , JProgressBar progressBar)
    {    
        try 
        {            
            FIS = new FileInputStream(path);
            BIS = new BufferedInputStream(FIS);
            player = new AdvancedPlayer(BIS);
            songTotal = FIS.available();     
        }
        catch (JavaLayerException | FileNotFoundException ex) 
        {
            System.out.println(""+ex);
        } 
        catch (IOException ex) 
        {   
            System.out.println(""+ex);

        } 
        
        theThread = new Thread(new Runnable() 
        {
            @Override
            public void run() {
               try 
                {
                    if(Paused == false)
                    {
                        player.play();
                    }
                    else if(Paused == true)
                    {
                        Paused=false;
                        BIS.skip(songTotal - pauseLocation);
                        player.play();  
                    }  
                } 
                catch (JavaLayerException | IOException ex)
                   
                {
                    System.out.println(""+ex);
                }    
            }
        });
        theThread.start();
        
        //about progress bar
        
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum((int)songTotal);//????
        
        File songFile = new File(path);
        progressBar.setString(songFile.getName());
        
        new Thread(new Runnable() 
        {
            public void run() 
            {
                while(theThread.isAlive())
                {
                    try
                    {
                        progressBar.setValue((int)(songTotal-BIS.available())); 
                    } 
                    catch (IOException ex) 
                    {
                        System.out.println(""+ex);
                    }
                }
            }
        }).start();  
    }
     
    //////////////////////////////////////////////////////////
     
    public void Pause()
    {
        if(player!=null)
        {
            Paused = true;
            
            try 
            {
                pauseLocation = BIS.available();
                player.close();
            } 
            catch (IOException ex) 
            {
            }           
        }
    }
    
    ////////////////////////////////////////////////////////// 
}
