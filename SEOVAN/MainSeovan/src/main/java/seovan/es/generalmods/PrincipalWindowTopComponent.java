/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/templateTopComponent637.java to edit this template
 */
package seovan.es.generalmods;

import beleris.es.finaldbmanager.FDBMan;
import beleris.es.finalutils.DSelectXMLDlg;
import static beleris.es.finalutils.HelpUtils.HU;
import beleris.es.finalinformationmanager.LoggingManagerGenerator;
import static beleris.es.finalinformationmanager.MainErrorManager.AcceptInformation;
import static beleris.es.finalinformationmanager.MainErrorManager.ShowDError;
import static beleris.es.finalinformationmanager.MainErrorManager.ShowDInfo;
import static beleris.es.finalinformationmanager.MainErrorManager.mconfyes;
import java.awt.Dimension;
import java.awt.Frame;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.OPEN_DIALOG;
import static javax.swing.JFileChooser.SAVE_DIALOG;
import javax.swing.UIManager;
import org.apache.commons.io.FileUtils;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.LifecycleManager;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import seovan.es.dialogs.SearchingDlg;
import static seovan.es.generalmods.ProgramInstaller.Instalador;
import seovan.es.importation.XMLDataImport;
import seovan.es.panels.JSourceAnalisysPanel;
import seovan.es.panels.JSourceEvaluationPanel;
import seovan.es.programconfig.PConfigManager;
import seovan.es.pdfutils.SoevanPDFManager;
import static seovan.es.programconfig.PConfigManager.PConfig;

/**
 *
 * @author Emilio David Diaus López 2023-2025
 */
/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//seovan.es.generalmods//PrincipalWindow//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PrincipalWindowTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "seovan.es.generalmods.PrincipalWindowTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PrincipalWindowAction",
        preferredID = "PrincipalWindowTopComponent"
)
@Messages({
    "CTL_PrincipalWindowAction=Ventana Principal",
    "CTL_PrincipalWindowTopComponent=Ventana Principal",
    "HINT_PrincipalWindowTopComponent=Esta es la ventana principal"
})

/**
 *
 * @author edavid
 */
public final class PrincipalWindowTopComponent extends TopComponent {

    private final FDBMan DBMSAP;
    private static final long serialVersionUID = 1630L;
    //PConfigManager PConfig = new PConfigManager();
    //ProgramInstaller Instalador = new ProgramInstaller();

    // HelpUtils HelpManager = new HelpUtils();
    /**
     *
     */
    public PrincipalWindowTopComponent() {
        UIManager.put("Button.arc", 999);
        UIManager.put("Component.arc", 999);
        UIManager.put("ProgressBar.arc", 999);
        UIManager.put("TextComponent.arc", 999);
        UIManager.put("Component.arrowType", "triangle");
//        DBMSAP = new DBManager20("jdbc:mysql://localhost:3306/desidaniespsources", PConfig.getConfPassDB(), "desidaniespsources", PConfig.getConfUserDB());
        initComponents();
        System.setProperty("program.name", "SEOVAN");
        LoggingManagerGenerator LMG = new LoggingManagerGenerator();
        if (PConfig == null) {
            PConfig = new PConfigManager();
        }

        PConfig.setDatabaseManagerinUse(FDBMan.DB_MYSQL_Selected);
        setDBToUse(FDBMan.DB_MYSQL_Selected);
        /*   if (!PConfig.ProgramConfigured()) {
            Instalador.CreateProgramDatabases();
            PConfig.InstalationComplete();
        }
        if (PConfig.ProgramConfigured()) {
            Instalador.ReadConfiguration();
        }*/
        DBMSAP = new FDBMan("jdbc:mysql://localhost:3306/desidaniespsources", PConfig.getConfPassDB(), "desidaniespsources", PConfig.getConfUserDB(), PConfig.getDatabaseManagerinUse());

        IndexDataTotal = getRecordsCount();
        JSAP = new JSourceAnalisysPanel(IndexItemSource, IndexDataTotal, getDBSystemToUse());
        JSEP = new JSourceEvaluationPanel(IndexItemSource, IndexDataTotal, getDBSystemToUse());

        //   UIManager.put("ScrollBar.width", 40);
        InnerScrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(
                (int) (InnerScrollPanel.getVerticalScrollBar().getPreferredSize()
                        .getWidth() * 1.2),
                (int) InnerScrollPanel.getVerticalScrollBar().getPreferredSize().getHeight()
        ));
        InnerScrollPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(
                (int) InnerScrollPanel.getHorizontalScrollBar().getPreferredSize()
                        .getWidth(),
                (int) (InnerScrollPanel.getHorizontalScrollBar().getPreferredSize().getHeight() * 1.2)
        ));
        //InnerScrollPanel.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        //InnerScrollPanel.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        //InnerScrollPanel.setBounds(0, 0, 1500, 2550);

        jInnerSurfacePanel.setBounds(0, 0, 1500, 2550);
        jInnerSurfacePanel.setMaximumSize(new Dimension(1500, 2550));
        jInnerSurfacePanel.repaint();
        jInnerSurfacePanel.add(JSAP);
        // jScrollAnalPanel.setLayout(this.getLayout());
        setName(Bundle.CTL_PrincipalWindowTopComponent());
        ChangeTitle();
        WindowManager.getDefault().getMainWindow().setExtendedState(MAXIMIZED_BOTH);
        setToolTipText(Bundle.HINT_PrincipalWindowTopComponent());
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
        //  ToolbarPool.getDefault().findToolbar("File").addSeparator(new Dimension(20, 20));
        //ToolbarPool.getDefault().findToolbar("Edit").addSeparator(new Dimension(20, 20));
        // ToolbarPool.getDefault().findToolbar("View").addSeparator(new Dimension(20, 20));
        //  ToolbarPool.getDefault().findToolbar("Navigate").addSeparator(new Dimension(20, 20));
        // ToolbarPool.getDefault().findToolbar("Tools").addSeparator(new Dimension(20, 20));
        // ToolbarPool.getDefault().findToolbar("Help").addSeparator(new Dimension(20, 20));
        //ToolbarPool.getDefault().findToolbar("Windows").addSeparator(new Dimension(20, 20));
        HU.setHelpConfig("Ayuda sensible al contexto", "PrincipalWindowTopComponent", "PrincipalWindowTopComponent.html");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        JActionButtonsPanel = new javax.swing.JPanel();
        jExitProgram = new javax.swing.JButton();
        jAnalButton = new javax.swing.JButton();
        jEvalButton = new javax.swing.JButton();
        jSurfacePanel = new javax.swing.JPanel();
        jButttonsPanel = new javax.swing.JPanel();
        ShowAnalReg = new javax.swing.JTextField();
        jBCreateReg = new javax.swing.JButton();
        jBUpdateReg = new javax.swing.JButton();
        jBAnalDeleteReg = new javax.swing.JButton();
        jFirstReg = new javax.swing.JButton();
        jPreviousReg = new javax.swing.JButton();
        jNextReg = new javax.swing.JButton();
        jLastReg = new javax.swing.JButton();
        jBExit = new javax.swing.JButton();
        InnerScrollPanel = new javax.swing.JScrollPane();
        jInnerSurfacePanel = new javax.swing.JPanel();

        setBackground(java.awt.Color.lightGray);
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 4, true));
        setForeground(java.awt.Color.black);
        setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.toolTipText")); // NOI18N
        setMaximumSize(new java.awt.Dimension(1618, 825));
        setMinimumSize(new java.awt.Dimension(1618, 825));
        setLayout(new java.awt.GridBagLayout());

        JActionButtonsPanel.setMaximumSize(new java.awt.Dimension(1500, 50));
        JActionButtonsPanel.setMinimumSize(new java.awt.Dimension(1500, 50));
        JActionButtonsPanel.setOpaque(false);
        JActionButtonsPanel.setPreferredSize(new java.awt.Dimension(1500, 50));
        JActionButtonsPanel.setLayout(new java.awt.GridBagLayout());

        jExitProgram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/iconos botones seovan-s.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jExitProgram, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jExitProgram.text")); // NOI18N
        jExitProgram.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jExitProgram.toolTipText")); // NOI18N
        jExitProgram.setActionCommand(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jExitProgram.actionCommand")); // NOI18N
        jExitProgram.setMaximumSize(new java.awt.Dimension(300, 50));
        jExitProgram.setMinimumSize(new java.awt.Dimension(300, 50));
        jExitProgram.setPreferredSize(new java.awt.Dimension(300, 50));
        jExitProgram.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/iconos botones seovan-s-i.png"))); // NOI18N
        jExitProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jExitProgramActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        JActionButtonsPanel.add(jExitProgram, gridBagConstraints);

        jAnalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/iconos botones seovan-a.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jAnalButton, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jAnalButton.text")); // NOI18N
        jAnalButton.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jAnalButton.toolTipText")); // NOI18N
        jAnalButton.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jAnalButton.setMaximumSize(new java.awt.Dimension(300, 50));
        jAnalButton.setMinimumSize(new java.awt.Dimension(300, 50));
        jAnalButton.setPreferredSize(new java.awt.Dimension(300, 50));
        jAnalButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/iconos botones seovan-a-i.png"))); // NOI18N
        jAnalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAnalButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        JActionButtonsPanel.add(jAnalButton, gridBagConstraints);

        jEvalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/iconos botones seovan-e.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jEvalButton, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jEvalButton.text")); // NOI18N
        jEvalButton.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jEvalButton.toolTipText")); // NOI18N
        jEvalButton.setMaximumSize(new java.awt.Dimension(300, 50));
        jEvalButton.setMinimumSize(new java.awt.Dimension(300, 50));
        jEvalButton.setPreferredSize(new java.awt.Dimension(300, 50));
        jEvalButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/iconos botones seovan-e-i.png"))); // NOI18N
        jEvalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEvalButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        JActionButtonsPanel.add(jEvalButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(JActionButtonsPanel, gridBagConstraints);

        jSurfacePanel.setBorder(new javax.swing.border.MatteBorder(null));
        jSurfacePanel.setMaximumSize(new java.awt.Dimension(1550, 645));
        jSurfacePanel.setMinimumSize(new java.awt.Dimension(1550, 645));
        jSurfacePanel.setOpaque(false);
        jSurfacePanel.setPreferredSize(new java.awt.Dimension(1550, 645));
        jSurfacePanel.setLayout(new java.awt.GridBagLayout());

        jButttonsPanel.setMaximumSize(new java.awt.Dimension(1535, 50));
        jButttonsPanel.setMinimumSize(new java.awt.Dimension(1535, 50));
        jButttonsPanel.setName(""); // NOI18N
        jButttonsPanel.setPreferredSize(new java.awt.Dimension(1535, 50));
        jButttonsPanel.setRequestFocusEnabled(false);
        jButttonsPanel.setLayout(new java.awt.GridLayout(1, 0));

        ShowAnalReg.setEditable(false);
        ShowAnalReg.setText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.ShowAnalReg.text")); // NOI18N
        ShowAnalReg.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.ShowAnalReg.toolTipText")); // NOI18N
        ShowAnalReg.setMaximumSize(new java.awt.Dimension(150, 50));
        ShowAnalReg.setMinimumSize(new java.awt.Dimension(150, 50));
        ShowAnalReg.setPreferredSize(new java.awt.Dimension(150, 50));
        jButttonsPanel.add(ShowAnalReg);

        org.openide.awt.Mnemonics.setLocalizedText(jBCreateReg, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jBCreateReg.text")); // NOI18N
        jBCreateReg.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jBCreateReg.toolTipText")); // NOI18N
        jBCreateReg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBCreateReg.setMaximumSize(new java.awt.Dimension(150, 50));
        jBCreateReg.setMinimumSize(new java.awt.Dimension(150, 50));
        jBCreateReg.setOpaque(true);
        jBCreateReg.setPreferredSize(new java.awt.Dimension(150, 50));
        jBCreateReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCreateRegActionPerformed(evt);
            }
        });
        jButttonsPanel.add(jBCreateReg);

        org.openide.awt.Mnemonics.setLocalizedText(jBUpdateReg, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jBUpdateReg.text")); // NOI18N
        jBUpdateReg.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jBUpdateReg.toolTipText")); // NOI18N
        jBUpdateReg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBUpdateReg.setMaximumSize(new java.awt.Dimension(150, 50));
        jBUpdateReg.setMinimumSize(new java.awt.Dimension(150, 50));
        jBUpdateReg.setOpaque(true);
        jBUpdateReg.setPreferredSize(new java.awt.Dimension(150, 50));
        jBUpdateReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUpdateRegActionPerformed(evt);
            }
        });
        jButttonsPanel.add(jBUpdateReg);

        jBAnalDeleteReg.setBackground(java.awt.Color.orange);
        jBAnalDeleteReg.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jBAnalDeleteReg, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jBAnalDeleteReg.text")); // NOI18N
        jBAnalDeleteReg.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jBAnalDeleteReg.toolTipText")); // NOI18N
        jBAnalDeleteReg.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.red, 4));
        jBAnalDeleteReg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBAnalDeleteReg.setMaximumSize(new java.awt.Dimension(150, 50));
        jBAnalDeleteReg.setMinimumSize(new java.awt.Dimension(150, 50));
        jBAnalDeleteReg.setOpaque(true);
        jBAnalDeleteReg.setPreferredSize(new java.awt.Dimension(150, 50));
        jBAnalDeleteReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAnalDeleteRegActionPerformed(evt);
            }
        });
        jButttonsPanel.add(jBAnalDeleteReg);

        jFirstReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/TI.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jFirstReg, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jFirstReg.text")); // NOI18N
        jFirstReg.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jFirstReg.toolTipText")); // NOI18N
        jFirstReg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jFirstReg.setMaximumSize(new java.awt.Dimension(150, 50));
        jFirstReg.setMinimumSize(new java.awt.Dimension(150, 50));
        jFirstReg.setOpaque(true);
        jFirstReg.setPreferredSize(new java.awt.Dimension(150, 50));
        jFirstReg.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/I-TI.png"))); // NOI18N
        jFirstReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFirstRegActionPerformed(evt);
            }
        });
        jButttonsPanel.add(jFirstReg);

        jPreviousReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/I.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jPreviousReg, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jPreviousReg.text_1")); // NOI18N
        jPreviousReg.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jPreviousReg.toolTipText")); // NOI18N
        jPreviousReg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPreviousReg.setMaximumSize(new java.awt.Dimension(150, 50));
        jPreviousReg.setMinimumSize(new java.awt.Dimension(150, 50));
        jPreviousReg.setOpaque(true);
        jPreviousReg.setPreferredSize(new java.awt.Dimension(150, 50));
        jPreviousReg.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/I-I.png"))); // NOI18N
        jPreviousReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPreviousRegActionPerformed(evt);
            }
        });
        jButttonsPanel.add(jPreviousReg);

        jNextReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/D.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jNextReg, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jNextReg.text")); // NOI18N
        jNextReg.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jNextReg.toolTipText")); // NOI18N
        jNextReg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jNextReg.setMaximumSize(new java.awt.Dimension(150, 50));
        jNextReg.setMinimumSize(new java.awt.Dimension(150, 50));
        jNextReg.setOpaque(true);
        jNextReg.setPreferredSize(new java.awt.Dimension(150, 50));
        jNextReg.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/D-I.png"))); // NOI18N
        jNextReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNextRegActionPerformed(evt);
            }
        });
        jButttonsPanel.add(jNextReg);

        jLastReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/TD.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLastReg, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jLastReg.text")); // NOI18N
        jLastReg.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jLastReg.toolTipText")); // NOI18N
        jLastReg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLastReg.setMaximumSize(new java.awt.Dimension(150, 50));
        jLastReg.setMinimumSize(new java.awt.Dimension(150, 50));
        jLastReg.setOpaque(true);
        jLastReg.setPreferredSize(new java.awt.Dimension(150, 50));
        jLastReg.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/I-D.png"))); // NOI18N
        jLastReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLastRegActionPerformed(evt);
            }
        });
        jButttonsPanel.add(jLastReg);

        jBExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/X.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jBExit, org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jBExit.text")); // NOI18N
        jBExit.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jBExit.toolTipText")); // NOI18N
        jBExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBExit.setMaximumSize(new java.awt.Dimension(150, 50));
        jBExit.setMinimumSize(new java.awt.Dimension(150, 50));
        jBExit.setOpaque(true);
        jBExit.setPreferredSize(new java.awt.Dimension(150, 50));
        jBExit.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/seovan/es/generalmods/X-I.png"))); // NOI18N
        jBExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExitActionPerformed(evt);
            }
        });
        jButttonsPanel.add(jBExit);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jSurfacePanel.add(jButttonsPanel, gridBagConstraints);

        InnerScrollPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.blue));
        InnerScrollPanel.setAutoscrolls(true);
        InnerScrollPanel.setColumnHeaderView(null);
        InnerScrollPanel.setDoubleBuffered(true);
        InnerScrollPanel.setMaximumSize(new java.awt.Dimension(1535, 570));
        InnerScrollPanel.setMinimumSize(new java.awt.Dimension(1535, 570));
        InnerScrollPanel.setPreferredSize(new java.awt.Dimension(1535, 570));
        InnerScrollPanel.setRowHeaderView(null);
        InnerScrollPanel.setViewportView(null);

        jInnerSurfacePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jInnerSurfacePanel.setToolTipText(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.jInnerSurfacePanel.toolTipText")); // NOI18N
        jInnerSurfacePanel.setMaximumSize(new java.awt.Dimension(1535, 4500));
        jInnerSurfacePanel.setMinimumSize(new java.awt.Dimension(1535, 4500));
        jInnerSurfacePanel.setPreferredSize(new java.awt.Dimension(1535, 4500));
        InnerScrollPanel.setViewportView(jInnerSurfacePanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jSurfacePanel.add(InnerScrollPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jSurfacePanel, gridBagConstraints);

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.AccessibleContext.accessibleName")); // NOI18N
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PrincipalWindowTopComponent.class, "PrincipalWindowTopComponent.AccessibleContext.accessibleDescription")); // NOI18N
        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    JSourceAnalisysPanel JSAP = null;
    JSourceEvaluationPanel JSEP = null;
    String ImportDocumentPath = "";


    private void jExitProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jExitProgramActionPerformed
        ExitProgram();
    }//GEN-LAST:event_jExitProgramActionPerformed
    void ExitProgram() {
        int ntf = AcceptInformation("¿Está seguro de que quiere salir del programa?");
        if (ntf == mconfyes) {
            Instalador.WriteConfiguration();
            LifecycleManager.getDefault().exit();
        }
    }
    private void jAnalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAnalButtonActionPerformed
        SelectAnalisysPanel();
    }//GEN-LAST:event_jAnalButtonActionPerformed

    private void jEvalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEvalButtonActionPerformed
        SelectEvaluationPanel();

    }//GEN-LAST:event_jEvalButtonActionPerformed

    private void jBCreateRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCreateRegActionPerformed
        // TODO add your handling code here:
        JSAP.CreateAnalysisDataItem(IndexItemSource + 1);
        JSEP.CreateEvaluationDataItem(IndexItemSource + 1);
        IndexItemSource++;
    }//GEN-LAST:event_jBCreateRegActionPerformed

    private void jBUpdateRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUpdateRegActionPerformed

        JSAP.UpdateAnalysisDataItem(IndexItemSource);
        JSEP.UpdateEvaluationDataItem(IndexItemSource);
    }//GEN-LAST:event_jBUpdateRegActionPerformed

    private void jBAnalDeleteRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAnalDeleteRegActionPerformed

        String AInfo = String.format("Está seguro de que quiere borrar el registro %d ", IndexItemSource);
        int result = AcceptInformation(AInfo);
        if (result == mconfyes) {
            JSAP.DBDeleteAnalRecord(IndexItemSource);
            JSEP.DBDeleteEvalRecord(IndexItemSource);
        }
    }//GEN-LAST:event_jBAnalDeleteRegActionPerformed

    private void jFirstRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFirstRegActionPerformed
        //DBMSAP.goFirst();
        IndexItemSource = 1;
        ShowDataItem(IndexItemSource);
    }//GEN-LAST:event_jFirstRegActionPerformed

    private void jPreviousRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPreviousRegActionPerformed
        //DBMSAP.goPrevious();
        IndexItemSource--;
        ShowDataItem(IndexItemSource);
    }//GEN-LAST:event_jPreviousRegActionPerformed

    private void jNextRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNextRegActionPerformed
        if (IndexItemSource < IndexDataTotal) {
            //DBMSAP.goNext();
            IndexItemSource++;
            ShowDataItem(IndexItemSource);
        }

    }//GEN-LAST:event_jNextRegActionPerformed

    private void jLastRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLastRegActionPerformed
        //DBMSAP.goLast();
        IndexItemSource = IndexDataTotal;
        ShowDataItem(IndexItemSource);
    }//GEN-LAST:event_jLastRegActionPerformed

    private void jBExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExitActionPerformed
        DBMSAP.Close();
        LifecycleManager.getDefault().exit();
    }//GEN-LAST:event_jBExitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane InnerScrollPanel;
    private javax.swing.JPanel JActionButtonsPanel;
    private javax.swing.JTextField ShowAnalReg;
    private javax.swing.JButton jAnalButton;
    private javax.swing.JButton jBAnalDeleteReg;
    private javax.swing.JButton jBCreateReg;
    private javax.swing.JButton jBExit;
    private javax.swing.JButton jBUpdateReg;
    private javax.swing.JPanel jButttonsPanel;
    private javax.swing.JButton jEvalButton;
    private javax.swing.JButton jExitProgram;
    private javax.swing.JButton jFirstReg;
    private javax.swing.JPanel jInnerSurfacePanel;
    private javax.swing.JButton jLastReg;
    private javax.swing.JButton jNextReg;
    private javax.swing.JButton jPreviousReg;
    private javax.swing.JPanel jSurfacePanel;
    // End of variables declaration//GEN-END:variables

    ///  JSourceAnalisysPanel APanel = new JSourceAnalisysPanel();
    // JSourceEvaluationPanel EvPanel = new JSourceEvaluationPanel();
//   JPanel AnalisisPanel;
    //   JPanel EvalPanel;
    int IndexItemSource = 1;
    int IndexDataTotal = 1;
    private int DBToUse = 1;

    private XMLDataImport XMLDI = null;
    String XMLTemplate = "";
    String ProgramPath = "";
    String sTheTitle = "SEOVAN 4.0b beta (c) Emilio David Diaus Lopez 2023-2026";
    String XMLHeader = """
                       <?xml version='1.0' encoding='UTF-8' ?>
                       <!-- was: <?xml version="1.0" encoding="ISO-8859-1"?> -->
                       <import_schema xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="importxmlschema.xsd">>""";
    String XMLEnd = "</import_schema>";

    /**
     *
     */
    @Override
    public void componentOpened() {

        LoggingManagerGenerator.getGlobalLogger().info(sTheTitle);
        DBToUse = PConfig.getDatabaseManagerinUse();
        ShowDataItem(IndexItemSource);
    }

    /**
     *
     */
    public void SelectandImportDocument() {
        Frame f = WindowManager.getDefault().getMainWindow();
        DSelectXMLDlg SelectFileDlg = new DSelectXMLDlg(f, true);
        SelectFileDlg.setTitle("Seleccione el fichero a importar");
        SelectFileDlg.GetFileChooser().setToolTipText("Seleccione el fichero a importar");
        SelectFileDlg.GetFileChooser().setDialogType(OPEN_DIALOG);
        SelectFileDlg.setLocationRelativeTo(f);
        SelectFileDlg.setVisible(true);
        JFileChooser FCH = SelectFileDlg.GetFileChooser();
        ImportDocumentPath = FCH.getSelectedFile().getAbsolutePath();
        XMLDI = new XMLDataImport(ImportDocumentPath);

    }

    /**
     *
     */
    public void ImportAnalisysData() {
        IndexDataTotal = getRecordsCount();
        JSAP.ImportAnalisysPanelData(XMLDI, IndexDataTotal, getDBSystemToUse());
    }

    /**
     *
     */
    public void ImportEvalData() {
        JSEP.ImportEvalPanelData(XMLDI, IndexDataTotal, getDBSystemToUse());
    }

    /**
     *
     */
    public void SelectEvaluationPanel() {
        jInnerSurfacePanel.removeAll();
        // JScrollPane JSP = new JScrollPane(JSEP);
        //JSP.setBounds(1, 1, 1500, 2500);
        // JSP.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        //  JSP.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jInnerSurfacePanel.setBounds(0, 0, 1500, 4550);
        jInnerSurfacePanel.setMaximumSize(new Dimension(1500, 2550));
        jInnerSurfacePanel.repaint();
        jInnerSurfacePanel.add(JSEP);
        jInnerSurfacePanel.repaint();
    }

    /**
     *
     */
    public void SelectAnalisysPanel() {
        jInnerSurfacePanel.removeAll();
        //JScrollPane JSP = new JScrollPane(JSAP);
        //JSP.setBounds(1, 1, 1500, 2500);
        // JSP.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        //JSP.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        jInnerSurfacePanel.setBounds(0, 0, 1500, 2550);
        jInnerSurfacePanel.setMaximumSize(new Dimension(1500, 2550));
        jInnerSurfacePanel.repaint();
        jInnerSurfacePanel.add(JSAP);
        jInnerSurfacePanel.repaint();
    }

    /**
     *
     * @param sFecha
     * @return
     */
    public Date ConvierteFecha(String sFecha) {

        SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
        Date rdate = null;
        try {
            rdate = date_format.parse(sFecha);
        } catch (ParseException ex) {
            Exceptions.printStackTrace(ex);
        }

        return rdate;

    }

    void ChangeTitle() {
        WindowManager.getDefault().getMainWindow().setTitle(sTheTitle);
    }

    /**
     *
     * @param item
     */
    public void DeleteAnalysisDataItem(int item) {
        boolean error = false;
        /*   ElementsRecordJpaController erjc = new ElementsRecordJpaController(PM.getTheEMFactory());
        try {
            erjc.destroy(item);
        }
        catch (NonexistentEntityException ex) {
            Exceptions.printStackTrace(ex);
            ShowDError(ex.getLocalizedMessage());
        }
        if (!error) {
            ShowDInfo("Registro de Análisis Borrado");
        }*/
    }

    /**
     *
     */
    @Override
    public void componentClosed() {
        ExitProgram();

    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");

    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");

    }

    private void ShowDataItem(int itmpIndexItemSource) {
        String RegStr = String.format(" Registro %d / %d ", itmpIndexItemSource, IndexDataTotal);
        ShowAnalReg.setText(RegStr);
        //   RegStr = String.format(" Registro %d / %d ", itmpIndexItemSource, IndexDataTotal);
        //  ShowAnalReg.setText(RegStr);
        //   ShowAnalReg.repaint();

    }

    int getRecordsCount() {
        int returnvalue = 1;
        returnvalue = DBMSAP.getLastRow("elements_record");
        return returnvalue;
    }

    /**
     *
     */
    public void LoadXMLTemplate() {
        File myfile = null;
        ProgramPath = PConfig.getProgramPath();
        myfile = new File(ProgramPath + "/config/xml-export-template.xml");
        try {
            XMLTemplate = FileUtils.readFileToString(myfile, "UTF-8");
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    /**
     *
     */
    public void UpdateXMLTemplate() {
        String uptemplate = XMLTemplate;
        String XMLAnalPart = "";
        String XMLEvalPart = "";
        String XMLIndexHeaderChanged = "";
        String FullDoc = "";
        IndexDataTotal = getRecordsCount();

        for (int tmpitem = 1; tmpitem <= IndexDataTotal; tmpitem++) {
            JSAP.LoadDBDatatoPanel(tmpitem);
            JSEP.LoadDBDatatoPanel(tmpitem);
            String fieldtochange = String.format("%04d", tmpitem);
            XMLIndexHeaderChanged = uptemplate.replace("[recid]", fieldtochange);
            XMLAnalPart = JSAP.ExportAnalisysPanelData(XMLIndexHeaderChanged);
            XMLEvalPart = JSEP.ExportEvalPanelData(XMLAnalPart);
            FullDoc += XMLEvalPart;
        }
        String tmpXMLTemplate = XMLHeader + FullDoc + XMLEnd;
        XMLTemplate = tmpXMLTemplate;
    }

    String CheckandCorrectExtension(String tmpPath, String tmpExtension) {
        tmpPath = tmpPath.toLowerCase();
        int idx = tmpPath.lastIndexOf('.');
        if (idx == -1 || idx == 0) {
            tmpPath = tmpPath + "." + tmpExtension;
        }
        return tmpPath;
    }

    /**
     *
     */
    public void ExportXMLDocument() {

        String filepath = "xml-export-template.xml";
        Frame f = WindowManager.getDefault().getMainWindow();
        DSelectXMLDlg SelectFile = new DSelectXMLDlg(f, true);
        int ExportXML = DSelectXMLDlg.getExportXML();
        SelectFile.setXmlorpdf(ExportXML);
        SelectFile.Configure_filters();
        SelectFile.setTitle("Seleccione el nombre del fichero a exportar");
        SelectFile.GetFileChooser().setDialogTitle("Seleccione el nombre del fichero a exportar");
        SelectFile.GetFileChooser().setDialogType(SAVE_DIALOG);
        SelectFile.setLocationRelativeTo(f);
        SelectFile.setVisible(true);
       // UpdateXMLTemplate();
        JFileChooser FCH = SelectFile.GetFileChooser();
        String tmpfilepath = FCH.getSelectedFile().getAbsolutePath();
        File myfile = null;
        filepath = CheckandCorrectExtension(tmpfilepath, "xml");
        myfile = new File(filepath);
        try {
            FileUtils.writeStringToFile(myfile, XMLTemplate, "UTF-8");
        } catch (IOException ex) {
            ShowDError(" Error al crear fichero XML" + ex.getLocalizedMessage());
        }
        XMLTemplate = "";
    }

    /**
     *
     */
    public void ExportPDFDocument() {

        String filepath = "pdf-export-template.xml";
        Frame f = WindowManager.getDefault().getMainWindow();
        int ExportPDF = DSelectXMLDlg.getExportPDF();
        DSelectXMLDlg SelectFile = new DSelectXMLDlg(f, true);
        SelectFile.setXmlorpdf(ExportPDF);
        SelectFile.Configure_filters();
        SelectFile.setTitle("Seleccione el nombre del fichero a exportar");
        SelectFile.GetFileChooser().setDialogTitle("Seleccione el nombre del fichero a exportar");
        SelectFile.GetFileChooser().setDialogType(SAVE_DIALOG);
        SelectFile.setLocationRelativeTo(f);
        SelectFile.setVisible(true);

        JFileChooser FCH = SelectFile.GetFileChooser();
        String tmpfilepath = FCH.getSelectedFile().getAbsolutePath();
        filepath = CheckandCorrectExtension(tmpfilepath, "pdf");
        SoevanPDFManager sfm = new SoevanPDFManager(filepath);
        sfm.CreatePDFDocuments();
    }

    /**
     *
     */
    public void ReloadData() {
        IndexDataTotal = getRecordsCount();
        ShowDataItem(IndexItemSource);
    }

    /**
     * @return the DBToUse
     */
    public int getDBSystemToUse() {
        return DBToUse;
    }

    /**
     * @param DabataasetoUse the DBToUse to set
     */
    public void setDBToUse(int DabataasetoUse) {
        this.DBToUse = DabataasetoUse;
    }

    /**
     *
     */
    public void SearchingElement() {
        Frame f = WindowManager.getDefault().getMainWindow();
        SearchingDlg sdlg = new SearchingDlg(f, true, IndexItemSource);
        sdlg.setLocationRelativeTo(f);
        sdlg.setVisible(true);
        if (sdlg.getSearcheditem() != -1 && sdlg.isEncontrado()) {
            ShowDInfo("Cadena encontrada en el registro: " + Integer.toString(sdlg.getSearcheditem()));
            IndexItemSource = sdlg.getSearcheditem();
        } else {
            ShowDError("Cadena no encontrada");
        }
    }

}
