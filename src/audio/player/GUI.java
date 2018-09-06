
package audio.player;

import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame 
{
    JPanel mainP;
    JList theList;
    
    // Buttons Section
    JButton playB;
    JButton pauseB;
    JButton nextB;
    JButton preB;
    JButton stopB;
    JButton soundB;
    JButton muteB;
    JButton rePlay;
    
    //labels section
    JLabel addSong;
    JLabel removeSong;
    JLabel appearTeList;

    //other components section
    JProgressBar progressBar;
    Toolkit toolkit;
    
    //variables section
    boolean playing = false;
    String Song;
    String Name;
    boolean show = true;
    public static int count;
    
    MusicClass MC = new MusicClass();
    
    public GUI()
    {
        guiComponents();
        DragDropList();
    }

    public void DragDropList()
    {
        DragList dl = new DragList(theList);
        new DropTarget(this, dl);    
    }
    
    /////////////////////////////////////////////////////////////////
    
    public void addSongsToList(MouseEvent e)
    {
         FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files", "mp3", "mpeg3");
        
        JFileChooser chooser = new JFileChooser("F:\\");
        chooser.addChoosableFileFilter(filter);
        chooser.setMultiSelectionEnabled(true);
        
        int returnVal = chooser.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File[] myFiles = chooser.getSelectedFiles();
            
            for (File f : myFiles)
            {
                String path = f.getPath();
                
                if(theList.getModel().getSize()==0)
                {
                    DefaultListModel d=new DefaultListModel();
                    d.addElement(path);
                    the:theList.setModel(d);           
                }
                else
                {
                    ((DefaultListModel)theList.getModel()).addElement(path);
                }
            }
        }
    }
    
    /////////////////////////////////////////////////////////////////
    
    private void PLAY(int index)
    {
        theList.setSelectedIndex(index);
        
        String s =theList.getSelectedValue().toString();
        MC.Play(s, progressBar);
    }
    
    /////////////////////////////////////////////////////////////////
    
    public void playBButtonActionPerformed(ActionEvent e) 
    {
        if(playing==false)
        {
            PLAY(theList.getSelectedIndex());
            playing=true;
        }
    }
    
    /////////////////////////////////////////////////////////////////
    
    public void nextBButtonActionPerformed(ActionEvent e) 
    {
        MC.Stop();
        int index = theList.getSelectedIndex() + 1;
        theList.setSelectedIndex(index);
        PLAY(index);
    }
    
    /////////////////////////////////////////////////////////////////
    
    public void preBButtonActionPerformed(ActionEvent e) 
    {
        MC.Stop();
        int index = theList.getSelectedIndex() - 1;
        theList.setSelectedIndex(index);
        PLAY(index);
    }
    
    /////////////////////////////////////////////////////////////////
    
    public void stopBButtonActionPerformed(ActionEvent e) 
    {
        MC.Stop();
        playing = false;
    }
    
    /////////////////////////////////////////////////////////////////
    
     public void pauseBButtonActionPerformed(ActionEvent e) 
     {
        MC.Pause();
        playing = false;
    }
    
    ///////////////////////////////////////////////////////////////// 
     
     public void rePlayButtonActionPerformed(ActionEvent e) 
     {
        switch(count)
        {
            case 0:   
                count = 1;
                break;
                
            case 1:   
                count = 0;
                break;
        } 
    }
     
     ///////////////////////////////////////////////////////////////// 
     
    public void guiComponents()
    {
        //about frame
        
        setTitle("Audio Player");
        setSize(400,350);
        setVisible(true);
        setResizable(false);
        toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation((size.width - getWidth()) / 2, (size.height - getHeight()) / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /////////////////////////////////////////////////////////////////////////////////////

        //about panel
        
        mainP = new JPanel();
        mainP.setLayout(null);
        mainP.setBackground(Color.DARK_GRAY);       
      
        /////////////////////////////////////////////////////////////////////////////////////

        //about buttons
        
        ImageIcon playIcon = new ImageIcon("src\\icons\\play.png");
        ImageIcon stopIcon = new ImageIcon("src\\icons\\stop.png");
        ImageIcon pauseIcon = new ImageIcon("src\\icons\\pause.png");
        ImageIcon nextIcon = new ImageIcon("src\\icons\\next.png");
        ImageIcon preIcon = new ImageIcon("src\\icons\\previous.png");
        ImageIcon soundIcon = new ImageIcon("src\\icons\\sound.png");
        ImageIcon muteIcon = new ImageIcon("src\\icons\\mute.png");
        
        playB = new JButton(playIcon);
        pauseB = new JButton(pauseIcon);
        stopB = new JButton(stopIcon);
        nextB = new JButton(nextIcon);
        preB = new JButton(preIcon);
        soundB = new JButton(soundIcon);
        muteB = new JButton(muteIcon);
        rePlay = new JButton("Replay");
        
        
        
        playB.setBounds(15, 70, 45, 45);
        playB.setBackground(Color.BLACK);
        mainP.add(playB);
        
        pauseB.setBounds(65, 70, 45, 45);
        pauseB.setBackground(Color.BLACK);
        mainP.add(pauseB);
        
        preB.setBounds(125, 70, 45, 45);
        preB.setBackground(Color.BLACK);
        mainP.add(preB);
        
        stopB.setBounds(175, 70, 45, 45);
        stopB.setBackground(Color.BLACK);
        mainP.add(stopB);
        
        nextB.setBounds(225, 70, 45, 45);
        nextB.setBackground(Color.BLACK);
        mainP.add(nextB);
        
        soundB.setBounds(285, 70, 45, 45);
        soundB.setBackground(Color.BLACK);
        mainP.add(soundB);
        
        muteB.setBounds(335, 70, 45, 45);
        muteB.setBackground(Color.BLACK);
        mainP.add(muteB);    
        
        rePlay.setBounds(50, 120, 20, 20);
        rePlay.setBackground(Color.BLACK);
        mainP.add(rePlay); 
        
        this.add(mainP);
        
        /////////////////////////////////////////////////////////////////////////////////////
        
        //about progress bar
        
        progressBar = new JProgressBar();
        mainP.add(progressBar);
        progressBar.setBounds(15, 15, 370, 30);
        progressBar.setBackground(Color.LIGHT_GRAY);
        
        /////////////////////////////////////////////////////////////////////////////////////
        
        //about the list
        
        theList = new JList();
        JScrollPane JSP = new JScrollPane(theList);
        JSP.setBounds(11, 150, 370, 160);
        mainP.add(JSP);
        
        theList.setBounds(11, 150, 370, 160);
        theList.setBackground(Color.LIGHT_GRAY);
        theList.setDragEnabled(true);

        /////////////////////////////////////////////////////////////////////////////////////
        
        //about labels
        
        addSong = new JLabel("+");
        mainP.add(addSong);
        addSong.setBackground(Color.DARK_GRAY);
        addSong.setForeground(Color.LIGHT_GRAY);
        addSong.setBounds(343, 130, 20, 20);
        addSong.setFont(new Font("+", Font.BOLD, 25));
        
        removeSong = new JLabel("-");
        mainP.add(removeSong);
        removeSong.setBackground(Color.DARK_GRAY);
        removeSong.setForeground(Color.LIGHT_GRAY);
        removeSong.setBounds(360, 127, 20, 20);
        removeSong.setFont(new Font("-", Font.BOLD, 25));
        
        appearTeList = new JLabel("▼");
        mainP.add(appearTeList);
        appearTeList.setBackground(Color.DARK_GRAY);
        appearTeList.setForeground(Color.LIGHT_GRAY);
        appearTeList.setBounds(15, 120, 30, 30);
        appearTeList.setFont(new Font("▼", Font.BOLD, 25));       
        
        
        /////////////////////////////////////////////////////////////////////////////////////
        
        //about Action listeners for components
        
        playB.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                playBButtonActionPerformed(e);
            }
        });           
        /////////////////////////////////////////////////////////////////////////////////////
        stopB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                stopBButtonActionPerformed(e);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////                              
        pauseB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                pauseBButtonActionPerformed(e);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////
        nextB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                nextBButtonActionPerformed(e);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////
        preB.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                preBButtonActionPerformed(e);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////           
        rePlay.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                rePlayButtonActionPerformed(e);       
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////   

        theList.addMouseListener(new MouseAdapter() 
        {           
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2) 
                {
                    MC.Stop();
                    PLAY(theList.getSelectedIndex());
                }
            }
        });
       ///////////////////////////////////////////////////////////////////////////////////// 
        addSong.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) 
            {
                addSongsToList(e);
            } 
        });
        /////////////////////////////////////////////////////////////////////////////////////
        removeSong.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e) 
            {
                int index = theList.getSelectedIndex();
                ((DefaultListModel) theList.getModel()).remove(index);
               
                theList.setSelectedIndex(index);
                removeSong.setForeground(Color.DARK_GRAY);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) 
            {
                removeSong.setForeground(Color.LIGHT_GRAY);
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////
        
        appearTeList.addMouseListener(new MouseAdapter() 
        {
           public void mousePressed(MouseEvent e) 
            {
                if(show == true)
                {
                    setSize(400,178);
                    theList.setVisible(false); 
                    show = false;
                } 
                else if(show == false)
                {
                    setSize(400,350);
                    theList.setVisible(true); 
                    show = true;
                }        
            }                      
        });
        
        /////////////////////////////////////////////////////////////////////////////////////
    }
}










