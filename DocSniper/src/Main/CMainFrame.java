package Main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JLabel;

public class CMainFrame extends JFrame {

	/**
	 * serialized version UID
	 */
	private static final long serialVersionUID = 1L;
	private static final String CHECK_BOX_NAME_1 = "IEEE Explore";
	private static final String CHECK_BOX_NAME_2 = "Science Direct";
	private static final String CHECK_BOX_NAME_3 = "Sigous";
	private static final String CHECK_BOX_NAME_4 = "Springer";
	
	private static final String SESSION_DIR_NAME = "dd-MM-yyyy_HH-mm-ss";
	
	private JPanel m_oContentPane = null;
	static private CMainFrame m_oMainFrame = null;
	private JTextField m_oFileSearchTextField = null;
	private JTextField m_oKeywordSearchTestField = null;
	private JButton m_oSearchNewDocButton = null;
	private JScrollPane m_oScrollPane = null;
	private JButton m_oSearchKeyword = null;
	private JPanel m_oPanel = null;
	private GroupLayout m_oGroupLayout = null;
	private JTextPane m_oCheckBoxTitle = null;
	private JCheckBox m_oCheckBox_1 = null;
	private JCheckBox m_oCheckBox_2 = null;
	private JCheckBox m_oCheckBox_3 = null;
	private JCheckBox m_oCheckBox_4 = null;
	private GroupLayout m_oCheckBoxGroupLayout = null;
	
	JTextPane m_oTitleTextPane  = null;
	JTextPane m_oJournalTextPane = null;
	JTextPane m_oVolumeTextPane = null;
	JTextPane m_oNumberTextPane = null;
	JTextPane m_oPagesTextPane = null;
	JTextPane m_oYearTextPane = null;
	JTextPane m_oNoteTextPane = null;
	JTextPane m_oISSNTextPane = null;
	JTextPane m_oDOITextPane = null;
	JTextPane m_oURLTextPane = null;
	JTextPane m_oAuthorTextPane = null;
	JTextPane m_oKeywordsTextPane = null;
	JTextPane m_oAbstractTextPane = null;
	
	JScrollPane m_oScrollAuthor = null;
	JScrollPane m_oScrollAbstract = null;
	JScrollPane m_oScrollTitle = null;
	
	JLabel m_oTitleLabel = null;
	JLabel m_oJournalLabel = null;
	JLabel m_oVolumeLabel = null;
	JLabel m_oNumberLabel = null;
	JLabel m_oPagesLabel = null;
	JLabel m_oYearLabel = null;
	JLabel m_oNoteLabel = null;
	JLabel m_oISSNLabel = null;
	JLabel m_oDOILabel = null;
	JLabel m_oURLLabel = null;
	JLabel m_oAuthorLabel = null;
	JLabel m_oKeywordsLabel = null;
	JLabel m_oAbstractLabel = null;
	
	DefaultMutableTreeNode m_oTreeModel = null;
	JTree m_oTree = null;
	
	private CSessionsContainer m_oSessions = null;
	private HashMap<Integer, CDocument> m_oDocumentsToSearch = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CLogger.createLogFile();
					m_oMainFrame = new CMainFrame();
					CLogger.log(CLogger.INFO, "Main window is created");
					m_oMainFrame.setVisible(true);
					CLogger.log(CLogger.INFO, "Main window is visable");
				} catch (Exception e) {
					CLogger.log(CLogger.INFO, "Can not create main window " + e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CMainFrame() 
	{
		m_oSessions = new CSessionsContainer();
		m_oSessions.loadRepo();
		m_oDocumentsToSearch = new HashMap<>();
		setMinimumSize(new Dimension(800, 700));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 800, 700);
		setResizable(false);
		m_oContentPane = new JPanel();
		m_oContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(m_oContentPane);
		
		m_oFileSearchTextField = new JTextField();
		m_oFileSearchTextField.setColumns(10);
		
		m_oSearchNewDocButton = new JButton("Search new documents");
		m_oSearchNewDocButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent a_oAction) 
			{
				SimpleDateFormat oDateFormat = new SimpleDateFormat(SESSION_DIR_NAME);
				String strSessionSufixName = oDateFormat.format(new Date());
				String strSessionDirectoryName = "sessions\\sess_" + strSessionSufixName;
				File oDirectory = new File(strSessionDirectoryName);
				oDirectory.mkdirs();
				// TODO for Mariusz
				/**
				 * Here should be executed module to search documents list
				 * parameters:
 				 * @param String strSessionDirectoryName - path to session directory
 				 * @param String m_oFileSearchTextField.getText() - keyword to search
 				 * 
 				 * Result of module:
 				 * bib format file with documents descriptions with proper url address must be created
 				 * in session directory path
				 */
				//section to delete - only for tests
//				File newOne = new File("sessions\\sess_01-06-2016_15-34-11\\science.bib");
//				File dest = new File(strSessionDirectoryName + "\\science.bib");
//				try {
//					Files.copy(newOne.toPath(), dest.toPath());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				//section to delete end
				
				File[] oFilesList = oDirectory.listFiles();
				if(oFilesList.length > 0)
				{
					File oNewNameFile = new File(strSessionDirectoryName + "\\" +CSession.SESSION_FILE_NAME);
					oFilesList[0].renameTo(oNewNameFile);
					m_oSessions.addSession("Session-"+strSessionSufixName+"-"+m_oFileSearchTextField.getText(), strSessionDirectoryName);
					m_oSessions.saveRepo();
					
					createTree();
				}
				else
				{
					
				}
				CLogger.log(CLogger.INFO, m_oSearchNewDocButton.getText() + " - Pressed; With \"" + m_oFileSearchTextField.getText() + "\" value" );
			}
		});
		
		m_oScrollPane = new JScrollPane();
		
		m_oKeywordSearchTestField = new JTextField();
		m_oKeywordSearchTestField.setColumns(10);
		
		m_oSearchKeyword = new JButton("SearchKeywords");
		m_oSearchKeyword.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent a_oAction)
			{
				CLogger.log(CLogger.INFO, m_oSearchKeyword.getText() + " - Pressed; With \"" + m_oKeywordSearchTestField.getText() + "\" value" );
				if((0 < m_oDocumentsToSearch.size()) && (0 < m_oKeywordSearchTestField.getText().length()))
				{
					//TODO for Mateusz
					/**
					 * Here should be executed module to search keyword in documents
					 * parameters:
	 				 * @param HashMap<Integer, CDocument> m_oDocumentToSearch - document information
	 				 * @param String m_oKeywordSearchTestField.getText() - keyword to search
	 				 * 
	 				 * Result of module:
	 				 * bib format file with documents descriptions with proper url address must be created
	 				 * in session directory path
					 */
				}
			}
		});
		
		m_oPanel = new JPanel();
		
		m_oTitleTextPane = new JTextPane();
		m_oTitleTextPane.setEditable(false);
		m_oTitleTextPane.setPreferredSize(new Dimension(500, 71));
		m_oScrollTitle = new JScrollPane();
		m_oScrollTitle.setViewportView(m_oTitleTextPane);
		m_oScrollTitle.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		m_oJournalTextPane = new JTextPane();
		m_oJournalTextPane.setEditable(false);
		m_oVolumeTextPane = new JTextPane();
		m_oVolumeTextPane.setEditable(false);
		m_oNumberTextPane = new JTextPane();
		m_oNumberTextPane.setEditable(false);
		m_oPagesTextPane = new JTextPane();
		m_oPagesTextPane.setEditable(false);
		m_oYearTextPane = new JTextPane();
		m_oYearTextPane.setEditable(false);
		m_oNoteTextPane = new JTextPane();
		m_oNoteTextPane.setEditable(false);
		m_oISSNTextPane = new JTextPane();
		m_oISSNTextPane.setEditable(false);
		m_oDOITextPane = new JTextPane();
		m_oDOITextPane.setEditable(false);
		m_oURLTextPane = new JTextPane();
		m_oURLTextPane.setEditable(false);
		m_oAuthorTextPane = new JTextPane();
		m_oAuthorTextPane.setEditable(false);
		m_oAuthorTextPane.setPreferredSize(new Dimension(500, 71));
		m_oScrollAuthor = new JScrollPane();
		m_oScrollAuthor.setViewportView(m_oAuthorTextPane);
		m_oScrollAuthor.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		m_oKeywordsTextPane = new JTextPane();
		m_oKeywordsTextPane.setEditable(false);
		m_oAbstractTextPane = new JTextPane();
		m_oAbstractTextPane.setEditable(false);
		m_oAbstractTextPane.setPreferredSize(new Dimension(500, 71));
		m_oScrollAbstract = new JScrollPane();
		m_oScrollAbstract.setViewportView(m_oAbstractTextPane);
		m_oScrollAbstract.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		m_oTitleLabel = new JLabel("Title");
		m_oJournalLabel = new JLabel("journal");
		m_oVolumeLabel = new JLabel("volume");
		m_oNumberLabel = new JLabel("number");
		m_oPagesLabel = new JLabel("pages");
		m_oYearLabel = new JLabel("year");
		m_oNoteLabel = new JLabel("note");
		m_oISSNLabel = new JLabel("issn");
		m_oDOILabel = new JLabel("doi");
		m_oURLLabel = new JLabel("url");
		m_oAuthorLabel = new JLabel("author");
		m_oKeywordsLabel = new JLabel("keywords");
		m_oAbstractLabel = new JLabel("abstract");
		
		m_oGroupLayout = new GroupLayout(m_oContentPane);
		m_oGroupLayout.setHorizontalGroup(
			m_oGroupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(m_oGroupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(m_oScrollPane, GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
						.addComponent(m_oFileSearchTextField, GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
						.addGroup(m_oGroupLayout.createSequentialGroup()
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(m_oKeywordsLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(m_oAuthorLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(m_oURLLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(m_oDOILabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(m_oNoteLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(m_oPagesLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(m_oVolumeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(m_oTitleLabel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
									.addComponent(m_oJournalLabel, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
								.addComponent(m_oAbstractLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(m_oScrollAuthor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(m_oScrollTitle, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
									.addGroup(m_oGroupLayout.createSequentialGroup()
										.addGroup(m_oGroupLayout.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(m_oVolumeTextPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
											.addComponent(m_oPagesTextPane, Alignment.LEADING)
											.addComponent(m_oNoteTextPane, Alignment.LEADING))
										.addGap(18)
										.addGroup(m_oGroupLayout.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(m_oISSNLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(m_oYearLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(m_oNumberLabel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(m_oGroupLayout.createParallelGroup(Alignment.TRAILING)
											.addComponent(m_oISSNTextPane, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
											.addComponent(m_oYearTextPane, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
											.addComponent(m_oNumberTextPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)))
									.addComponent(m_oKeywordsTextPane, GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
									.addComponent(m_oScrollAbstract)
									.addComponent(m_oJournalTextPane)
									.addComponent(m_oDOITextPane)
									.addComponent(m_oURLTextPane))))
						.addComponent(m_oKeywordSearchTestField, GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(m_oGroupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(m_oGroupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(m_oPanel, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
							.addComponent(m_oSearchNewDocButton, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
						.addComponent(m_oSearchKeyword, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
					.addContainerGap())
		);
		m_oGroupLayout.setVerticalGroup(
			m_oGroupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(m_oGroupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(m_oGroupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(m_oFileSearchTextField, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(m_oSearchNewDocButton))
					.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(m_oGroupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_oPanel, GroupLayout.PREFERRED_SIZE, 336, GroupLayout.PREFERRED_SIZE))
						.addGroup(m_oGroupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(m_oScrollPane, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(m_oTitleLabel)
								.addComponent(m_oScrollTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(m_oJournalLabel)
								.addComponent(m_oJournalTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(m_oGroupLayout.createSequentialGroup()
									.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(m_oVolumeLabel)
										.addComponent(m_oVolumeTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(m_oPagesLabel)
										.addComponent(m_oPagesTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(m_oGroupLayout.createSequentialGroup()
									.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(m_oNumberLabel, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
										.addComponent(m_oNumberTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(m_oYearLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(m_oYearTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(m_oISSNLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(m_oNoteLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(m_oISSNTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(m_oNoteTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(m_oDOILabel)
								.addComponent(m_oDOITextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(m_oGroupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(m_oGroupLayout.createSequentialGroup()
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(m_oURLLabel)
								.addComponent(m_oURLTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(m_oAuthorLabel)
								.addComponent(m_oScrollAuthor, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(m_oKeywordsLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(m_oKeywordsTextPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(m_oGroupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(m_oGroupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(m_oScrollAbstract, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
								.addGroup(m_oGroupLayout.createSequentialGroup()
									.addGap(32)
									.addComponent(m_oAbstractLabel)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(m_oKeywordSearchTestField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(m_oSearchKeyword)))
		);
		
		createTree();
		
		m_oCheckBoxTitle = new JTextPane();
		m_oCheckBoxTitle.setText("Choosen sites");
		m_oCheckBoxTitle.setBackground(m_oPanel.getBackground());
		
		
		m_oCheckBox_1 = new JCheckBox(CHECK_BOX_NAME_1);
		
		m_oCheckBox_2 = new JCheckBox(CHECK_BOX_NAME_2);
		
		m_oCheckBox_3 = new JCheckBox(CHECK_BOX_NAME_3);
		
		m_oCheckBox_4 = new JCheckBox(CHECK_BOX_NAME_4);
		m_oCheckBoxGroupLayout = new GroupLayout(m_oPanel);
		m_oCheckBoxGroupLayout.setHorizontalGroup(
			m_oCheckBoxGroupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(m_oCheckBoxGroupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(m_oCheckBoxGroupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(m_oCheckBox_4)
						.addComponent(m_oCheckBox_3)
						.addComponent(m_oCheckBox_2)
						.addComponent(m_oCheckBox_1)
						.addComponent(m_oCheckBoxTitle, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
					.addContainerGap())
		);
		m_oCheckBoxGroupLayout.setVerticalGroup(
			m_oCheckBoxGroupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(m_oCheckBoxGroupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(m_oCheckBoxTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(m_oCheckBox_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(m_oCheckBox_2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(m_oCheckBox_3)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(m_oCheckBox_4)
					.addContainerGap(177, Short.MAX_VALUE))
		);
		
		
		m_oPanel.setLayout(m_oCheckBoxGroupLayout);
		m_oContentPane.setLayout(m_oGroupLayout);
	}
	
	private void createTree()
	{
		DefaultMutableTreeNode oNewNode = null;
		m_oTreeModel = new DefaultMutableTreeNode();
		for(int i = 0; i < m_oSessions.size(); i++)
		{
			oNewNode = new DefaultMutableTreeNode(m_oSessions.get(i));
			for(int k = 0; k < m_oSessions.get(i).m_oFileDescriptions.size(); k++)
			{
				oNewNode.add(new DefaultMutableTreeNode(m_oSessions.get(i).m_oFileDescriptions.get(k)));
			}
			
			
			m_oTreeModel.add(oNewNode);
		}

		m_oTree = new JTree(m_oTreeModel);
		m_oTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		m_oTree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent a_oEvent)
			{

				DefaultMutableTreeNode oNode = (DefaultMutableTreeNode) m_oTree.getLastSelectedPathComponent();
				Object oObject = oNode.getUserObject();
				if(oObject instanceof CDocument)
				{
					CDocument oDocument = (CDocument)oObject;
					
					m_oTitleTextPane.setText(oDocument.getTitle());
					m_oJournalTextPane.setText(oDocument.getJournal());
					m_oVolumeTextPane.setText(oDocument.getVolume());
					m_oNumberTextPane.setText(oDocument.getNumber());
					m_oPagesTextPane.setText(oDocument.getPages());
					m_oYearTextPane.setText(oDocument.getYear());
					m_oNoteTextPane.setText(oDocument.getNote());
					m_oISSNTextPane.setText(oDocument.getISSN());
					m_oDOITextPane.setText(oDocument.getDOI());
					m_oURLTextPane.setText(oDocument.getURL());
					m_oAuthorTextPane.setText(oDocument.getAutor());
					m_oKeywordsTextPane.setText(oDocument.getKeywords());
					m_oAbstractTextPane.setText(oDocument.getAbstract());
					
					m_oAbstractTextPane.setCaretPosition(0);
					m_oTitleTextPane.setCaretPosition(0);
					m_oAuthorTextPane.setCaretPosition(0);
					m_oTree.updateUI();
					if((a_oEvent.getKeyCode() == KeyEvent.VK_Z) && (null != oDocument.getFileName()))
					{
						oDocument.changeSelection();
						if(oDocument.isSelected())
						{
							m_oDocumentsToSearch.put(oDocument.hashCode(), oDocument);
						}
						else
						{
							m_oDocumentsToSearch.remove(oDocument);
						}
						m_oTree.updateUI();
					}
					else if((a_oEvent.getKeyCode() == KeyEvent.VK_Z) && (null != oDocument.getURL()))
					{
						try
						{
							URL oURL = new URL(oDocument.getURL());
							ReadableByteChannel oReadByteChannel = Channels.newChannel(oURL.openStream());
							String strFileName = oURL.getFile().replaceAll(".*/", "");
							FileOutputStream oFileWriter = new FileOutputStream(oDocument.getPath() + "\\" + strFileName);
							oFileWriter.getChannel().transferFrom(oReadByteChannel, 0, Long.MAX_VALUE);
							CLogger.log(CLogger.INFO, "File is downloaded" );
							
							oDocument.setFileName(strFileName);
							oFileWriter.close();
							oDocument.changeSelection();
							m_oTree.updateUI();
							m_oSessions.saveRepo();
						}
						catch (MalformedURLException e)
						{
							CLogger.log(CLogger.WARNING, "URL is not available :" + e.getMessage() );
							return;
						}
						catch (IOException e)
						{
							CLogger.log(CLogger.WARNING, "Channel can not be estabilished :" + e.getMessage() );
							return;
						}
					}
					else if(a_oEvent.getKeyCode() == KeyEvent.VK_Z)
					{
						CLogger.log(CLogger.INFO, "No file or URL information");
					}
				}
				else
				{
					m_oTitleTextPane.setText(" ");
					m_oJournalTextPane.setText(" ");
					m_oVolumeTextPane.setText(" ");
					m_oNumberTextPane.setText(" ");
					m_oPagesTextPane.setText(" ");
					m_oYearTextPane.setText(" ");
					m_oNoteTextPane.setText(" ");
					m_oISSNTextPane.setText(" ");
					m_oDOITextPane.setText(" ");
					m_oURLTextPane.setText(" ");
					m_oAuthorTextPane.setText(" ");
					m_oKeywordsTextPane.setText(" ");
					m_oAbstractTextPane.setText(" ");
					
					m_oTree.updateUI();
				}
			}
		});
		
		m_oScrollPane.setViewportView(m_oTree);
		m_oTree.setRootVisible(false);
		
	}

}
