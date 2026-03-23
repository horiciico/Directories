import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class Directories extends Frame
{

     ScrollPane scroll;//pt scroll
     Panel treePanel;//panelul efectiv
     ArrayList<String> file_path = new ArrayList<>();//pt path
     Label pathLabel = new Label("Calea curentă...");

    public Directories() {
        super("File Explorer");

        setLayout(new BorderLayout());
        add(pathLabel, BorderLayout.NORTH);
        pathLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        treePanel = new Panel();
        treePanel.setLayout(new GridBagLayout());

        scroll = new ScrollPane();
        scroll.add(treePanel);
        add(scroll, BorderLayout.CENTER);

        showRoots(); //afiseaza radacinile

        setSize(600, 400);
        setVisible(true);

        //inchide fereastra
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }



    public static void main(String[] args)
    {
        new Directories();
    }



    void showRoots()
    {
        File[] _radacini_ = File.listRoots();//lista radacinilor
        GridBagConstraints gbc = new GridBagConstraints();

        //ceva de grafica
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        for (File root : _radacini_)
        {

            String rootName = root.getAbsolutePath();
            Label rootLabel = new Label(rootName);
            rootLabel.setForeground(Color.RED);
            rootLabel.setFont(new Font("Arial", Font.PLAIN, 24));



            Panel rootPanel = new Panel();
            rootPanel.setLayout(new GridBagLayout());

            //grafica
            GridBagConstraints innerGbc = new GridBagConstraints();
            innerGbc.gridx = 0;
            innerGbc.gridy = 0;
            innerGbc.anchor = GridBagConstraints.WEST;
            innerGbc.fill = GridBagConstraints.HORIZONTAL;
            innerGbc.weightx = 1.0;

            rootPanel.add(rootLabel, innerGbc);

            Panel childrenPanel = new Panel();
            childrenPanel.setLayout(new GridBagLayout());
            innerGbc.gridy = 1;
            rootPanel.add(childrenPanel, innerGbc);

            //ataseaza un event dubluclick
            DoubleClick(rootLabel, root, childrenPanel, 0);

            treePanel.add(rootPanel, gbc);
        }

        // se adauga un panel gol la sfarsit ca sa se impinga continutul in sus
        Panel filler = new Panel();
        gbc.weighty = 1.0;
        treePanel.add(filler, gbc);

        treePanel.revalidate();
        treePanel.repaint();
    }



    //creeaza un obiect de tip panel care reprezinta un nod in treeview
    Panel createNode(File f, int level) {
        Panel nodePanel = new Panel();
        nodePanel.setLayout(new GridBagLayout());

        //grafica
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Label nodeLabel = new Label(" ".repeat(level * 4) + f.getName());
        nodePanel.add(nodeLabel, gbc);
        nodeLabel.setFont(new Font("Arial", Font.PLAIN, 24)); //seteaza fontul si dimensiunea


        Panel sub_panel = new Panel();
        sub_panel.setLayout(new GridBagLayout());
        gbc.gridy = 1;
        nodePanel.add(sub_panel, gbc);

        if (f.isDirectory())
        {
            DoubleClick(nodeLabel, f, sub_panel, level);//ataseaza un event dubluclick
            String []continut=f.list();
            if(continut==null || continut.length==0) nodeLabel.setForeground(Color.GREEN);
            else
                nodeLabel.setForeground(Color.BLACK);
        }
        else nodeLabel.setForeground(Color.BLUE);

        return nodePanel;
    }


    //se creeaza un event dubluclick care se adauga la toate nodurile care sunt directoare
    void DoubleClick(Label nodeLabel, File f, Panel sub_panel, int level)
    {
        nodeLabel.addMouseListener(new MouseAdapter()
        {
            private boolean expanded = false;

            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    pathLabel.setText("Calea curenta: "+f.getAbsolutePath());

                    if (expanded)
                    {
                        sub_panel.removeAll();
                        file_path.remove(f.getAbsolutePath());
                        expanded = false;
                    }
                    else
                    {
                        file_path.add(f.getAbsolutePath());

                        File[] subDirs = f.listFiles();
                        if (subDirs != null)
                        {
                            //grafica
                            GridBagConstraints gbc = new GridBagConstraints();
                            gbc.gridx = 0;
                            gbc.gridy = GridBagConstraints.RELATIVE;
                            gbc.anchor = GridBagConstraints.WEST;
                            gbc.fill = GridBagConstraints.HORIZONTAL;
                            gbc.weightx = 1.0;

                            for (File sub : subDirs)
                                sub_panel.add(createNode(sub, level + 1), gbc);
                        }
                        expanded = true;
                    }

                    sub_panel.revalidate();
                    sub_panel.repaint();
                    treePanel.revalidate();
                    treePanel.repaint();
                }
            }
        });
    }
}
